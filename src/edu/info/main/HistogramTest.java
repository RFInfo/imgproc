package edu.info.main;

import edu.info.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static edu.info.util.ImageUtil.*;
import static edu.info.util.ImageUtil.histogramImage;

public class HistogramTest {
    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/ab pattern.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage inputImg = loadImage(fileName);

        displayImage(inputImg, "Imagine Originala");

        displayImage(extractBand(inputImg,'R'),"R");
        displayImage(extractBand(inputImg,'G'),"G");
        displayImage(extractBand(inputImg,'B'),"B");

        // print histogram values for band 2 (Blue)
        for(double v : histogramSimple(inputImg, 2))
            System.out.print(String.format("%, .5f",v));


        displayImage(histogramImage(inputImg,0,256,200), "R");
        displayImage(histogramImage(inputImg,1,256,200), "G");
        displayImage(histogramImage(inputImg,2,256,200), "B");
    }
}
