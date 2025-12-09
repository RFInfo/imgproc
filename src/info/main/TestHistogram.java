package info.main;

import java.awt.image.BufferedImage;

import static info.util.ImageUtil.*;

public class TestHistogram {
    public static void main(String[] args) {
        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/halloween.png";
        BufferedImage img = loadImage(fileName);
        displayImage(img, "Original");

        histogramSimple(img,0);

        // histo RGB
        displayImage(histogramImage(img,0,256, 150), "RED");
        displayImage(histogramImage(img,1,256, 150), "GREEN");
        displayImage(histogramImage(img,2,256, 150), "BLUE");

        // histo luminosity
//        BufferedImage grayImg = toGray(img, 4);
//        displayImage(grayImg, "Gray");
//
//        displayImage(histogramImage(grayImg,0,256, 150), "LUMINOSUTY");

//        BufferedImage contrastStretchImg = contrastStretch(grayImg);
//        displayImage(contrastStretchImg, "Stretch");
//        displayImage(histogramImage(contrastStretchImg,0,256, 150), "LUMINOSUTY Stretch");

        BufferedImage contrastStretchImg = contrastStretch(img);
        displayImage(contrastStretchImg, "Stretch");
        displayImage(histogramImage(contrastStretchImg,0,256, 150), "RED");
        displayImage(histogramImage(contrastStretchImg,1,256, 150), "GREEN");
        displayImage(histogramImage(contrastStretchImg,2,256, 150), "BLUE");


    }
}
