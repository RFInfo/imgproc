package edu.info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Utils {

    public static BufferedImage loadImage(String fileName){
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public static void saveImage(BufferedImage image, String fileName, String fileType){
        try {
            ImageIO.write(image,fileType, new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayImage(BufferedImage image, String title){
        if(image == null)
            return;
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setFitToScreen(false);
        imagePanel.setImage(image);

        frame.setContentPane(new JScrollPane(imagePanel));
        frame.pack();
        frame.setVisible(true);
    }

    static public BufferedImage generateRandom(int width, int heigh){
//        BufferedImage image = new BufferedImage(width, heigh, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage image = new BufferedImage(width, heigh, BufferedImage.TYPE_3BYTE_BGR);
        Random random = new Random();

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                image.getRaster().setSample(x,y,0, random.nextInt(256));
                image.getRaster().setSample(x,y,1, random.nextInt(256));
                image.getRaster().setSample(x,y,2, random.nextInt(256));
            }

        return image;
    }

    static public BufferedImage extractBands(BufferedImage image, char band){
        // band = A, R, G, B

        BufferedImage outImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Random random = new Random();

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x,y);

                int alpha = (pixel & 0xFF000000) >> 24;
                int red   = (pixel & 0x00FF0000) >> 16;
                int green = (pixel & 0x0000FF00) >> 8;
                int blue  = (pixel & 0x000000FF);

                switch (band){
                    case 'A' -> outImg.getRaster().setSample(x,y,0, alpha);
                    case 'R' -> outImg.getRaster().setSample(x,y,0, red);
                    case 'G' -> outImg.getRaster().setSample(x,y,0, green);
                    case 'B' -> outImg.getRaster().setSample(x,y,0, blue);
                }
            }
        return outImg;
    }
}
