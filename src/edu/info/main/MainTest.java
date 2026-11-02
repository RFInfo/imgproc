package edu.info.main;

import edu.info.util.BrightnessDlg;
import edu.info.util.BrightnessRGBDlg;
import edu.info.util.GammaDlg;

import java.awt.image.BufferedImage;

import static edu.info.util.Utils.*;

public class MainTest {
    static void main() {
        String fileName = "test_images/lena_color_512.bmp";
//        String fileName = "test_images/halloween.png";

        BufferedImage inImg = loadImage(fileName);

        displayImage(inImg, "Primul test");

//        displayImage(generateRandom(800,600), "Random pixels");

        // extract bands / channels / components / elements
//        displayImage(extractBands(inImg, 'A'), "Alpha");
//        displayImage(extractBands(inImg, 'R'), "Red");
//        displayImage(extractBands(inImg, 'G'), "Green");
//        displayImage(extractBands(inImg, 'B'), "Blue");

        // flip
//        displayImage(flipV(inImg), "Flip Vertical");
//        displayImage(flipH(inImg), "Flip Horizontal");
//        displayImage(flipV(flipH(inImg)), "Flip Horizontal Vertical");

        // pixelate
//        displayImage(pixelate(inImg,4), "Pixelate 4");
//        displayImage(pixelate(inImg,16), "Pixelate 16");
//        displayImage(pixelate(inImg,32), "Pixelate 32");
//        displayImage(pixelate(inImg,128), "Pixelate 128");

        // gray level 0 - 255
//        displayImage(grayLevelGenerator(0,40,1, 600), "GrayLevel");

        // to gray
//        displayImage(toGrayAVG(inImg), "To Gray AVG");

//        displayImage(toGray(inImg,GrayTransforms.GRAY_TRANSFORMS_GREEN),"Gray g");
//        displayImage(toGray(inImg,GrayTransforms.GRAY_TRANSFORMS_SQRT),"Gray sqrt");
//        displayImage(toGray(inImg,GrayTransforms.GRAY_TRANSFORMS_AVG),"Gray Avg");
//        displayImage(toGray(inImg,GrayTransforms.GRAY_TRANSFORMS_USUAL),"Gray usual");
//        displayImage(toGray(inImg,GrayTransforms.GRAY_TRANSFORMS_PAL),"Gray PAL");
//
//        displayImage(toGrayscaleFast(inImg),"Gray Fast");

    // brightness
//        displayImage(brightnessV1(inImg, 50), "Brightness +50");
//        displayImage(brightnessV1(inImg, -50), "Brightness -50");

//        displayImage(brightnessV2(inImg, 50), "Brightness +50");
//        displayImage(brightnessV2(inImg, -50), "Brightness -50");

//        displayImage(applySettingsDlg(inImg, new BrightnessDlg()));

//        displayImage(brightnessRGB(inImg,255,0,255));

//        displayImage(applySettingsDlg(inImg, new BrightnessRGBDlg()));

        // contrast

//        displayImage(contrast(inImg,1.5f), "Contrast 1.5");
//        displayImage(contrast(inImg,0.5f), "Contrast 0.5");

        // contrast gamma

//        displayImage(contrastGamma(inImg, 0.5), "Gamma 0.5");
//        displayImage(contrastGamma(inImg, 1.5), "Gamma 1.5");

        displayImage(applySettingsDlg(inImg, new GammaDlg()));

    }



}
