package info.main;

import info.util.BrightnessDlg;
import info.util.BrightnessRGBDlg;

import static info.util.ImageUtil.*;

import java.awt.image.BufferedImage;

public class MainTest {
    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage img = loadImage(fileName);

        displayImage(img, "Original");


//        displayImage(extractBands(img, 'R'), "Band R");
//        displayImage(extractBands(img, 'G'), "Band G");
//        displayImage(extractBands(img, 'B'), "Band B");
//        displayImage(extractBands(img, 'A'), "Band A");

//        displayImage(grayLevelGenerator(0,10,1, 600));

//        displayImage(pixelate(img,64));


//    displayImage(toGray(img), "ToGray");

//        displayImage(toGray(img,0),"Gray g");
//        displayImage(toGray(img,1),"Gray sqrt");
//        displayImage(toGray(img,2),"Gray Avg");
//        displayImage(toGray(img,3),"Gray usual");
//        displayImage(toGray(img,4),"Gray PAL");

// brightness

//        displayImage(brightnessV1(img,150));
//        displayImage(brightnessV2(img,-50));
//        displayImage(brightnessV3(img,-50));

//        BufferedImage img1 = brightnessV1(img,150);
//        BufferedImage img2 = brightnessV1(img1,-150);
//        displayImage(img1);
//        displayImage(img2);

//        displayImage(applySettingsDlg(img, new BrightnessDlg()));

//        displayImage(brightnessRGB(img,-50,0,0));

        displayImage(applySettingsDlg(img, new BrightnessRGBDlg()));

    }
}
