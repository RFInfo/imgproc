package edu.info.main;

import edu.info.util.ThresholdDlg;

import java.awt.image.BufferedImage;

import static edu.info.util.Utils.*;

public class ColorGrayBinaryMaskTest {
    static void main() {
//        String fileName = "test_images/rice.bmp";
//        String fileName = "test_images/eight.bmp";
//        String fileName = "test_images/lena_gray_512.bmp";
        String fileName = "test_images/ab pattern.bmp";
//        String fileName = "test_images/halloween.png";

        BufferedImage inImg = loadImage(fileName);

        displayImage(inImg, "Original");

        BufferedImage grayImg = toGray(inImg, GrayTransforms.GRAY_TRANSFORMS_PAL);
        displayImage(grayImg, "Gray");

//        BufferedImage binaryImg = threshold(inImg, 110);
        BufferedImage binaryImg = applySettingsDlg(grayImg, new ThresholdDlg());
        binaryImg = negative(binaryImg);
        displayImage(binaryImg, "Threshold");

        BufferedImage resultImg = applyMask(inImg,binaryImg);
        displayImage(resultImg, "Apply Binary Mask");
    }
}
