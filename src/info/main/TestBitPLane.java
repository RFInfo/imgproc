package info.main;

import java.awt.image.BufferedImage;

import static info.util.ImageUtil.*;

public class TestBitPLane {
    public static void main(String[] args) {
        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage img = loadImage(fileName);

        displayImage(img, "Original");

        BufferedImage grayImg = toGray(img, 4);
        displayImage(grayImg, "Gray");

        displayImage(getBitPlane(grayImg,0), "BitPlane 0");
        displayImage(getBitPlane(grayImg,1), "BitPlane 1");
        displayImage(getBitPlane(grayImg,2), "BitPlane 2");
        displayImage(getBitPlane(grayImg,3), "BitPlane 3");
        displayImage(getBitPlane(grayImg,4), "BitPlane 4");
        displayImage(getBitPlane(grayImg,5), "BitPlane 5");
        displayImage(getBitPlane(grayImg,6), "BitPlane 6");
        displayImage(getBitPlane(grayImg,7), "BitPlane 7");
    }
}
