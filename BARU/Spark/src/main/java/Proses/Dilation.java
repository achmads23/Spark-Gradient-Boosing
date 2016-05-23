/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author achma
 */
public class Dilation extends StructuringElement{
    int shapeSize;
    public Color[][] structElem;
    public STRUCTURING_ELEMENT_SHAPE shape;
    
    public BufferedImage Dilation(BufferedImage img,int shapeSize, STRUCTURING_ELEMENT_SHAPE shape){
        int width = img.getWidth();
        int height = img.getHeight();
        
        BufferedImage dilationImage = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        
        int sSize = 2 * shapeSize + 1;
        
        int filterWidth = width - sSize;
        int filterHeight = height - sSize;
        int lowerSide = height - shapeSize;
        int rightSide = width - shapeSize;
        
        int dilation = 0;
        
        //center
        for (int x = 0; x <= filterWidth; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,x,y);
                dilation = max(structElem,sSize,shape);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x+shapeSize, y+shapeSize, newColor.getRGB());
            }
        }
        
        //leftborder
        for (int x = 0; x < shapeSize; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,0,y);
                dilation = max(structElem,sSize,shape);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x, y + shapeSize, newColor.getRGB());
            }
        }
        
        //leftlowerside
        for (int x = 0 ; x <shapeSize;x++){
            for (int y = lowerSide ; y < height;y++){
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
         //leftupperside
        this.structElem = constructShape(img, shape, shapeSize,0,0);
        dilation = max(structElem,sSize,shape);
        for (int x = 0 ; x <shapeSize ; x++){
            for (int y = 0 ; y < shapeSize ; y++){
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //rightborder
        for (int x = rightSide; x < width; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,filterWidth,y);
                dilation = max(structElem,sSize,shape);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x, y + shapeSize, newColor.getRGB());
            }
        }
        
        //rightlowerside
        for (int x = rightSide ; x <width;x++){
            for (int y = lowerSide ; y < height;y++){
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //lowerborder
        for (int y = lowerSide - 1; y < height; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,filterHeight);
                dilation = max(structElem,sSize,shape);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        //upperborder
        for (int y = 0; y < shapeSize; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,0);
                dilation = max(structElem,sSize,shape);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        for (int x = rightSide ; x <width;x++){
            for (int y = 0 ; y < shapeSize;y++){
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        return dilationImage;
    }
    
    public int max(Color[][] val,int sSize,STRUCTURING_ELEMENT_SHAPE shape) {
        if(shape == STRUCTURING_ELEMENT_SHAPE.SQUARE || shape == STRUCTURING_ELEMENT_SHAPE.ROUND){
            sSize = sSize * sSize;
        }
        int[] R = new int[sSize];
        int[] G = new int[sSize];
        int[] B = new int[sSize];
        int count = 0;
        for(int i = 0; i < val.length ; i++){
            for(int j = 0 ; j < val[i].length ; j++){
                if(val[i][j] == null){
                    continue;
                }
                R[count] =  val[i][j].getRed();
                count++;
            }
        }
        
        int max = R[0];
        int end = R.length;
        int v = 0;
        for (int i = 1; i < end; i++) {
                if (R[i] < 0)
                        v = 256 + R[i];
                else
                        v = R[i];
                if (v > max)
                        max = v;
        }
        return max;
    }
}
