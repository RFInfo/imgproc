package edu.info.main;

import java.awt.image.BufferedImage;

import static edu.info.util.ImageUtil.*;

public class TestOtsuTreshold {

    public static void main(String[] args) {
        BufferedImage inputImg= loadImage("./test_images/lena_gray_512.bmp");

        displayImage(inputImg, "Original image");

        int otsuThreshold = otsuTreshold(inputImg);

        displayImage(threshold(inputImg,otsuThreshold), "OtsuThreshold: " + otsuThreshold);

    }

}
