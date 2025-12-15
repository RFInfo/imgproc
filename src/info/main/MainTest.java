package info.main;

import info.util.BrightnessDlg;
import info.util.BrightnessRGBDlg;
import info.util.GammaDlg;

import static info.util.ImageUtil.*;

import java.awt.image.BufferedImage;

public class MainTest {
    public static void main(String[] args) {

        String fileName = "./test_images/lena_color_512.bmp";
//        String fileName = "./test_images/halloween.png";

        BufferedImage inImg = loadImage(fileName);

        displayImage(inImg, "Original");


//        displayImage(extractBands(inImg, 'R'), "Band R");
//        displayImage(extractBands(inImg, 'G'), "Band G");
//        displayImage(extractBands(inImg, 'B'), "Band B");
//        displayImage(extractBands(inImg, 'A'), "Band A");

//        displayImage(grayLevelGenerator(0,10,1, 600));

//        displayImage(pixelate(inImg,64));


//    displayImage(toGray(inImg), "ToGray");

//        displayImage(toGray(inImg,0),"Gray g");
//        displayImage(toGray(inImg,1),"Gray sqrt");
//        displayImage(toGray(inImg,2),"Gray Avg");
//        displayImage(toGray(inImg,3),"Gray usual");
//        displayImage(toGray(inImg,4),"Gray PAL");

// brightness

//        displayImage(brightnessV1(inImg,150));
//        displayImage(brightnessV2(inImg,-50));
//        displayImage(brightnessV3(inImg,-50));

//        BufferedImage img1 = brightnessV1(inImg,150);
//        BufferedImage img2 = brightnessV1(img1,-150);
//        displayImage(img1);
//        displayImage(img2);

//        displayImage(applySettingsDlg(inImg, new BrightnessDlg()));

//        displayImage(brightnessRGB(inImg,-50,0,0));

//        displayImage(applySettingsDlg(inImg, new BrightnessRGBDlg()));

        // Contrast

        displayImage(contrast(inImg,1.5f));
//        displayImage(contrast(inImg,0.5f));
        displayImage(contrast(brightnessV3(inImg,-40),1.5f),"Brightness+Contrast");

        displayImage(contrastGamma(inImg,0.5d), "Gamma 1.5");

        displayImage(applySettingsDlg(inImg, new GammaDlg()), "Contrast Gamma");
    }
}
