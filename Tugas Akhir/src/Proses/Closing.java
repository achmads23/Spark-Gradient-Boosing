/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.image.BufferedImage;

/**
 *
 * @author achma
 */
public class Closing {
    public BufferedImage Closing(BufferedImage img,int shapeSize, StructuringElement.STRUCTURING_ELEMENT_SHAPE shape){
        
        img = new Dilation().Dilation(img,shapeSize,shape);
        img = new Erosion().Erosion(img,shapeSize,shape);
        
        return img;
    }
    
}
