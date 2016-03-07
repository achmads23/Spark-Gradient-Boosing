/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author achma
 */
public class Threshold {

    public static int[] Histogram(BufferedImage img) {
        int[] histogram = new int[256];
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int red = new Color(img.getRGB(i, j)).getRed();
                histogram[red]++;
            }
        }
        return histogram;
    }

    public int OtsuThreshold(BufferedImage img) {
        int[] histogram = Histogram(img);
        int width = img.getWidth();
        int height = img.getHeight();
        int total = width * height;

        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }
        
        float sumB = 0;
        int wB = 0; //weight background
        int wF = 0; //weight foreground

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) {
                continue;
            }
            wF = total - wB;

            if (wF == 0) {
                break;
            }

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB; //mean Background
            float mF = (sum - sumB) / wF; //mean foreground

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }
    
    public BufferedImage Threshold(BufferedImage img) {
        return Threshold(img, OtsuThreshold(img));
    }
    
    public BufferedImage Threshold(BufferedImage img, int thres) {
        int width = img.getWidth();
        int height = img.getHeight();
        int Red;
        int Blue;
        int Green;
        
        BufferedImage ThresholdImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixel = new Color(img.getRGB(j, i));
                Red = pixel.getRed();
                Blue = pixel.getBlue();
                Green = pixel.getGreen();
                int avg = (Red + Green + Blue) / 3;
                Color newColor;
                if (avg < thres) {
                    newColor = new Color(0, 0, 0);
                } else {
                    newColor = new Color(255, 255, 255);
                }
                ThresholdImage.setRGB(j, i, newColor.getRGB());
            }
        }

        return ThresholdImage;
    }

}
