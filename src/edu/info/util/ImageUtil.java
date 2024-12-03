package edu.info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

    public static BufferedImage applySettingsDlg(BufferedImage img,
                                                 AbstractSettingsDialog dialog) {
        if (img == null)
            return null;
        JFrame frame = new JFrame();
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setFitToScreen(false);
        imagePanel.setImage(img);
        frame.setContentPane(new JScrollPane(imagePanel));
        frame.pack();
        frame.setVisible(true);
        dialog.setImagePanel(imagePanel);
        dialog.pack();
        dialog.setVisible(true);
        frame.dispose();
        return imagePanel.getImage();
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

    public static BufferedImage brightnessRGB(BufferedImage inImg, int offsetR, int offsetG, int offsetB){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] rLUT = new short[256];
        short[] gLUT = new short[256];
        short[] bLUT = new short[256];

        short[][] rgbLUT = {rLUT, gLUT, bLUT};

        for (int i = 0; i < 256; i++) {
            rLUT[i] = (short) constrain(i + offsetR);
            gLUT[i] = (short) constrain(i + offsetG);
            bLUT[i] = (short) constrain(i + offsetB);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,rgbLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg,outImg);

        return  outImg;
    }

    public static BufferedImage contrast(BufferedImage inImg, float scale){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] contrastLUT = new short[256];

        for (int i = 0; i < contrastLUT.length; i++) {
            contrastLUT[i] = (short) constrain(Math.round(scale * i));
            System.out.print(contrastLUT[i] + " ");
        }
        System.out.println();

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,contrastLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg,outImg);

        return  outImg;
    }

    public static BufferedImage contrastGamma(BufferedImage inImg, double gamma){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] contrastLUT = new short[256];

        for (int i = 0; i < contrastLUT.length; i++) {

            double a = i / 255.0; // scale to [0.0 ... 1.0]
            double b = Math.pow(a, 1.0/gamma);
            double c = b * 255.0; // scale to [0 ... 255]

            contrastLUT[i] = (short) constrain((int)Math.round(c));
            System.out.print(contrastLUT[i] + " ");
        }
        System.out.println();

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,contrastLUT);
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

    public enum GrayTransforms {
        GRAY_TRANSFORMS_GREEN, GRAY_TRANSFORMS_SQRT,
        GRAY_TRANSFORMS_AVG, GRAY_TRANSFORMS_USUAL, GRAY_TRANSFORMS_PAL
    }

    public static BufferedImage colorToGray(BufferedImage inImg, GrayTransforms version){
        if(inImg.getRaster().getNumBands() == 1)
            return inImg;

        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int r = inImg.getRaster().getSample(x,y,0);
                int g = inImg.getRaster().getSample(x,y,1);
                int b = inImg.getRaster().getSample(x,y,2);

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

                outImg.getRaster().setSample(x,y,0, grayLevel);
            }

        return outImg;
    }

    public static BufferedImage threshold(BufferedImage inImg, int threshold) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        short[] thresholdLUT = new short[256];

        for (int i = 0; i < 256; i++) {
            thresholdLUT[i] = (short)((i<threshold) ? 0 : 255);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,thresholdLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg,outImg);

        return outImg;
    }

    public static BufferedImage negative(BufferedImage inImg) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] negativeLUT = new short[256];

        for (int i = 0; i < 256; i++) {
            negativeLUT[i] = (short)(255 - i);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,negativeLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg,outImg);

        return outImg;
    }

    public static BufferedImage applyMask(BufferedImage inImg, BufferedImage maskImg){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(), inImg.getType());

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++)
                if(maskImg.getRaster().getSample(x,y,0) > 0 ){
                    int pixel = inImg.getRGB(x,y);
                    outImg.setRGB(x,y,pixel);
                }
        return outImg;
    }

    static public BufferedImage getBitPlane(BufferedImage inImg, int bitLevel){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++) {
                int pixel = inImg.getRaster().getSample(x,y,0);
                pixel = (pixel >>> bitLevel) & 1;
                outImg.getRaster().setSample(x,y,0,pixel);
            }
        return outImg;
    }

    public static double[] histogramSimple(BufferedImage inImg, int band){
        double[] histogram = new double[256];

        for (int y = 0; y < inImg.getHeight(); y++)
            for (int x = 0; x < inImg.getWidth(); x++){
                int grayLevel = inImg.getRaster().getSample(x,y,band);
                histogram[grayLevel]++;
            }

        int totalNrOfPixels = inImg.getWidth()*inImg.getHeight();

        for (int i = 0; i < 256; i++) {
            histogram[i] = histogram[i] / totalNrOfPixels;
        }

        System.out.println(Arrays.toString(histogram));

        return histogram;
    }

    public static BufferedImage histogramImage(BufferedImage inImg, int band, int hWidth, int hHeight){
        BufferedImage outImg = new BufferedImage(hWidth, hHeight, BufferedImage.TYPE_INT_RGB);

        double[] histogram = histogramSimple(inImg, band);
        double max = 0;
        int stickWidth = hWidth/histogram.length;

        Graphics2D g2 = outImg.createGraphics();

        for (int i = 0; i < histogram.length; i++) {
            if(histogram[i] > max)
                max = histogram[i];
        }

        switch (band){
            case 0 -> g2.setColor(Color.RED);
            case 1 -> g2.setColor(Color.GREEN);
            case 2 -> g2.setColor(Color.BLUE);
        }

        if(inImg.getRaster().getNumBands() == 1)
            g2.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < histogram.length; i++) {
            g2.fillRect(i*stickWidth, hHeight - (int)((histogram[i]*hHeight)/max), stickWidth, hHeight);
//            g2.drawLine(i, hHeight, i, hHeight - (int)((histogram[i]*hHeight)/max));
        }

        g2.dispose();
        return outImg;
    }

    // normalize a value
    public static int normalize(int val, int oldMin, int oldMax, int newMin, int newMax){
        double c = 1.0 * (newMax - newMin)/(oldMax - oldMin);
        return (int)Math.round(c * (val-oldMin) + newMin);
    }

    // normalize a LUT
    static void normalize(short[] lut, int newMin, int newMax) {
        short max = Short.MIN_VALUE;
        short min = Short.MAX_VALUE;

        // find max and min
        for (int i = 0; i < lut.length; i++) {
            if (lut[i] > max)
                max = lut[i];
            if (lut[i] < min)
                min = lut[i];
        }

        // (x-min)(255-0)/(max-min)+0
        // (x-min)(newMax-newMin)/(max-min)+newMin
        double c = 1.0 * (newMax - newMin) / (max - min);

        System.out.println("\r\n"+"max= "+max+" min= "+min+" c= "+c );
        // build output
        for (int i = 0; i < lut.length; i++) {
            lut[i] = (short) ((lut[i] - min) * c + newMin);
            System.out.print(lut[i] + " ");
        }
        // System.out.println("\r\n"+"max= "+max+" min= "+min+" c= "+c );
    }

    public static BufferedImage contrastStretch(BufferedImage inImg){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),inImg.getType());

        short[][] contrastLUT = new short[inImg.getRaster().getNumBands()][256];

        for (int band = 0; band < inImg.getRaster().getNumBands(); band++) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            int pixel;

            // find max and min
            for (int y = 0; y < inImg.getHeight(); y++)
                for (int x = 0; x < inImg.getWidth(); x++) {
                    pixel = inImg.getRaster().getSample(x,y,band);
                    if(pixel > max)
                        max = pixel;
                    if(pixel < min)
                        min = pixel;
                }

            System.out.println("min= " + min + " max= " + max);

            for (int i = min; i <= max; i++) {
                contrastLUT[band][i] = (short)normalize(i,min, max,0,255);
            }
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0, contrastLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg, outImg);

        return outImg;
    }
}
