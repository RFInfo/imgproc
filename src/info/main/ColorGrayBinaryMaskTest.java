package info.main;

import info.util.ThresholdDlg;

import java.awt.image.BufferedImage;

import static info.util.ImageUtil.*;

public class ColorGrayBinaryMaskTest {
    public static void main(String[] args) {

//        String fileName = "./test_images/rice.bmp";
        String fileName = "./test_images/ab pattern.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage inputImg = loadImage(fileName);

        displayImage(inputImg, "Imagine Originala");


    BufferedImage grayImg = toGray(inputImg,GrayTransforms.GRAY_TRANSFORMS_PAL);
    displayImage(grayImg, "Gray");
//    BufferedImage binaryImg = threshold(grayImg, 110);
    BufferedImage binaryImg = applySettingsDlg(grayImg, new ThresholdDlg());
    binaryImg = negative(binaryImg);
    displayImage(binaryImg, "Binary");

    BufferedImage resultImg = applyMask(grayImg,binaryImg);
    displayImage(resultImg, "ApplyMask");
    }
}
