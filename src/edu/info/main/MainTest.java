package edu.info.main;

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
        displayImage(extractBand(inputImg,'R'),"R");
        displayImage(extractBand(inputImg,'G'),"G");
        displayImage(extractBand(inputImg,'B'),"B");
        displayImage(extractBand(inputImg,'A'),"A");

    }
}
