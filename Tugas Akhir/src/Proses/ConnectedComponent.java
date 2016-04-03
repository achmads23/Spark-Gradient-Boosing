/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author achma
 */
public class ConnectedComponent {
    
    BufferedImage image;
    BufferedImage originalImage;
    BufferedImage output;
    int[][] pixel;
    int[][] visited;
    int nextLabel = 1;
    int width;
    int height;
    
    public ArrayList<ArrayList<Integer>> ConnectedComponent(BufferedImage img,BufferedImage oriImg) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        originalImage = oriImg;
        int background = 0;
        image = img;
        output = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
        height = img.getHeight();
        width = img.getWidth();
        
        ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> unionFind = new ArrayList<ArrayList<Integer>>();
        for (int x = 0; x < width; x++ ){
            array.add(new ArrayList<Integer>()); 
            for (int y = 0; y < height; y++ ){
                array.get(x).add(y,0);
            }
        }
        
        //First Pass
        for (int y = 0; y < height; y++ ){
            for (int x = 0; x < width; x++ ){
                int currentColor = 0;
                int topColor = 0;
                int leftColor = 0;
                Color color = new Color(image.getRGB(x,y));
                currentColor = color.getRed();
                if ( currentColor !=0 ){
                    if ( y > 0 ){
                        topColor = array.get(x).get(y-1);
                    }
                    
                    if ( x > 0 ){
                        leftColor = array.get(x-1).get(y);
                    }
                    
                    if(leftColor != 0 || topColor!=0){
                        if(leftColor == 0){
                            array.get(x).set(y,topColor);
                        } else if(topColor == 0){
                            array.get(x).set(y,leftColor);
                        } else {
                            array.get(x).set(y,min(leftColor,topColor));
                            if(leftColor!= topColor){
                                int cek = 0;
                                for( int a = 0; a < unionFind.size() ; a++){
                                    if( unionFind.get(a).get(0) == min(leftColor,topColor) && unionFind.get(a).get(1) == max(leftColor,topColor)){
                                        cek = 1;
                                    }
                                    
                                }
                                if (cek == 0){
                                    unionFind.add(new ArrayList<Integer>());
                                    unionFind.get(unionFind.size()-1).add(0,min(leftColor,topColor));
                                    unionFind.get(unionFind.size()-1).add(1,max(leftColor,topColor));
                                }
                            }
                        }
                    } else {
                        array.get(x).set(y,nextLabel);
                        nextLabel++;
                    }
                }
            }
        }
        
        //Second Pass
        for( int a = 0; a <unionFind.size()-1 ; a++){
            for( int b = a + 1; b < unionFind.size() ; b++){
                if (unionFind.get(a).get(1) < unionFind.get(b).get(1)){
                    int temp = unionFind.get(a).get(1);
                    unionFind.get(a).set(1,unionFind.get(b).get(1));
                    unionFind.get(b).set(1,temp);
                    
                    temp = unionFind.get(a).get(0);
                    unionFind.get(a).set(0,unionFind.get(b).get(0));
                    unionFind.get(b).set(0,temp);
                }
            }
        }
        
        for( int a = 0; a <unionFind.size() ; a++){
            for (int y = 0; y < height; y++ ){
                for (int x = 0; x < width; x++ ){
                    if ((int)array.get(x).get(y) == (int)unionFind.get(a).get(1)){
                        array.get(x).set(y,unionFind.get(a).get(0));
                    }
                }
            }
        }
        
        ArrayList<ArrayList<Integer>> maxmin = new ArrayList<ArrayList<Integer>>();
        for (int y = 0; y < height; y++ ){
            for (int x = 0; x < width; x++ ){
                if(array.get(x).get(y) != 0 ){
                    int exist = 0;

                    if(maxmin.size() != 0){
                        for (int z = 0; z < maxmin.size() ; z++){
                            if( (int)maxmin.get(z).get(0) == (int)array.get(x).get(y) ){
                                if( maxmin.get(z).get(1) > x){
                                    maxmin.get(z).set(1,x);
                                } else if ( maxmin.get(z).get(2) < x){
                                    maxmin.get(z).set(2,x);
                                }

                                if( maxmin.get(z).get(3) > y){
                                    maxmin.get(z).set(3,y);
                                } else if ( maxmin.get(z).get(4) < y){
                                    maxmin.get(z).set(4,y);
                                }
                                exist = 1;
                                break;
                            }
                        }
                    }
                    if(exist == 0){
                        maxmin.add(new ArrayList<Integer>());
                        maxmin.get(maxmin.size()-1).add(0,array.get(x).get(y));
                        maxmin.get(maxmin.size()-1).add(1,x);
                        maxmin.get(maxmin.size()-1).add(2,x);
                        maxmin.get(maxmin.size()-1).add(3,y);
                        maxmin.get(maxmin.size()-1).add(4,y);
                    }
                }
            }
        }
        
        
        /**
         * Write To File per label
         */
        
        for (int z = 0; z < maxmin.size() ; z++){
            int _width;
            _width = maxmin.get(z).get(2) - maxmin.get(z).get(1);
            int _height;
            _height = maxmin.get(z).get(4) - maxmin.get(z).get(3);
            if(_width < 100 && _height < 100){
                if ((maxmin.get(z).get(2) + 10) < originalImage.getWidth() && _width > 25)
                    maxmin.get(z).set(2,maxmin.get(z).get(2) + 10);

                if ((maxmin.get(z).get(3) - 15) > 0 && _height < 27)
                    maxmin.get(z).set(3,maxmin.get(z).get(3)-15);
                else if ((maxmin.get(z).get(3) - 25) > 0)
                    maxmin.get(z).set(3,maxmin.get(z).get(3)-25);

                if ((maxmin.get(z).get(4) - 15) > 0)
                    maxmin.get(z).set(4,maxmin.get(z).get(4)-15);

                _width = maxmin.get(z).get(2) - maxmin.get(z).get(1);
                _height = maxmin.get(z).get(4) - maxmin.get(z).get(3);

                BufferedImage images;
                images = new BufferedImage(_width,_height, output.getType());

                for (int y = 0; y < _height; y++ ){
                    for (int x = 0; x < _width; x++ ){
                        int _x = maxmin.get(z).get(1)+x;
                        int _y = maxmin.get(z).get(3)+y;
                        images.setRGB(x, y, originalImage.getRGB(_x,_y));
                    }
                }
                String kata = "CCL" + Integer.toString(z);
                File output = new File("imageCCL/" + kata + ".jpg");
                ImageIO.write(images,"jpg", output);
            }
        }
        
        return array;
    }   
}
