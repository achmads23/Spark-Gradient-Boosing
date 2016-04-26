/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import UI.ProcessedImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.OP_BRIGHTER;
/**
 *
 * @author achmad
 */
public class TugasAkhir {
    
    public ArrayList<Double> ExtractFitur(String filepath) throws IOException{
        BufferedImage image;
        String output;
        image = new ReadImage().ReadImage(filepath);
        
        int w = image.getWidth();
        int h = image.getHeight();
        
        ArrayList<Double> fitur = new ArrayList<Double>();
        
        fitur = new Wavelet().Wavelet2D(image,3);
        
        return fitur;
    }
    
    public TugasAkhir() throws IOException{
        String PathPositive = "E:\\Teknik Informatika 2012\\Tugas Akhir\\Training Data\\Spark\\Dataset\\positive_images";
        String PathNegative = "E:\\Teknik Informatika 2012\\Tugas Akhir\\Training Data\\Spark\\Dataset\\negative_images";
        File positive = new File(PathPositive);
        File negative = new File(PathNegative);
        File[] listOfPositive = positive.listFiles();
        File[] listOfNegative = negative.listFiles();
        
        ArrayList<String> listPathPositive = new ArrayList<String>();
        ArrayList<String> listPathNegative = new ArrayList<String>();
        
        for (int i = 0; i < listOfPositive.length; i++) {
          File file = listOfPositive[i];
          String Path = PathPositive +"\\" + file.getName();
          listPathPositive.add(Path);
        }
        
        for (int i = 0; i < listOfNegative.length; i++) {
          File file = listOfNegative[i];
          String Path = PathNegative +"\\" + file.getName();
          listPathNegative.add(Path);
        }
        
        ArrayList<ArrayList<Double>> listFiturPositive = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> listFiturNegative = new ArrayList<ArrayList<Double>>();
        
        ArrayList<Double> fitur = new ArrayList<Double>();
        
        for (int i = 0; i < listPathPositive.size(); i++) {
            fitur = ExtractFitur(listPathPositive.get(i));
            listFiturPositive.add(fitur);
        }
        
        for (int i = 0; i < listPathNegative.size(); i++) {
            fitur = ExtractFitur(listPathNegative.get(i));
            listFiturNegative.add(fitur);
        }
        
        for (int x = 0; x < listFiturNegative.size() ;){
            Double min = listFiturNegative.get(x).get(0);
            Double max = min;
            for (int y = 0; y < listFiturNegative.get(x).size();y++ ){
                if(min > listFiturNegative.get(x).get(y)){
                   min =  listFiturNegative.get(x).get(y);
                }

                if(max < listFiturNegative.get(x).get(y)){
                   max =  listFiturNegative.get(x).get(y);
                }
            }

            for (int y = 0; y < listFiturNegative.get(x).size();y++ ){
                Double value = listFiturNegative.get(x).get(y) -  min;
                listFiturNegative.get(x).set(y,value);

                value = listFiturNegative.get(x).get(y) / max;
                listFiturNegative.get(x).set(y,value);
            }
            x++;
        }
        
        for (int x = 0; x < listFiturPositive.size() ;){
            Double min = listFiturPositive.get(x).get(0);
            Double max = min;
            for (int y = 0; y < listFiturPositive.get(x).size();y++ ){
                if(min > listFiturPositive.get(x).get(y)){
                   min =  listFiturPositive.get(x).get(y);
                }

                if(max < listFiturPositive.get(x).get(y)){
                   max =  listFiturPositive.get(x).get(y);
                }
            }

            for (int y = 0; y < listFiturPositive.get(x).size();y++ ){
                Double value = listFiturPositive.get(x).get(y) -  min;
                listFiturPositive.get(x).set(y,value);

                value = listFiturPositive.get(x).get(y) / max;
                listFiturPositive.get(x).set(y,value);
            }
            x++;
        }
        
        PrintWriter writerNegative = new PrintWriter("FiturNegative.txt", "UTF-8");
        PrintWriter writer = new PrintWriter("Fitur.txt", "UTF-8");

        for (int x = 0; x < listFiturNegative.size() ; x++){
            writerNegative.print("0 ");
            writer.print("0 ");
            for (int y = 0; y < listFiturNegative.get(x).size();y++ ){
                int count = y + 1;
                DecimalFormat df = new DecimalFormat("#.###############");
                df.setRoundingMode(RoundingMode.CEILING);
                writerNegative.print(count + ":" + df.format(listFiturNegative.get(x).get(y)) + " ");
                writer.print(count + ":" + df.format(listFiturNegative.get(x).get(y)) + " ");
            }
            writerNegative.println("");
            writer.println("");
        }
        writerNegative.close();
        
        PrintWriter writerPositive = new PrintWriter("FiturPositive.txt", "UTF-8");

        for (int x = 0; x < listFiturPositive.size() ; x++){
            writerPositive.print("1 ");
            writer.print("1 ");
            for (int y = 0; y < listFiturPositive.get(x).size();y++ ){
                int count = y +1;
                DecimalFormat df = new DecimalFormat("#.###############");
                df.setRoundingMode(RoundingMode.CEILING);
                writerPositive.print(count + ":" + df.format(listFiturPositive.get(x).get(y)) + " ");
                writer.print(count + ":" + df.format(listFiturPositive.get(x).get(y)) + " ");
            }
            writerPositive.println("");
            writer.println("");
        }
        writerPositive.close();
        writer.close();
    }
    
    public static void main(String[] args) throws Exception{
        TugasAkhir obj = new TugasAkhir();
    }
}
