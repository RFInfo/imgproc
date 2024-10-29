package edu.info.main;

import edu.info.util.ImageUtil;

import java.awt.image.BufferedImage;

public class MainTest {

    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";

        BufferedImage inputImg = ImageUtil.loadImage(fileName);

        ImageUtil.displayImage(inputImg, "Imagine Originala");
//
//        ImageUtil.saveImage(inputImg, "outImg1.jpg", "jpg");
//        ImageUtil.saveImage(inputImg, "outImg1.bmp", "bmp");
//        ImageUtil.saveImage(inputImg, "outImg1.png", "png");
//        ImageUtil.saveImage(inputImg, "outImg1.gif", "gif");

//        ImageUtil.displayImage(ImageUtil.generateRandom(200,200),"Random");
//        ImageUtil.displayImage(ImageUtil.extractBand(inputImg,'R'),"R");
//        ImageUtil.displayImage(ImageUtil.extractBand(inputImg,'G'),"G");
//        ImageUtil.displayImage(ImageUtil.extractBand(inputImg,'B'),"B");

        // lab05
//        ImageUtil.displayImage(ImageUtil.grayLevelGenerator(0,100,5,600),"GrayLevels");
        ImageUtil.displayImage(ImageUtil.pixelate(inputImg,64),"Pixelate");

    }
}
