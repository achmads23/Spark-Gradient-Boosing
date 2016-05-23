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
public class Resize_Image {
    
    public Resize_Image() throws IOException{
        String Path = "E:\\Teknik Informatika 2012\\Tugas Akhir\\Dataset\\positive_images_separated_car";
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
            images = Scalr.resize(images, Scalr.Method.SPEED,Scalr.Mode.FIT_EXACT, 50,50, OP_ANTIALIAS);
            count++;
            File output = new File("positive_images/separated_" + count + ".png");
            ImageIO.write(images,"png", output);
        }
    }
    
    public static void main(String[] args) throws Exception{
        Resize_Image obj = new Resize_Image();
    }
}
