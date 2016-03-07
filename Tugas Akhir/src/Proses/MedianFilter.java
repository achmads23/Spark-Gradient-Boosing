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
import java.util.Arrays;

/**
 *
 * @author achma
 */
public class MedianFilter extends StructuringElement{
    int shapeSize;
    public Color[][] structElem;
    public STRUCTURING_ELEMENT_SHAPE shape;
    
    public int median(Color[][] val, int sSize,STRUCTURING_ELEMENT_SHAPE shape, String RGB) {
        int median = 1;
        if(shape == STRUCTURING_ELEMENT_SHAPE.SQUARE || shape == STRUCTURING_ELEMENT_SHAPE.ROUND){
            sSize = sSize * sSize;
            median = 4;
        }
        int[] R = new int[sSize];
        int count = 0;
        for(int i = 0; i < val.length ; i++){
            for(int j = 0 ; j < val[i].length ; j++){
                if("R".equals(RGB)){
                    if(val[i][j] == null){
                        continue;
                    }
                    R[count] =  val[i][j].getRed();
                } else if("G".equals(RGB)){
                    if(val[i][j] == null){
                        continue;
                    }
                    R[count] =  val[i][j].getGreen();
                } else if("B".equals(RGB)){
                    if(val[i][j] == null){
                        continue;
                    }
                    R[count] =  val[i][j].getBlue();
                }
                count++;
            }
        }
        Arrays.sort(R);
        median = R[median];
        return median;
    }
    
    public BufferedImage MedianFilter(BufferedImage img,int shapeSize, STRUCTURING_ELEMENT_SHAPE shape){
        int width = img.getWidth();
        int height = img.getHeight();
        
        BufferedImage medianImage = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        
        int sSize = 2 * shapeSize + 1;
        
        int filterWidth = width - sSize;
        int filterHeight = height - sSize;
        int lowerSide = height - shapeSize;
        int rightSide = width - shapeSize;
        
        int newR = 0;
        int newG = 0;
        int newB = 0;
        
        //center
        for (int x = 0; x <= filterWidth; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,x,y);
                newR = median(structElem,sSize,shape,"R");
                newG = median(structElem,sSize,shape,"G");
                newB = median(structElem,sSize,shape,"B");
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x+shapeSize, y+shapeSize, newColor.getRGB());
            }
        }
        
        //leftborder
        for (int x = 0; x < shapeSize; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,0,y);
                newR = median(structElem,sSize,shape,"R");
                newG = median(structElem,sSize,shape,"G");
                newB = median(structElem,sSize,shape,"B");
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x, y + shapeSize, newColor.getRGB());
            }
        }
        
        //leftlowerside
        for (int x = 0 ; x <shapeSize;x++){
            for (int y = lowerSide ; y < height;y++){
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //leftupperside
        this.structElem = constructShape(img, shape, shapeSize,0,0);
        newR = median(structElem,sSize,shape,"R");
        newG = median(structElem,sSize,shape,"G");
        newB = median(structElem,sSize,shape,"B");
        for (int x = 0 ; x <shapeSize ; x++){
            for (int y = 0 ; y < shapeSize ; y++){
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //rightborder
        for (int x = rightSide; x < width; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,filterWidth,y);
                newR = median(structElem,sSize,shape,"R");
                newG = median(structElem,sSize,shape,"G");
                newB = median(structElem,sSize,shape,"B");
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x, y + shapeSize, newColor.getRGB());
            }
        }
        
        //rightlowerside
        for (int x = rightSide ; x <width;x++){
            for (int y = lowerSide ; y < height;y++){
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //lowerborder
        for (int y = lowerSide - 1; y < height; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,filterHeight);
                newR = median(structElem,sSize,shape,"R");
                newG = median(structElem,sSize,shape,"G");
                newB = median(structElem,sSize,shape,"B");
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        //upperborder
        for (int y = 0; y < shapeSize; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,0);
                newR = median(structElem,sSize,shape,"R");
                newG = median(structElem,sSize,shape,"G");
                newB = median(structElem,sSize,shape,"B");
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        for (int x = rightSide ; x <width;x++){
            for (int y = 0 ; y < shapeSize;y++){
                Color newColor = new Color(newR,newG,newB);
                medianImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return medianImage;
    }
}
