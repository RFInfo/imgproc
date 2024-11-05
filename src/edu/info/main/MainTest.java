package edu.info.main;

import edu.info.util.ImageUtil;

import static edu.info.util.ImageUtil.*;

import java.awt.image.BufferedImage;

public class MainTest {

    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
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

        ImageUtil.displayImage(ImageUtil.brightnessRGB(inputImg,-50,60, 0),"Brightness RGB");


    }
}
