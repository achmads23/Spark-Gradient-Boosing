/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author achma
 */
public class HighPassFilter extends StructuringElement{
    int shapeSize;
    public Color[][] structElem;
    public STRUCTURING_ELEMENT_SHAPE shape;
    
    double[][] matrix = {{0,-1,0},
                      {-1,6,-1},
                      {0,-1,0}};
    
    public double[] HPF(Color[][] val,STRUCTURING_ELEMENT_SHAPE shape) {
        double[] hpf = new double[3];
        hpf[0] = 0;
        hpf[1] = 0;
        hpf[2] = 0;
        for(int x = 0 ; x < 3 ; x++){
            for(int y = 0 ; y < 3 ; y++){
                hpf[0] = hpf[0] + (val[x][y].getRed() * matrix[x][y]);
                hpf[1] = hpf[1] + (val[x][y].getGreen() * matrix[x][y]);
                hpf[2] = hpf[2] + (val[x][y].getBlue() * matrix[x][y]);
            }
        }
        
        if (hpf[0] > 255){
            hpf[0] = 255;
        } else if (hpf[0] < 0){
            hpf[0] = 0;
        }
        if (hpf[1] > 255){
            hpf[1] = 255;
        } else if (hpf[1] < 0){
            hpf[1] = 0;
        }

        if (hpf[2] > 255){
            hpf[2] = 255;
        } else if (hpf[2] < 0){
            hpf[2] = 0;
        }
        return hpf;
    }
    
    public BufferedImage HighPassFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        
        BufferedImage HPFImage = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        shapeSize = 1;
        shape = StructuringElement.STRUCTURING_ELEMENT_SHAPE.SQUARE;
        
        int sSize = 2 * shapeSize + 1;
        
        int filterWidth = width - sSize;
        int filterHeight = height - sSize;
        int lowerSide = height - shapeSize;
        int rightSide = width - shapeSize;
        
        double[] hpf = new double[3];
        
        //center
        for (int x = 0; x <= filterWidth; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,x,y);
                hpf = HPF(structElem,shape);
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x+shapeSize, y+shapeSize, newColor.getRGB());
            }
        }
        
        
        //leftborder
        for (int x = 0; x < shapeSize; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,0,y);
                hpf = HPF(structElem,shape);
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x, y+shapeSize, newColor.getRGB());
            }
        }
        
        //leftlowerside
        for (int x = 0 ; x <shapeSize;x++){
            for (int y = lowerSide ; y < height;y++){
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //leftupperside
        this.structElem = constructShape(img, shape, shapeSize,0,0);
        hpf = HPF(structElem,shape);
        for (int x = 0 ; x <shapeSize ; x++){
            for (int y = 0 ; y < shapeSize ; y++){
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //rightborder
        for (int x = rightSide; x < width; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,filterWidth,y);
                hpf = HPF(structElem,shape);
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x, y+shapeSize, newColor.getRGB());
            }
        }
        
        //rightlowerside
        for (int x = rightSide ; x <width;x++){
            for (int y = lowerSide ; y < height;y++){
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        //lowerborder
        for (int y = lowerSide - 1; y < height; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,filterHeight);
                hpf = HPF(structElem,shape);
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        //upperborder
        for (int y = 0; y < shapeSize; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,0);
                hpf = HPF(structElem,shape);
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        for (int x = rightSide ; x <width;x++){
            for (int y = 0 ; y < shapeSize;y++){
                Color newColor = new Color((int)hpf[0],(int)hpf[1],(int)hpf[2]);
                HPFImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        return HPFImage;
    }
}
