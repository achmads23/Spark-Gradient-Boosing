/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author achma
 */
public class Brigthness {
    public BufferedImage Brigthness(BufferedImage img,int increasingFactor){
        
        //size of input image
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage outImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        //Pixel by pixel navigation loop
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {


                //get the RGB component of input imge pixel
                Color color = new Color(img.getRGB(i, j));

                int r, g, b;

                //change the value of each component
                r = color.getRed() + increasingFactor;
                g = color.getGreen() + increasingFactor;
                b = color.getBlue() + increasingFactor;


                //r,g,b values which are out of the range 0 to 255 should set to 0 or 255
                if (r >= 256) {
                    r = 255;
                } else if (r < 0) {
                    r = 0;
                }

                if (g >= 256) {
                    g = 255;
                } else if (g < 0) {
                    g = 0;
                }

                if (b >= 256) {
                    b = 255;
                } else if (b < 0) {
                    b = 0;
                }

                //set output image pixel component
                outImage.setRGB(i, j, new Color(r, g, b).getRGB());

            }
        }
        return outImage;
    }
}
