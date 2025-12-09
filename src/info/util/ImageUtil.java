package info.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
        if(src.getRaster().getNumBands() == 1)
            return src;

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

    public static BufferedImage brightnessV1(BufferedImage inImg, int offset){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),inImg.getType());

        for (int band = 0; band < inImg.getRaster().getNumBands(); band++)
            for (int y = 0; y < inImg.getHeight(); y++)
                for (int x = 0; x < inImg.getWidth(); x++) {
                    int inGrayLevel = inImg.getRaster().getSample(x,y,band);
                    int outGrayLevel = constrain(inGrayLevel + offset);
                    outImg.getRaster().setSample(x,y,band,outGrayLevel);
                }

        return outImg;
    }

    public static BufferedImage brightnessV2(BufferedImage inImg, int offset){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),inImg.getType());

        short[] brightnessLUT = new short[256];

        for (int i = 0; i < 256; i++) {
            brightnessLUT[i] = (short)constrain(i+offset);
            System.out.print(brightnessLUT[i] + " ");
        }

        for (int band = 0; band < inImg.getRaster().getNumBands(); band++)
            for (int y = 0; y < inImg.getHeight(); y++)
                for (int x = 0; x < inImg.getWidth(); x++) {
                    int inGrayLevel = inImg.getRaster().getSample(x,y,band);
                    int outGrayLevel = brightnessLUT[inGrayLevel];
                    outImg.getRaster().setSample(x,y,band,outGrayLevel);
                }

        return outImg;
    }

    public static BufferedImage brightnessV3(BufferedImage inImg, int offset){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),inImg.getType());

        short[] brightnessLUT = new short[256];

        for (int i = 0; i < 256; i++) {
            brightnessLUT[i] = (short)constrain(i+offset);
            System.out.print(brightnessLUT[i] + " ");
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,brightnessLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable,null);
        lookupOp.filter(inImg,outImg);

        return outImg;
    }

    public static BufferedImage brightnessRGB(BufferedImage inImg, int offsetR, int offsetG, int offsetB){
        BufferedImage outImg = new BufferedImage(inImg.getWidth(),inImg.getHeight(),inImg.getType());

        short[] rLUT = new short[256];
        short[] gLUT = new short[256];
        short[] bLUT = new short[256];

        short[][] rgbLUT = {rLUT, gLUT, bLUT};

        for (int i = 0; i < 256; i++) {
            rLUT[i] = (short)constrain(i+offsetR);
            gLUT[i] = (short)constrain(i+offsetG);
            bLUT[i] = (short)constrain(i+offsetB);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0,rgbLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable,null);
        lookupOp.filter(inImg,outImg);

        return outImg;
    }

    public static BufferedImage contrast(BufferedImage inImg, float scale) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] contrastLUT = new short[256];

        for (int i = 0; i < 256; i++) {
            contrastLUT[i] = (short) constrain(Math.round(scale * i));
            System.out.println(i + " " + contrastLUT[i]);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0, contrastLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg, outImg);

        return outImg;
    }

    public static BufferedImage contrastGamma(BufferedImage inImg, double gamma) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] contrastLUT = new short[256];

        for (int i = 0; i < 256; i++) {

            double a = i / 255.0; // scale to [0.0 ... 1.0]
            double b = Math.pow(a, 1.0/gamma); // aplicarea functiei gamma
            double c = b * 255.0; // scale to [0 ... 255]

            contrastLUT[i] = (short) constrain((int)Math.round(c));
            System.out.println(i + " " + contrastLUT[i]);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0, contrastLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg, outImg);

        return outImg;
    }

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
    public static BufferedImage threshold(BufferedImage inImg, int threshold) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        short[] thresholdLUT = new short[256];

        for (int i = 0; i < 256; i++) {
            thresholdLUT[i] = (short) ((i < threshold) ? 0 : 255);
            System.out.println(i + " " + thresholdLUT[i]);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0, thresholdLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg, outImg);

        return outImg;
    }

    public static BufferedImage negative(BufferedImage inImg) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] negative = new short[256];

        for (int i = 0; i < 256; i++) {
            negative[i] = (short) (255 - i);
            System.out.println(i + " " + negative[i]);
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0, negative);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg, outImg);

        return outImg;
    }

    public static BufferedImage applyMask(BufferedImage inImage, BufferedImage maskImg) {
        BufferedImage outImg = new BufferedImage(inImage.getWidth(), inImage.getHeight(), inImage.getType());

        for (int y = 0; y < inImage.getHeight(); y++)
            for (int x = 0; x < inImage.getWidth(); x++) {
                if (maskImg.getRaster().getSample(x, y, 0) > 0) {
                    int pixel = inImage.getRGB(x, y);
                    outImg.setRGB(x, y, pixel);
                }
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
            for (int x = 0; x < inImg.getWidth(); x++) {
                int gray = inImg.getRaster().getSample(x,y, band);
                histogram[gray]++;
            }

        System.out.println(Arrays.toString(histogram));

        // normalize
        int totalNrOfPixels  = inImg.getWidth() * inImg.getWidth();

        for (int i = 0; i <histogram.length; i++) {
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

    public static BufferedImage convolutionSimple(BufferedImage inImg, Kernel kernel) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        // kernel properties
        // kernel size 2n+1 x 2n+1
        int kWidth = kernel.getWidth();
        int kRadius = kWidth / 2;
        float[] kData = kernel.getKernelData(null);
        int kDataIndex = 0;

        for (int band = 0; band < inImg.getRaster().getNumBands() && band < 3; band++)
            for (int y = 0; y < inImg.getHeight(); y++)
                for (int x = 0; x < inImg.getWidth(); x++) {

                    float gray = 0;
                    kDataIndex = 0;
                    // summing neighborhood
                    for (int ky = -kRadius; ky <= kRadius ; ky++)
                        for (int kx = -kRadius; kx <= kRadius ; kx++){
                            if((x+kx) < 0 || (x+kx) > inImg.getWidth()-1 || (y+ky) < 0 || (y+ky) > inImg.getHeight()-1){
                                gray+=0;
//                                gray += kData[kDataIndex] * inImg.getRaster().getSample(x, y, band);
                            }
                            else {
                                gray+= kData[kDataIndex] * inImg.getRaster().getSample(x+kx,y+ky,band);
                            }
                            kDataIndex++;
                        }
                    outImg.getRaster().setSample(x,y,band,constrain(Math.round(gray)));

                }

        return outImg;
    }

    public static BufferedImage convolution(BufferedImage inImg, Kernel kernel) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

//        ConvolveOp convolveOp = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP,null);
        ConvolveOp convolveOp = new ConvolveOp(kernel,ConvolveOp.EDGE_ZERO_FILL,null);
        convolveOp.filter(inImg,outImg);

        return outImg;
    }

}
