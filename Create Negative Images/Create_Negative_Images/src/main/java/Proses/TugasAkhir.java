/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.OP_ANTIALIAS;
/**
 *
 * @author achmad
 */
public class TugasAkhir {
    
    public TugasAkhir() throws IOException{
        String Path = "E:\\Teknik Informatika 2012\\Tugas Akhir\\Create Negative Images\\Create_Negative_Images\\Dataset\\raw_images";
        File image = new File(Path);
        
        File[] listOfImage = image.listFiles();
        
        ArrayList<String> listPathImage = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < listOfImage.length; i++) {
            System.out.println(listOfImage[i]);
            File file = listOfImage[i];
            String Path_Image = listOfImage[i].toString();
          
            BufferedImage images;
            images = new ReadImage().ReadImage(Path_Image);
            images = Scalr.resize(images, Scalr.Method.SPEED, 1000, OP_ANTIALIAS);
            int width = images.getWidth();
            int height = images.getHeight();
            for(int j = 0 ; j< 100 ; j++){
                BufferedImage img;
                Random rand = new Random();
                int init_x = (int )(Math.random() * (width-100));
                int init_y = (int )(Math.random() * (height-100));
                int _x = (int )(Math.random() * 99+10);
                int _y = (int )(Math.random() * 99+10);
                int end_x = init_x + _x;
                int end_y = init_y + _y;
                int _width = end_x - init_x;
                int _height = end_y - init_y;
                if(_height > 0 && _width > 0 &&_width < 100 && _height < 100){

                    img = new BufferedImage(_width,_height, images.getType());
                    for (int y = 0; y < _height; y++ ){
                        for (int x = 0; x < _width; x++ ){
                            img.setRGB(x, y, images.getRGB(init_x + x,init_y + y));
                        }
                    }
                    String kata = "CCL" + count;
                    count++;
                    File output = new File("negative_images/" + kata + ".png");
                    ImageIO.write(img,"png", output);
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception{
        TugasAkhir obj = new TugasAkhir();
    }
}
