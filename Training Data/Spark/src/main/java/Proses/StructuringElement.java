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
public class StructuringElement {
    
    public enum STRUCTURING_ELEMENT_SHAPE {
        SQUARE, VERTICAL_LINE, HORIZONTAL_LINE, ROUND
    }
    
    public Color[][] constructShape(BufferedImage img,STRUCTURING_ELEMENT_SHAPE shape,int shapeSize, int x, int y) {
        int size = 2 * shapeSize + 1;
        Color[][] structElem = new Color[size][size];
        switch (shape) {
        case SQUARE:

                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                                structElem[i][j] = new Color(img.getRGB(x+i,j+y));
                        }
                }
                break;
        case VERTICAL_LINE:
                for (int i = 0; i < size; i++) {
                        structElem[i][shapeSize] = new Color(img.getRGB(x+i,y));
                }
                break;
        case HORIZONTAL_LINE:
                for (int i = 0; i < size; i++) {
                        structElem[shapeSize][i] = new Color(img.getRGB(x,i+y));
                }
                break;
        case ROUND:
                int middle = (size / 2);
                int awal;
                int akhir;
                for (int i = 0; i < size; i++) {
                    if(i <=middle){
                        awal = middle - i;
                        akhir = middle + i;
                    } else {
                        awal = i - middle;
                        akhir = size - (i - middle + 1);
                    }
                    for (int j = awal; j <= akhir; j++) {
                            structElem[i][j] = new Color(img.getRGB(x+i,j+y));
                    }
                }
        default:
                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                                structElem[i][j] = new Color(img.getRGB(x+i,j+y));
                        }
                }
        }
        return structElem;
    }
}
