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
public class Opening {
    public BufferedImage Opening(BufferedImage img,int shapeSize, StructuringElement.STRUCTURING_ELEMENT_SHAPE shape,StructuringElement.STRUCTURING_ELEMENT_SHAPE shape2, int erosion, int dilation) throws FileNotFoundException, UnsupportedEncodingException{
        
        for(int a=0; a<erosion;a++){
            img = new Erosion().Erosion(img,shapeSize,shape);
        }
        for(int a=0; a<dilation;a++){
            img = new Dilation().Dilation(img,shapeSize,shape2);
        }
        return img;
    }
    
}
