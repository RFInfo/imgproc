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
        displayImage(extractBands(testImg, 'R'), "Red");
        displayImage(extractBands(testImg, 'G'), "Green");
        displayImage(extractBands(testImg, 'B'), "Blue");
    }
}
