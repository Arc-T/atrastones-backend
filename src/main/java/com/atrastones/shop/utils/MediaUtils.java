package com.atrastones.shop.utils;

import com.atrastones.shop.dto.ProductMediaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public final class MediaUtils {

    private MediaUtils() {
    }

    public static List<ProductMediaDTO> list(long productId) {

        Path productDir = Paths.get("uploads", "products", String.valueOf(productId));
        List<ProductMediaDTO> mediaList = new ArrayList<>();

        if (!Files.exists(productDir) || !Files.isDirectory(productDir)) {
            log.warn("Media directory does not exist for product {}", productId);
            return mediaList;
        }

        try {
            int displayOrder = 0;
            for (Path filePath : Files.list(productDir).toList()) {
                if (Files.isRegularFile(filePath)) {
                    String fileName = filePath.getFileName().toString();
                    String extension = getExtension(fileName);
                    mediaList.add(
                            ProductMediaDTO.builder()
                                    .productId(productId)
                                    .url(fileName)
                                    .extension(extension)
                                    .displayOrder(displayOrder++)
                                    .build()
                    );
                }
            }
        } catch (IOException e) {
            log.error("Failed to list media files for product {}: {}", productId, e.getMessage(), e);
            throw new RuntimeException("Unable to list product media for product " + productId, e);
        }

        return mediaList;
    }

    public static List<ProductMediaDTO> draft(MultipartFile[] files) {

        List<ProductMediaDTO> mediaList = new ArrayList<>(files.length);
        Path draftPath = Paths.get("uploads", "draft");
        try {
            Files.createDirectories(draftPath);
        } catch (IOException e) {
            log.error("Failed to create directory '{}': {}", draftPath, e.getMessage(), e);
            throw new RuntimeException("Unable to create upload directory for draft ", e);
        }
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String extension = getExtension(file.getOriginalFilename());
            String uniqueName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
            Path targetFile = draftPath.resolve(uniqueName);
            try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(targetFile))) {
                outputStream.write(file.getBytes());
                mediaList.add(
                        ProductMediaDTO.builder()
                                .url(uniqueName)
                                .extension(extension)
                                .displayOrder(i)
                                .build()
                );
            } catch (IOException e) {
                log.error("Failed to save file '{}' for draft", file.getOriginalFilename());
                throw new RuntimeException("Failed to save product media: " + file.getOriginalFilename(), e);
            }
        }
        return mediaList;
    }

    public static List<ProductMediaDTO> upload(long productId, MultipartFile[] files) {

        List<ProductMediaDTO> mediaList = new ArrayList<>(files.length);
        Path productDir = Paths.get("uploads/products", String.valueOf(productId));
        try {
            Files.createDirectories(productDir);
        } catch (IOException e) {
            log.error("Failed to create directory '{}': {}", productDir, e.getMessage(), e);
            throw new RuntimeException("Unable to create upload directory for product " + productId, e);
        }
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String extension = getExtension(file.getOriginalFilename());
            String uniqueName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
            Path targetFile = productDir.resolve(uniqueName);
            try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(targetFile))) {
                outputStream.write(file.getBytes());
                mediaList.add(
                        ProductMediaDTO.builder()
                                .productId(productId)
                                .url(uniqueName)
                                .extension(extension)
                                .displayOrder(i)
                                .build()
                );
            } catch (IOException e) {
                log.error("Failed to save file '{}' for product {}: {}", file.getOriginalFilename(), productId, e.getMessage(), e);
                throw new RuntimeException("Failed to save product media: " + file.getOriginalFilename(), e);
            }
        }
        return mediaList;
    }

    private static String getExtension(String filename) {
        if (filename == null) return "";
        int index = filename.lastIndexOf('.');
        return (index > 0 && index < filename.length() - 1) ? filename.substring(index + 1) : "";
    }

}