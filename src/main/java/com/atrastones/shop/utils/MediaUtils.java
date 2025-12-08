package com.atrastones.shop.utils;

import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.exception.UtilsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
public final class MediaUtils {

    private static final Path UPLOAD_ROOT = Paths.get("uploads");
    private static final Path DRAFT_DIR = UPLOAD_ROOT.resolve("draft");
    private static final Path PRODUCTS_ROOT = UPLOAD_ROOT.resolve("products");

    private MediaUtils() {
    }

    // ======================================= PUBLIC API ===================================================

    public static List<ProductMediaDTO> list(long productId) {

        Path productDir = productDir(productId);
        List<ProductMediaDTO> mediaList = new ArrayList<>();

        if (!Files.exists(productDir) || !Files.isDirectory(productDir)) {
            log.warn("Media directory does not exist for product {}", productId);
            return mediaList;
        }

        try (Stream<Path> stream = Files.list(productDir)) {
            int[] displayOrder = {0};
            stream
                    .filter(Files::isRegularFile)
                    .sorted()
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        String extension = getExtension(fileName);
                        mediaList.add(
                                new ProductMediaDTO(
                                        productId,
                                        fileName,
                                        extension,
                                        displayOrder[0]++
                                )
                        );
                    });
        } catch (IOException e) {
            log.error("Failed to list media files for product {}: {}", productId, e.getMessage(), e);
            throw new RuntimeException("Unable to list product media for product " + productId, e);
        }

        return mediaList;
    }

    public static List<ProductMediaDTO> listDrafts() {

        ensureDirectoryExists(DRAFT_DIR);

        List<ProductMediaDTO> mediaList = new ArrayList<>();

        try (Stream<Path> stream = Files.list(DRAFT_DIR)) {
            int[] displayOrder = {0};

            stream
                    .filter(Files::isRegularFile)
                    .sorted()
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        String extension = getExtension(fileName);
                        mediaList.add(
                                ProductMediaDTO.builder()
                                        .url(fileName)
                                        .extension(extension)
                                        .displayOrder(displayOrder[0]++)
                                        .build()
                        );
                    });

        } catch (IOException e) {
            log.error("Failed to list draft files in '{}': {}", DRAFT_DIR, e.getMessage(), e);
            throw new RuntimeException("Unable to list draft media", e);
        }

        return mediaList;
    }

    public static List<ProductMediaDTO> draft(MultipartFile[] files) {
        ensureDirectoryExists(DRAFT_DIR);
        return saveFiles(files, DRAFT_DIR, null);
    }

    public static List<ProductMediaDTO> upload(long productId, MultipartFile[] files) {
        Path productDir = productDir(productId);
        ensureDirectoryExists(productDir);
        return saveFiles(files, productDir, productId);
    }

    public static void deleteFile(long productId, String fileName) {
        Path filePath = productDir(productId).resolve(fileName);
        if (!Files.exists(filePath)) {
            log.warn("File '{}' not found for product {}", fileName, productId);
            return;
        }
        try {
            Files.delete(filePath);
            log.info("Deleted file '{}' for product {}", fileName, productId);
        } catch (IOException e) {
            log.error("Failed to delete file '{}' for product {}: {}", fileName, productId, e.getMessage(), e);
            throw new RuntimeException("Unable to delete file: " + fileName, e);
        }
    }

    public static void deleteDraftFile(String fileName) {
        Path filePath = DRAFT_DIR.resolve(fileName);
        if (!Files.exists(filePath)) {
            log.warn("File '{}' not found in draft", fileName);
            throw new UtilsException("FILE.DOES.NOT.EXIST");
        }
        try {
            Files.delete(filePath);
            log.info("Deleted file '{}' in draft", fileName);
        } catch (IOException e) {
            log.error("Failed to delete file '{}' in draft: {}", fileName, e.getMessage(), e);
            throw new UtilsException("ERROR.DELETING.DRAFT.MEDIA");
        }
    }

    public static void deleteProductFolder(long productId) {
        Path dir = productDir(productId);
        deleteDirectoryRecursively(dir, "product " + productId);
    }

    public static void deleteFolder(Path folder) {
        deleteDirectoryRecursively(folder, folder.toString());
    }

    public static List<ProductMediaDTO> moveAllDraftsToProduct(long productId) {
        Path productDir = productDir(productId);
        ensureDirectoryExists(productDir);

        List<ProductMediaDTO> mediaList = new ArrayList<>();

        try (Stream<Path> stream = Files.list(DRAFT_DIR)) {
            int[] displayOrder = {0};
            stream
                    .filter(Files::isRegularFile)
                    .forEach(draftFile -> {
                        String fileName = draftFile.getFileName().toString();
                        Path targetFile = productDir.resolve(fileName);
                        try {
                            Files.move(draftFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                            String extension = getExtension(fileName);
                            mediaList.add(
                                    ProductMediaDTO.builder()
                                            .productId(productId)
                                            .url(fileName)
                                            .extension(extension)
                                            .displayOrder(displayOrder[0]++)
                                            .build()
                            );
                            log.info("Moved draft '{}' to product {} folder", fileName, productId);
                        } catch (IOException e) {
                            log.error("Failed to move draft '{}' to product {}: {}",
                                    fileName, productId, e.getMessage(), e);
                            throw new RuntimeException("Failed to move draft file: " + fileName, e);
                        }
                    });
        } catch (IOException e) {
            log.error("Failed to list draft directory '{}': {}", DRAFT_DIR, e.getMessage(), e);
            throw new RuntimeException("Unable to move drafts for product " + productId, e);
        }

        return mediaList;
    }

    // ======================================= PRIVATE HELPERS ===============================================

    private static List<ProductMediaDTO> saveFiles(MultipartFile[] files, Path directory, Long productId) {
        List<ProductMediaDTO> mediaList = new ArrayList<>(files.length);

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String originalName = file.getOriginalFilename();
            String extension = getExtension(originalName);
            String uniqueName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
            Path targetFile = directory.resolve(uniqueName);

            try (BufferedOutputStream outputStream =
                         new BufferedOutputStream(Files.newOutputStream(targetFile))) {

                outputStream.write(file.getBytes());

                ProductMediaDTO.ProductMediaDTOBuilder builder = ProductMediaDTO.builder()
                        .url(uniqueName)
                        .extension(extension)
                        .displayOrder(i);

                if (productId != null) {
                    builder.productId(productId);
                }

                mediaList.add(builder.build());

            } catch (IOException e) {
                log.error("Failed to save file '{}' to '{}': {}", originalName, directory, e.getMessage(), e);
                throw new RuntimeException("Failed to save product media: " + originalName, e);
            }
        }

        return mediaList;
    }

    private static void ensureDirectoryExists(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            log.error("Failed to create directory '{}': {}", dir, e.getMessage(), e);
            throw new RuntimeException("Unable to create directory: " + dir, e);
        }
    }

    private static void deleteDirectoryRecursively(Path dir, String context) {
        if (!Files.exists(dir)) {
            log.warn("Directory '{}' does not exist, nothing to delete for {}", dir, context);
            return;
        }

        try (Stream<Path> walk = Files.walk(dir)) {
            walk
                    .sorted(Comparator.reverseOrder()) // delete children first
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            log.error("Failed to delete path '{}' for {}: {}", path, context, e.getMessage(), e);
                            throw new RuntimeException("Unable to delete path: " + path, e);
                        }
                    });
            log.info("Deleted directory '{}' for {}", dir, context);
        } catch (IOException e) {
            log.error("Failed to traverse directory '{}' for {}: {}", dir, context, e.getMessage(), e);
            throw new RuntimeException("Unable to delete directory: " + dir, e);
        }
    }

    private static Path productDir(long productId) {
        return PRODUCTS_ROOT.resolve(String.valueOf(productId));
    }

    private static String getExtension(String filename) {
        if (filename == null) return "";
        int index = filename.lastIndexOf('.');
        return (index > 0 && index < filename.length() - 1) ? filename.substring(index + 1) : "";
    }

}