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

    private static final String UPLOAD_DIR = "uploads/products";

    private MediaUtils() {
    }

    /**
     * Saves uploaded files to disk and returns a list of ProductMediaDTO
     * with the file paths in the content field.
     *
     * @param productId the product ID
     * @param files     one or multiple uploaded files
     * @return list of ProductMediaDTO containing metadata and saved file paths
     */
    public static List<ProductMediaDTO> uploadProductMedia(long productId, List<MultipartFile> files) {
        List<ProductMediaDTO> mediaList = new ArrayList<>(files.size());
        Path productDir = Paths.get(UPLOAD_DIR, String.valueOf(productId));
        try {
            Files.createDirectories(productDir);
        } catch (IOException e) {
            log.error("Failed to create directory '{}': {}", productDir, e.getMessage(), e);
            throw new RuntimeException("Unable to create upload directory for product " + productId, e);
        }
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
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

    /**
     * Extracts the file extension from a filename.
     *
     * @param filename the original filename
     * @return the extension without dot, e.g., "png" or "" if none
     */
    private static String getExtension(String filename) {
        if (filename == null) return "";
        int index = filename.lastIndexOf('.');
        return (index > 0 && index < filename.length() - 1) ? filename.substring(index + 1) : "";
    }

}