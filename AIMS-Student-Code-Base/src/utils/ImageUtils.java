package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageUtils {

    public static String saveImage(File sourceFile, String destinationFolderPath) throws Exception {
        // Ensure the destination folder exists
        File destinationFolder = new File(destinationFolderPath);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + sourceFile.getName();

        // Create the destination path
        Path destinationPath = Path.of(destinationFolderPath, fileName);

        // Copy the file to the destination folder
        Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the saved file's path
        return destinationPath.toString();
    }
}
