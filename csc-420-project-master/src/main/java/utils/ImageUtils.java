package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public class ImageUtils {

    public static BufferedImage resizeBufferedImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static Image scaleImage(BufferedImage imageIcon, int w, int h) {
        return imageIcon.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }

    public static BufferedImage loadImage(String imageUrl) {
        BufferedImage image = null;
        try {
            String urls = imageUrl;
            URL url = new URL(urls);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "NING/1.0");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            image = bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
