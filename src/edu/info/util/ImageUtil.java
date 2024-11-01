package edu.info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageUtil {

    public static BufferedImage loadImage(String filename){
       BufferedImage img= null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static void saveImage(BufferedImage img, String fileName, String fileType){
        try {
            ImageIO.write(img, fileType, new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayImage(BufferedImage img, String title){
        if (img == null)
            return;
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setFitToScreen(false);
        imagePanel.setImage(img);
        frame.setContentPane(new JScrollPane(imagePanel));
        frame.pack();
        frame.setVisible(true);
    }
    
    public static BufferedImage generateRandom(int width, int height){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Random rand = new Random();

        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++) {
//                img.setRGB(x,y, rand.nextInt(256));
                img.getRaster().setSample(x,y,0,rand.nextInt(256));
                img.getRaster().setSample(x,y,1,rand.nextInt(256));
                img.getRaster().setSample(x,y,2,rand.nextInt(256));
            }
        return img;
    }

    public static BufferedImage extractBand(BufferedImage inImg, char band){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int pixel = inImg.getRGB(x,y);

                int alpha = (pixel & 0xff000000) >> 24;
                int red   = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue  = (pixel & 0x000000ff);

                if(y == 0)
                    System.out.print(alpha + " " + red + " " + green + " " + blue + " / ");

                switch (band){
                    case 'R' -> outImg.getRaster().setSample(x,y,0, red);
                    case 'G' -> outImg.getRaster().setSample(x,y,0, green);
                    case 'B' -> outImg.getRaster().setSample(x,y,0, blue);
                    case 'A' -> outImg.getRaster().setSample(x,y,0, alpha);
                }
            }



        return outImg;
    }
}
