package info.main;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import static info.util.ImageUtil.*;

public class TestConvolve {
    public static void main(String[] args) {
        String fileName = "./test_images/lena_color_512.bmp";
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


        Kernel avgKernel = new Kernel(3,3, avg);
        int kSize = 25;
        float avg2[] = new float[kSize * kSize];
        for (int i = 0; i < kSize*kSize; i++) {
            avg2[i] = 1.0f / (kSize*kSize);
        }

        BufferedImage avgImg = convolutionSimple(img, avgKernel);
        displayImage(avgImg, "AVG");

//        BufferedImage avg2Img = convolutionSimple(img, new Kernel(kSize,kSize,avg2));
//        displayImage(avg2Img, "AVG 2");
//
//        BufferedImage avg3Img = convolution(img, new Kernel(kSize,kSize,avg2));
//        displayImage(avg3Img, "AVG 3");


        BufferedImage sharpImg = convolution(img, new Kernel(3,3, sharp));
        displayImage(sharpImg, "Sharp");

        BufferedImage edgeImg = convolution(img, new Kernel(3,3, edge));
        displayImage(edgeImg, "Edge");
    }
}
