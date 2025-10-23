package info.main;

import static info.util.ImageUtil.*;

import java.awt.image.BufferedImage;

public class MainTest {
    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";

        BufferedImage img = loadImage(fileName);

        displayImage(img, "Original");


//        displayImage(extractBands(img, 'R'), "Band R");
//        displayImage(extractBands(img, 'G'), "Band G");
//        displayImage(extractBands(img, 'B'), "Band B");

        displayImage(grayLevelGenerator(0,10,1, 600));



    }
}
