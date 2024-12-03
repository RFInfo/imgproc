package edu.info.main;

import edu.info.util.BrightnessDlg;
import edu.info.util.BrightnessRGBDlg;
import edu.info.util.ImageUtil;
import edu.info.util.ThresholdDlg;

import static edu.info.util.ImageUtil.*;

import java.awt.image.BufferedImage;

public class MainTest {

    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/ab pattern.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage inputImg = loadImage(fileName);

        displayImage(inputImg, "Imagine Originala");
//
//        ImageUtil.saveImage(inputImg, "outImg1.jpg", "jpg");
//        ImageUtil.saveImage(inputImg, "outImg1.bmp", "bmp");
//        ImageUtil.saveImage(inputImg, "outImg1.png", "png");
//        ImageUtil.saveImage(inputImg, "outImg1.gif", "gif");

//        ImageUtil.displayImage(ImageUtil.generateRandom(200,200),"Random");
//        displayImage(extractBand(inputImg,'R'),"R");
//        displayImage(extractBand(inputImg,'G'),"G");
//        displayImage(extractBand(inputImg,'B'),"B");
//        displayImage(extractBand(inputImg,'A'),"A");

        // lab05
//        ImageUtil.displayImage(ImageUtil.grayLevelGenerator(0,100,5,600),"GrayLevels");
//        ImageUtil.displayImage(ImageUtil.pixelate(inputImg,64),"Pixelate");

        // lab06
//        ImageUtil.displayImage(ImageUtil.brightnessV1(inputImg,-40),"Brightness -40");
//        ImageUtil.displayImage(ImageUtil.brightnessV1(inputImg,+40),"Brightness +40");
//
//        ImageUtil.displayImage(ImageUtil.brightnessV3(inputImg,-40),"Brightness -40");
//        ImageUtil.displayImage(ImageUtil.brightnessV3(inputImg,+40),"Brightness +40");

//        BufferedImage img1 = ImageUtil.brightnessV1(inputImg,+100);
//        BufferedImage img2 = ImageUtil.brightnessV1(img1,-100);
//
//        displayImage(img1);
//        displayImage(img2);

//        ImageUtil.displayImage(ImageUtil.brightnessRGB(inputImg,-50,60, 0),"Brightness RGB");

//        BufferedImage img1 = ImageUtil.brightnessRGB(inputImg,-40,0,0);
//        ImageUtil.displayImage(ImageUtil.contrast(inputImg,3.5f),"Contrast");

//        applySettingsDlg(inputImg, new BrightnessDlg());
//        applySettingsDlg(inputImg, new BrightnessRGBDlg());

        // lab07

//        displayImage(contrastGamma(inputImg,2.5), "Gamma 2.5");

//        displayImage(colorToGray(inputImg, GrayTransforms.GRAY_TRANSFORMS_AVG), "Color to Gray - AVG");
//        displayImage(colorToGray(inputImg, GrayTransforms.GRAY_TRANSFORMS_GREEN), "Color to Gray - G");
//        displayImage(colorToGray(inputImg, GrayTransforms.GRAY_TRANSFORMS_PAL), "Color to Gray - PAL");

        // toGray, toBinary, applyMask
//    BufferedImage grayImg = colorToGray(inputImg,GrayTransforms.GRAY_TRANSFORMS_PAL);
//    displayImage(grayImg, "Gray");
////    BufferedImage binaryImg = threshold(grayImg, 110);
//    BufferedImage binaryImg = applySettingsDlg(grayImg, new ThresholdDlg());
//    binaryImg = negative(binaryImg);
//    displayImage(binaryImg, "Binary");
//
//    BufferedImage resultImg = applyMask(grayImg,binaryImg);
//    displayImage(resultImg, "ApplyMask");

//        histogramSimple(inputImg,2);
//        displayImage(histogramImage(inputImg,0,256,200), "R");
//        displayImage(histogramImage(inputImg,1,256,200), "G");
//        displayImage(histogramImage(inputImg,2,256,200), "B");

        // lab08
//        inputImg = colorToGray(inputImg, GrayTransforms.GRAY_TRANSFORMS_PAL);
        BufferedImage contrastStretchImg = contrastStretch(inputImg);
        displayImage(contrastStretchImg,"ContrastStretch");

        displayImage(histogramImage(inputImg,0, 256,200));
        displayImage(histogramImage(contrastStretchImg,0, 256,200));
    }
}
