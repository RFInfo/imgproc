package info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    public static BufferedImage loadImage(String filename){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static void saveImage(BufferedImage img, String filename, String fileType){
        try {
            ImageIO.write(img,fileType, new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayImage(BufferedImage img, String ... title){
        if(img == null)
            return;
        JFrame frame = new JFrame(String.join(" - ", title));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setFitToScreen(false);
        imagePanel.setImage(img);
        frame.setContentPane(new JScrollPane(imagePanel));
        frame.pack();
        frame.setVisible(true);
    }

    public static BufferedImage extractBands(BufferedImage inImg, char band){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int pixel = inImg.getRGB(x, y);

                int alpha = (pixel & 0xFF000000) >> 24; // (argb >> 24) & 0xff;
                int red = (pixel & 0x00FF0000) >> 16; // (argb >> 16) & 0xff;
                int green = (pixel & 0x0000FF00) >> 8;  // (argb >> 8) & 0xff;
                int blue = (pixel & 0x000000FF);       // (argb) & 0xff;

                // how to recompose?
                // pixel = 0x00000000 | (alpha << 24) | (red << 16) | (green << 8) | blue;

                if (y == 0)
                    System.out.println(alpha + " " + red + " " + green + " " + blue);


                switch (band) {
                    case 'A' -> outImg.getRaster().setSample(x, y, 0, alpha);
                    case 'R' -> outImg.getRaster().setSample(x, y, 0, red);
                    case 'G' -> outImg.getRaster().setSample(x, y, 0, green);
                    case 'B' -> outImg.getRaster().setSample(x, y, 0, blue);
                }
            }

        return outImg;
    }
}
