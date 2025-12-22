package com.atrastones.shop.utils;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.exception.UtilsException;
import com.atrastones.shop.type.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
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

    // ======================================= PUBLIC API =======================================

    public static List<MediaDTO> list(long productId) {
        return listFromDirectory(productDir(productId), productId);
    }

    public static List<MediaDTO> listDrafts() {
        return listFromDirectory(DRAFT_DIR, null);
    }

    public static List<MediaDTO> draft(MultipartFile[] files) {
        ensureDirectoryExists(DRAFT_DIR);
        return saveFiles(files);
    }

    public static void deleteProductMedia(long productId, String fileName) {
        deleteFileInternal(productDir(productId).resolve(fileName), "product " + productId);
    }

    public static void deleteDraftFile(String fileName) {
        deleteFileInternal(DRAFT_DIR.resolve(fileName), "draft");
    }

    public static void deleteProductFolder(long productId) {
        deleteDirectoryRecursively(productDir(productId), "product " + productId);
    }

    public static void deleteFolder(Path folder) {
        deleteDirectoryRecursively(folder, folder.toString());
    }

    public static List<ProductMediaDTO> moveAllDraftsToProduct(long productId) {
        Path targetDir = productDir(productId);
        ensureDirectoryExists(targetDir);

        List<ProductMediaDTO> result = new ArrayList<>();

        try (Stream<Path> stream = Files.list(DRAFT_DIR)) {
            List<Path> drafts = stream.filter(Files::isRegularFile).sorted().toList();

            for (int index = 0; drafts.size() > index; index++) {
                Path target = targetDir.resolve(drafts.get(index).getFileName());
                String filename = target.getFileName().toString();
                Files.move(drafts.get(index), target, StandardCopyOption.REPLACE_EXISTING);
                result.add(
                        new ProductMediaDTO(
                                productId,
                                filename,
                                MediaType.IMAGE.name(),
                                index,
                                getExtension(filename),
                                LocalDateTime.now())
                );
            }

            return result;
        } catch (IOException e) {
            log.error("Failed moving drafts to product {}: {}", productId, e.getMessage(), e);
            throw new RuntimeException("FAILED_TO_MOVE_DRAFTS");
        }
    }

    // ======================================= CORE HELPERS =======================================

    private static List<MediaDTO> listFromDirectory(Path dir, Long productId) {
        ensureDirectoryExists(dir);

        try (Stream<Path> stream = Files.list(dir)) {
            List<Path> files = stream
                    .filter(Files::isRegularFile)
                    .sorted()
                    .toList();

            List<MediaDTO> result = new ArrayList<>(files.size());

            for (Path file : files) {
                result.add(new MediaDTO(file.getFileName().toString()));
            }

            return result;
        } catch (IOException e) {
            log.error("Failed listing media in '{}': {}", dir, e.getMessage(), e);
            throw new RuntimeException("UNABLE_TO_LIST_MEDIA");
        }
    }

    private static List<MediaDTO> saveFiles(MultipartFile[] files) {
        List<MediaDTO> result = new ArrayList<>(files.length);

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String extension = getExtension(originalName);
            String uniqueName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
            Path target = MediaUtils.DRAFT_DIR.resolve(uniqueName);

            try (BufferedOutputStream out =
                         new BufferedOutputStream(Files.newOutputStream(target))) {

                out.write(file.getBytes());
                result.add(new MediaDTO(target.getFileName().toString()));

            } catch (IOException e) {
                log.error("Failed saving '{}' to '{}': {}", originalName, MediaUtils.DRAFT_DIR, e.getMessage(), e);
                throw new RuntimeException("FAILED_TO_SAVE_MEDIA");
            }
        }

        return result;
    }

    private static void deleteFileInternal(Path file, String context) {
        if (Files.notExists(file)) {
            log.warn("File '{}' not found ({})", file.getFileName(), context);
            throw new UtilsException("FILE.DOES.NOT.EXIST");
        }

        try {
            Files.delete(file);
            log.info("Deleted '{}' ({})", file.getFileName(), context);
        } catch (IOException e) {
            log.error("Failed deleting '{}' ({}): {}", file, context, e.getMessage(), e);
            throw new UtilsException("ERROR.DELETING.MEDIA");
        }
    }

    private static void deleteDirectoryRecursively(Path dir, String context) {
        if (Files.notExists(dir)) return;

        try (Stream<Path> walk = Files.walk(dir)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    log.error("Failed deleting '{}' ({})", path, context, e);
                    throw new RuntimeException("FAILED_TO_DELETE_DIRECTORY");
                }
            });
        } catch (IOException e) {
            log.error("Failed traversing '{}' ({})", dir, context, e);
            throw new RuntimeException("FAILED_TO_DELETE_DIRECTORY");
        }
    }

    private static void ensureDirectoryExists(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            log.error("Failed creating directory '{}'", dir, e);
            throw new RuntimeException("FAILED_TO_CREATE_DIRECTORY");
        }
    }

    private static Path productDir(long productId) {
        return PRODUCTS_ROOT.resolve(String.valueOf(productId));
    }

    private static String getExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        return (idx > 0 && idx < filename.length() - 1)
                ? filename.substring(idx + 1)
                : "";
    }

}