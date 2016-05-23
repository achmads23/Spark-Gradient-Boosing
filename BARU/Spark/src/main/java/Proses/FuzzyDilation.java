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
public class FuzzyDilation extends StructuringElement{
    int shapeSize;
    public Color[][] structElem, B;
    public STRUCTURING_ELEMENT_SHAPE shape;
    
    public BufferedImage FuzzyDilation(BufferedImage img,int shapeSize, STRUCTURING_ELEMENT_SHAPE shape){
        int width = img.getWidth();
        int height = img.getHeight();
        
        BufferedImage dilationImage = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        
        int sSize = 2 * shapeSize + 1;
        
        int filterWidth = width - sSize;
        int filterHeight = height - sSize;
        int lowerSide = height - shapeSize;
        int rightSide = width - shapeSize;
        
        int dilation = 0;
        
        int size = 2 * shapeSize + 1;
        
        int[][] matrix;
        matrix = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                matrix[x][y] = 1;
            }
        }
        
        //center
        for (int x = 0; x <= filterWidth; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,x,y);
                dilation = DKD(structElem,sSize,shape,matrix);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x+shapeSize, y+shapeSize, newColor.getRGB());
            }
        }
        
        //leftborder
        for (int x = 0; x < shapeSize; x++) {
            for (int y = 0; y <= filterHeight; y++) {
                this.structElem = constructShape(img, shape, shapeSize,0,y);
                dilation = DKD(structElem,sSize,shape,matrix);
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
        dilation = DKD(structElem,sSize,shape,matrix);
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
                dilation = DKD(structElem,sSize,shape,matrix);
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
                dilation = DKD(structElem,sSize,shape,matrix);
                Color newColor = new Color(dilation,dilation,dilation);
                dilationImage.setRGB(x + shapeSize, y, newColor.getRGB());
            }
        }
        
        //upperborder
        for (int y = 0; y < shapeSize; y++) {
            for (int x = 0; x <= filterWidth; x++) {
                this.structElem = constructShape(img, shape, shapeSize,x,0);
                dilation = DKD(structElem,sSize,shape,matrix);
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
    
    public int Conjunction(int a, int val){
        int hasil;
        int b = 1-a;
        if ((double)val/255 <= 1-a){
            hasil = 0;
        } else {
            hasil = val;
        }
        
        return hasil;
    }
    
    public int DKD(Color[][] val,int sSize,STRUCTURING_ELEMENT_SHAPE shape, int[][] matrix) {
        if(shape == STRUCTURING_ELEMENT_SHAPE.SQUARE || shape == STRUCTURING_ELEMENT_SHAPE.ROUND){
            sSize = sSize * sSize;
        }
        int[] temp = new int[sSize];
        int count = 0;
        
        for(int i = 0; i < val.length ; i++){
            for(int j = 0 ; j < val[i].length ; j++){
                if(val[i][j] == null){
                    continue;
                }
                
                temp[count] =  Conjunction(matrix[i][j],val[i][j].getRed());
                count++;
            }
        }
        
        int max = temp[0];
        int end = temp.length;
        int v = 0;
        for (int i = 1; i < end; i++) {
                if(max < temp[i]){
                    max = temp[i];
                }
        }
        return max;
    }
    
}
