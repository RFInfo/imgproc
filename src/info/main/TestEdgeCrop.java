package info.main;

import info.util.ThresholdDlg;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import static info.util.ImageUtil.*;
import static info.util.ImageUtil.convolution;
import static info.util.ImageUtil.displayImage;

public class TestEdgeCrop {
    public static void main(String[] args) {
//        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/rice.bmp";
        String fileName = "./test_images/eight.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage img = loadImage(fileName);

        displayImage(img, "Original");

        float[] avg = { 0.11f, 0.11f, 0.11f,
                0.11f, 0.11f, 0.11f,
                0.11f, 0.11f, 0.11f};

        float[] sharp = {   0.0f, -1.0f, 0.0f,
                -1.0f, 5.0f, -1.0f,
                0.0f, -1.0f, 0.0f};

        float[] edge = {   0.0f, -1.0f, 0.0f,
                -1.0f, 4.0f, -1.0f,
                0.0f, -1.0f, 0.0f};

        BufferedImage grayImg = toGray(img, GrayTransforms.GRAY_TRANSFORMS_PAL);
        displayImage(grayImg, "Gray");

        BufferedImage avgImg = convolutionSimple(grayImg, new Kernel(3,3,avg));
        displayImage(avgImg, "AVG");

        BufferedImage thresholdImg = applySettingsDlg(avgImg, new ThresholdDlg());
        displayImage(thresholdImg, "Threshold");

        BufferedImage outImg = applyMask(img, thresholdImg);
        displayImage(outImg, "Apply Mask");

        BufferedImage edgeImg = convolution(outImg, new Kernel(3,3,edge));
        displayImage(edgeImg, "Sparp");

        BufferedImage edge2Img = convolution(thresholdImg, new Kernel(3,3,edge));
        displayImage(edge2Img, "Sparp");







//        BufferedImage sharpImg = convolution(img, new Kernel(3,3, sharp));
//        displayImage(sharpImg, "Sharp");
//
//        BufferedImage edgeImg = convolution(img, new Kernel(3,3, edge));
//        displayImage(edgeImg, "Edge");
    }
}
