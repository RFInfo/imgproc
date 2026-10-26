package edu.info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
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

    public static BufferedImage flipV(BufferedImage inImg){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        for (int y = 0; y < inImg.getHeight()/2; y++)
            for (int x = 0; x < inImg.getWidth(); x++) {

                outImg.setRGB(x, y, inImg.getRGB(x, inImg.getHeight()-1-y));
                outImg.setRGB(x, inImg.getHeight()-1-y, inImg.getRGB(x,y));

            }

        return outImg;
    }
    public static BufferedImage flipH(BufferedImage inImg){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth()/2; x++) {

                outImg.setRGB(x, y, inImg.getRGB(inImg.getWidth()-1-x, y));
                outImg.setRGB(inImg.getWidth()-1-x, y, inImg.getRGB(x,y));

            }

        return outImg;
    }

    public static BufferedImage pixelate(BufferedImage inImg, int blockSize) {
        BufferedImage outImg = null;

        if (inImg.getWidth() % blockSize != 0 && inImg.getHeight() % blockSize != 0) {
            System.out.println("wrong image size");
            return outImg;
        }

        outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        for (int band = 0; band < inImg.getRaster().getNumBands(); band++)
            for (int y = 0; y < outImg.getHeight(); y += blockSize) {
                for (int x = 0; x < outImg.getWidth(); x += blockSize) {

                    // parcurgem blocul
                    int grayLevelSum = 0;
                    for (int yi = 0; yi < blockSize; yi++)
                        for (int xi = 0; xi < blockSize; xi++)
                            grayLevelSum += inImg.getRaster().getSample(x + xi, y + yi, band);

                    int avgGrayLevel = grayLevelSum / (blockSize * blockSize);

                    for (int yi = 0; yi < blockSize; yi++)
                        for (int xi = 0; xi < blockSize; xi++)
                            outImg.getRaster().setSample(x + xi, y + yi, band, avgGrayLevel);
                }
            }

        return outImg;
    }

    public static BufferedImage grayLevelGenerator(int firstGrayLevel, int blockSize, int grayLevelStep, int imgHeight) {

        int imgWidth = ((256 - firstGrayLevel) / grayLevelStep) * blockSize;

        BufferedImage outImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < outImg.getHeight(); y++) {
            int grayLevel = firstGrayLevel;
            for (int x = 0; x < outImg.getWidth(); x += blockSize) {
                for (int xi = 0; xi < blockSize; xi++) {
                    outImg.getRaster().setSample(x + xi, y, 0, grayLevel);
                }
                grayLevel += grayLevelStep;
            }
        }
        return outImg;
    }

    public static BufferedImage toGrayAVG(BufferedImage input){
        BufferedImage output = null;
        output = new
                BufferedImage(input.getWidth(),input.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        for(int y=0; y<input.getHeight(); y++)
            for(int x=0; x<input.getWidth(); x++){
                int r = input.getRaster().getSample(x, y, 0);
                int g = input.getRaster().getSample(x, y, 1);
                int b = input.getRaster().getSample(x, y, 2);
                output.getRaster().setSample(x, y, 0, (r+g+b)/3);
            }
        return output;
    }

    public static int constrain(int val, int min, int max){
        return val > max ? max : (val < min ? min : val);
    }

    public static int constrain(int val){
        return constrain(val, 0, 255);
    }

    public enum GrayTransforms {
        GRAY_TRANSFORMS_GREEN, GRAY_TRANSFORMS_SQRT,
        GRAY_TRANSFORMS_AVG, GRAY_TRANSFORMS_USUAL, GRAY_TRANSFORMS_PAL
    }

    public static BufferedImage toGray(BufferedImage inImg, GrayTransforms version) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        if (inImg.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            inImg.copyData(outImg.getRaster());
            return outImg;
        }
        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int r = inImg.getRaster().getSample(x, y, 0);
                int g = inImg.getRaster().getSample(x, y, 1);
                int b = inImg.getRaster().getSample(x, y, 2);
                int grayLevel = 0;
                switch (version) {
                    case GRAY_TRANSFORMS_GREEN -> grayLevel = g;
                    case GRAY_TRANSFORMS_SQRT -> grayLevel =
                            constrain((int) Math.round(Math.sqrt(r * r + g * g + b * b)));
                    case GRAY_TRANSFORMS_AVG -> grayLevel =
                            constrain((int) Math.round((double) (r + g + b) / 3));
                    case GRAY_TRANSFORMS_USUAL -> grayLevel =
                            constrain((int) Math.round((double) (3 * r + 2 * g + 4 * b) / 9));
                    case GRAY_TRANSFORMS_PAL -> grayLevel =
                            constrain((int) Math.round(0.299 * r + 0.587 * g + 0.114 * b));
                }
                outImg.getRaster().setSample(x, y, 0, grayLevel);
            }
        return outImg;
    }

    public static BufferedImage toGrayscaleFast(BufferedImage src) {
        BufferedImage gray = new BufferedImage(
                src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        ColorConvertOp op = new ColorConvertOp(
                ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(src, gray);
        return gray;
    }
}
