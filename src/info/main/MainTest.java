package info.main;

import info.util.ImageUtil;

import java.awt.image.BufferedImage;

public class MainTest {
    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";

        BufferedImage img = ImageUtil.loadImage(fileName);

        ImageUtil.displayImage(img, "Original");

    }
}
