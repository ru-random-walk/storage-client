package ru.random.walk.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

public class FileUtil {
    public static InputStream getInputStream(String base64) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        return new ByteArrayInputStream(imageBytes);
    }

    public static boolean isImage(String base64) {
        try {
            var input = getInputStream(base64);
            BufferedImage image = ImageIO.read(input);
            return Objects.nonNull(image);
        } catch (IOException e) {
            return false;
        }
    }
}
