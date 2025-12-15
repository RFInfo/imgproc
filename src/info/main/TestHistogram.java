package info.main;

import java.awt.image.BufferedImage;

import static info.util.ImageUtil.*;

public class TestHistogram {
    public static void main(String[] args) {
        String fileName = "./test_images/lena_color_256.bmp";
        BufferedImage inImg = loadImage(fileName);
        displayImage(inImg, "Original");

        histogramSimple(inImg,0);

        // histo RGB
        displayImage(histogramImage(inImg,0,256, 150), "RED");
        displayImage(histogramImage(inImg,1,256, 150), "GREEN");
        displayImage(histogramImage(inImg,2,256, 150), "BLUE");

        // histo luminosity
//        BufferedImage grayImg = toGray(inImg, GrayTransforms.GRAY_TRANSFORMS_PAL);
//        displayImage(grayImg, "Gray");
//
//        displayImage(histogramImage(grayImg,0,256, 150), "LUMINOSITY");

//        BufferedImage contrastStretchedImg = contrastStretch(grayImg);
//        displayImage(contrastStretchedImg, "Stretch");
//        displayImage(histogramImage(contrastStretchedImg,0,256, 150), "LUMINOSITY Stretch");

        BufferedImage contrastStretchedImg = contrastStretch(inImg);
        displayImage(contrastStretchedImg, "Stretch");
        displayImage(histogramImage(contrastStretchedImg,0,256, 150), "RED");
        displayImage(histogramImage(contrastStretchedImg,1,256, 150), "GREEN");
        displayImage(histogramImage(contrastStretchedImg,2,256, 150), "BLUE");


    }
}
