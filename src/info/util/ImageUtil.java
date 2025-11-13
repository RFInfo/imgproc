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

    public static int constrain(int val, int min, int max){
        return val > max ? max : (val < min ? min : val);
    }

    public static int constrain(int val){
        return constrain(val, 0, 255);
    }

    public static BufferedImage toGray(BufferedImage input){
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

    public static BufferedImage toGray(BufferedImage src, int version){
// 0 - g
// 1 - sqrt
// 2 - avg
// 3 - usual
// 4 - PAL
        BufferedImage dst = null;
        dst = new
                BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        for(int y=0; y<src.getHeight(); y++)
            for(int x=0; x<src.getWidth(); x++){
                int r = src.getRaster().getSample(x,y,0);
                int g = src.getRaster().getSample(x,y,1);
                int b = src.getRaster().getSample(x,y,2);
                int gray=0;
                switch (version){
                    case 0:{
                        gray = g;
                        break;
                    }
                    case 1:{
                        gray = constrain((int)Math.round(Math.sqrt(r*r+b*b+g*g)));
                        break;
                    }
                    case 2:{
                        gray = (r+b+g)/3;
                        break;
                    }
                    case 3:{
                        gray = (3*r+4*b+2*g)/9;
                        break;
                    }
                    case 4:{
                        gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);
                        break;
                    }
                }
                dst.getRaster().setSample(x,y,0,gray);
            }
        return dst;
    }


}
