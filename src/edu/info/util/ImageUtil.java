package edu.info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
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

    public static void displayImage(BufferedImage img) {
        displayImage(img, "");
    }
    
    public static BufferedImage generateRandom(int width, int height){
//        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
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
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int pixel = inImg.getRGB(x,y);

                int alpha = (pixel & 0xff000000) >> 24; // (pixel >> 24) & 0xff
                int red =   (pixel & 0x00ff0000) >> 16; // (pixel >> 16) & 0xff
                int green = (pixel & 0x0000ff00) >> 8;  // (pixel >> 8) & 0xff
                int blue =  (pixel & 0x000000ff);       // (pixel) & 0xff

                // how to recompose
                //pixel = 0x00000000 | (alpha << 24) | (red << 16) | (green << 8) | blue;

                // print first row
//                if(y == 0)
//                    System.out.println(alpha + " " + red + " " + green + " " + blue);

                switch (band){
                    case 'A' -> outImg.getRaster().setSample(x,y,0,alpha);
                    case 'R' -> outImg.getRaster().setSample(x,y,0,red);
                    case 'G' -> outImg.getRaster().setSample(x,y,0,green);
                    case 'B' -> outImg.getRaster().setSample(x,y,0,blue);
                }
            }
        return outImg;
    }

    public static BufferedImage extractBandV2(BufferedImage inImg, int band){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int pixel = inImg.getRaster().getSample(x,y,band);
                outImg.getRaster().setSample(x,y,0,pixel);
            }
        return outImg;
    }

    public static BufferedImage grayLevelGenerator(int firstGrayLevel, int blockSize, int grayLevelStep, int imgHeight){

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

    public static BufferedImage pixelate(BufferedImage inImg, int blockSize){
        BufferedImage outImg = null;

        if((inImg.getWidth() % blockSize) != 0 || (inImg.getHeight() % blockSize != 0)){
            System.out.println("Wrong image size!");
            return outImg;
        }

        outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(), inImg.getType());

        for (int band = 0; band < inImg.getRaster().getNumBands(); band++)
            for (int y = 0; y < outImg.getHeight(); y+=blockSize)
                for (int x = 0; x < outImg.getWidth(); x+=blockSize) {
                    int grayLevelSum = 0;
                    for(int yi = 0; yi <blockSize; yi ++)
                        for(int xi = 0; xi <blockSize; xi ++)
                            grayLevelSum+=inImg.getRaster().getSample(x+xi, y+yi, band);

                    int avgGrayLevel = grayLevelSum / (blockSize * blockSize);

                    for(int yi = 0; yi <blockSize; yi ++)
                        for(int xi = 0; xi <blockSize; xi ++)
                            outImg.getRaster().setSample(x+xi, y+yi,band, avgGrayLevel);
                }
        return outImg;
    }

    public static BufferedImage brightnessV1(BufferedImage inImg, int offset){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        for (int band = 0; band < inImg.getRaster().getNumBands(); band++)
            for (int y = 0; y < inImg.getHeight(); y++)
                for (int x = 0; x < inImg.getWidth(); x++) {
                    int inGrayLevel = inImg.getRaster().getSample(x,y,band);
                    int outGrayLevel = constrain(inGrayLevel + offset);
                    outImg.getRaster().setSample(x,y,band,outGrayLevel);
                }

        return  outImg;
    }

    public static BufferedImage brightnessV2(BufferedImage inImg, int offset){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] brightnessLUT = new short[256];

        for (int i = 0; i < brightnessLUT.length; i++) {
            brightnessLUT[i] = (short) constrain(i + offset);
            System.out.print(brightnessLUT[i] + " ");
        }
        System.out.println();


        for (int band = 0; band < inImg.getRaster().getNumBands(); band++)
            for (int y = 0; y < inImg.getHeight(); y++)
                for (int x = 0; x < inImg.getWidth(); x++) {
                    int inGrayLevel = inImg.getRaster().getSample(x,y,band);
                    int outGrayLevel = brightnessLUT[inGrayLevel];
                    outImg.getRaster().setSample(x,y,band,outGrayLevel);
                }

        return  outImg;
    }

    public static BufferedImage brightnessV3(BufferedImage inImg, int offset){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] brightnessLUT = new short[256];

        for (int i = 0; i < brightnessLUT.length; i++) {
            brightnessLUT[i] = (short) constrain(i + offset);
            System.out.print(brightnessLUT[i] + " ");
        }
        System.out.println();

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,brightnessLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg,outImg);

        return  outImg;
    }

    public static int constrain(int val, int min, int max){
        return val > max ? max : (val < min ? min : val);
    }

    public static int constrain(int val){
        return constrain(val,0,255);
    }
}
