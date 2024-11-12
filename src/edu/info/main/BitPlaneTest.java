package edu.info.main;

import java.awt.image.BufferedImage;

import static edu.info.util.ImageUtil.*;

public class BitPlaneTest {
    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
        BufferedImage inputImg = loadImage(fileName);

        displayImage(inputImg, "Imagine Originala");

        BufferedImage grayImg = colorToGray(inputImg, GrayTransforms.GRAY_TRANSFORMS_PAL);
        displayImage(grayImg, "Imagine Gray");

        displayImage(getBitPlane(grayImg,0), "Level 0");
        displayImage(getBitPlane(grayImg,1), "Level 1");
        displayImage(getBitPlane(grayImg,2), "Level 2");
        displayImage(getBitPlane(grayImg,3), "Level 3");
        displayImage(getBitPlane(grayImg,4), "Level 4");
        displayImage(getBitPlane(grayImg,5), "Level 5");
        displayImage(getBitPlane(grayImg,6), "Level 6");
        displayImage(getBitPlane(grayImg,7), "Level 7");
    }


}
