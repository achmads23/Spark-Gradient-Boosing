/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author achma
 */
public class RGBtoHSB {
    
    public BufferedImage RGBtoHSB(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        
        BufferedImage HSBImage = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        for (int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                Color pixel = new Color(img.getRGB(j,i));
                int Red;
                int Blue;
                int Green;
                Red = pixel.getRed();
                Blue = pixel.getBlue();
                Green = pixel.getGreen();
                float[] hsb = Color.RGBtoHSB(Red, Green, Blue, null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];
                Color newColor = new Color(hue,saturation,brightness);
                HSBImage.setRGB(j, i, newColor.getRGB());
            }
        }
        return HSBImage;
    }
    
    public BufferedImage Saturation(BufferedImage img) throws FileNotFoundException, UnsupportedEncodingException{
        int width = img.getWidth();
        int height = img.getHeight();
//        System.out.println(width);
//        System.out.println(height);
        int Red;
        int Blue;
        int Green;
        
        BufferedImage HSBImage = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        
        PrintWriter writer = new PrintWriter("Saturation.txt", "UTF-8");
        PrintWriter writer2 = new PrintWriter("RGBSaturation.txt", "UTF-8");
        
        for (int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                Color pixel = new Color(img.getRGB(j,i));
                Red = pixel.getRed();
                Blue = pixel.getBlue();
                Green = pixel.getGreen();
                float[] hsb = Color.RGBtoHSB(Red, Green, Blue, null);
                float saturation = hsb[1];
                writer.print(saturation*255 + " ");   
                Color newColor = new Color(saturation,saturation,saturation);
                HSBImage.setRGB(j, i, newColor.getRGB());
                Color pixel2 = new Color(HSBImage.getRGB(j,i));
                writer2.print(pixel2.getRed() + " ");
            }
            writer.println("");
            writer2.println("");
        }
        writer.close();
        writer2.close();

        return HSBImage;
    }
}
