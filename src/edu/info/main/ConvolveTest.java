package edu.info.main;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import static edu.info.util.ImageUtil.*;

public class ConvolveTest {

    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/ab pattern.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage inputImg = loadImage(fileName);

        displayImage(inputImg, "Imagine Originala");


        float[] avg ={  0.11f, 0.11f, 0.11f,
                        0.11f, 0.11f, 0.11f,
                        0.11f, 0.11f, 0.11f };

        float[] sharp ={  0.0f, -1.0f, 0.0f,
                         -1.0f, 5.0f, -1.0f,
                         0.0f, -1.0f, 0.0f };

        float[] edge ={  0.0f, -1.0f, 0.0f,
                         -1.0f, 4.0f, -1.0f,
                         0.0f, -1.0f, 0.0f };

        Kernel avgKernel = new Kernel(3,3, avg);

        BufferedImage avgImg = convolutionSimple(inputImg,avgKernel);

        displayImage(avgImg, "AVG 3x3");

        int kSize = 25;
        float[] avg2 = new float[kSize*kSize];
        for (int i = 0; i < avg2.length; i++)
            avg2[i] = 1.0f / (kSize*kSize);

        BufferedImage avgImg2 = convolutionSimple(inputImg,new Kernel(kSize, kSize,avg2));
        displayImage(avgImg2, "AVG1");

        BufferedImage avgImg3 = convolution(inputImg,new Kernel(kSize, kSize,avg2));
        displayImage(avgImg3, "AVG2");

//        BufferedImage sharpImg = convolutionSimple(inputImg,new Kernel(3, 3,sharp));
//        displayImage(sharpImg, "Sharp");
//
//        BufferedImage edgeImg = convolutionSimple(inputImg,new Kernel(3, 3,edge));
//        displayImage(edgeImg, "Edge");
    }
}
