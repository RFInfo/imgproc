package edu.info.main;

import java.awt.image.BufferedImage;

import static edu.info.util.Utils.*;

public class MainTest {
    static void main() {
        String fileName = "test_images/lena_color_512.bmp";
//        String fileName = "test_images/halloween.png";

        BufferedImage testImg = loadImage(fileName);

        displayImage(testImg, "Primul test");

//        displayImage(generateRandom(800,600), "Random pixels");

        // extract bands / channels / components / elements
//        displayImage(extractBands(testImg, 'A'), "Alpha");
//        displayImage(extractBands(testImg, 'R'), "Red");
//        displayImage(extractBands(testImg, 'G'), "Green");
//        displayImage(extractBands(testImg, 'B'), "Blue");

        // flip
//        displayImage(flipV(testImg), "Flip Vertical");
//        displayImage(flipH(testImg), "Flip Horizontal");
//        displayImage(flipV(flipH(testImg)), "Flip Horizontal Vertical");

        // pixelate
//        displayImage(pixelate(testImg,4), "Pixelate 4");
//        displayImage(pixelate(testImg,16), "Pixelate 16");
//        displayImage(pixelate(testImg,32), "Pixelate 32");
//        displayImage(pixelate(testImg,128), "Pixelate 128");

        // gray level 0 - 255
        displayImage(grayLevelGenerator(0,40,1, 600), "GrayLevel");
    }



}
