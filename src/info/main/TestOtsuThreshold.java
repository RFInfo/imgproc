package info.main;

import java.awt.image.BufferedImage;

import static info.util.ImageUtil.*;

public class TestOtsuThreshold {

    public static void main(String[] args) {
//        String fileName = "./test_images/lena_gray_512.bmp";
        String fileName = "./test_images/rice.bmp";
//        String fileName = "./test_images/eight.bmp";

        BufferedImage inImg = loadImage(fileName);

        displayImage(inImg, "Original image");

        int otsuThreshold = otsuThreshold(inImg);

        displayImage(threshold(inImg,otsuThreshold), "OtsuThreshold: " + otsuThreshold);

    }

}
