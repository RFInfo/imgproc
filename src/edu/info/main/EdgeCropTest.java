package edu.info.main;


import edu.info.util.ThresholdDlg;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import static edu.info.util.ImageUtil.*;

public class EdgeCropTest {

    public static void main(String[] args) {
        String inputFileName = "./test_images/rice.bmp";
        //        BufferedImage inputImg = ImageUtil.loadImage(inputFileName);
        BufferedImage inputImg = loadImage(inputFileName);

        //        ImageUtil.displayImage(inputImg,"Original image");
        displayImage(inputImg,"Original image");

        float[] avg = { 0.11f, 0.11f, 0.11f,
                        0.11f, 0.11f, 0.11f,
                        0.11f, 0.11f, 0.11f};

        float[] sharp = {   0.0f, -1.0f, 0.0f,
                            -1.0f, 5.0f, -1.0f,
                            0.0f, -1.0f, 0.0f};

        float[] edge = {    0.0f, -1.0f, 0.0f,
                            -1.0f, 4.0f, -1.0f,
                            0.0f, -1.0f, 0.0f};


        Kernel avgKernel = new Kernel(3,3 ,avg);

        BufferedImage grayImg = colorToGray(inputImg, GrayTransforms.GRAY_TRANSFORMS_PAL);
        displayImage(grayImg, "Gray");

        BufferedImage blurImg = convolutionSimple(grayImg,new Kernel(3,3,avg));
        displayImage(blurImg, "Blur");

        BufferedImage thresholdImg = applySettingsDlg(blurImg, new ThresholdDlg());
        displayImage(thresholdImg, "Binary");

        BufferedImage out = applyMask(inputImg, thresholdImg);
        displayImage(out, "Final");

//        BufferedImage sharpImg = convolutionSimple(inputImg,new Kernel(3,3,sharp));
//        displayImage(sharpImg, "Sharp");
//
//        BufferedImage edgeImg = convolutionSimple(thresholdImg,new Kernel(3,3,edge));
//        displayImage(edgeImg, "Edge");
    }

}
