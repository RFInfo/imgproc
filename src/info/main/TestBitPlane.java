package info.main;

import java.awt.image.BufferedImage;
import static info.util.ImageUtil.*;
import static info.util.ImageUtil.GrayTransforms.*;

public class TestBitPlane {
    public static void main(String[] args) {
        String fileName = "./test_images/lena_color_512.bmp";

        BufferedImage inImg = loadImage(fileName);

        displayImage(inImg, "Original");

        BufferedImage grayImg = toGray(inImg, GRAY_TRANSFORMS_PAL);
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
