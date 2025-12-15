package info.main;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import static info.util.ImageUtil.*;

public class TestAffineTransforms {

    public static void main(String[] args) throws InterruptedException {


//        BufferedImage inImg = loadImage("./test_images/lena_color_512.bmp");
//        BufferedImage inImg = loadImage("./test_images/lena_gray_256.bmp");
        BufferedImage inImg = loadImage("./test_images/alumgrns.bmp");

        displayImage(inImg, "Original");

        displayImage(scale(inImg,2,2, AffineTransformOp.TYPE_NEAREST_NEIGHBOR), "TYPE_NEAREST_NEIGHBOR");
        displayImage(scale(inImg,2,2, AffineTransformOp.TYPE_BILINEAR), "TYPE_BILINEAR");
        displayImage(scale(inImg,2,2, AffineTransformOp.TYPE_BICUBIC), "TYPE_BICUBIC");


        float[] sharp = {0.0f, -1.0f, 0.0f,
                        -1.0f, 5.0f, -1.0f,
                        0.0f, -1.0f, 0.0f};

        BufferedImage test;

        test = scale(inImg, 2, 2, AffineTransformOp.TYPE_BICUBIC);
        displayImage(test, "Scale 2x, Bicubic");

        test = contrastStretch(test);
        displayImage(test, "Contrast Stretch");

        test = convolutionSimple(test, new Kernel(3, 3, sharp));
        displayImage(test, "Convolve Sharpening");


        displayImage(resize(inImg, 1024, 0, AffineTransformOp.TYPE_BICUBIC), "Resize, Bicubic");

        displayImage(rotate(inImg, 30, AffineTransformOp.TYPE_BICUBIC), "Rotate 30, Bicubic");
    }
}
