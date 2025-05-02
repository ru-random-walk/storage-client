package ru.random.walk.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class FileUtil {
    public static boolean isImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return Objects.nonNull(image);
        } catch (IOException e) {
            return false;
        }
    }

    public static String getBase64FromFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent;
            fileContent = fis.readAllBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        }
    }

    public static File getFileFromBase64(String base64, String outputFilePath) throws IOException {
        byte[] fileContent = Base64.getDecoder().decode(base64);
        File file = new File(outputFilePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent);
        }
        return file;
    }
}
