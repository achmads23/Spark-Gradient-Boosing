/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        
        ArrayList<String> listPathPositive = new ArrayList<>();
        ArrayList<String> listPathNegative = new ArrayList<>();
        
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
        ArrayList<ArrayList<Double>> listFitur = new ArrayList<ArrayList<Double>>();
        
        ArrayList<Double> fitur = new ArrayList<Double>();
        
        for (int i = 0; i < listPathPositive.size(); i++) {
            fitur = ExtractFitur(listPathPositive.get(i));
            listFiturPositive.add(fitur);
            listFitur.add(fitur);
        }
        
        for (int i = 0; i < listPathNegative.size(); i++) {
            fitur = ExtractFitur(listPathNegative.get(i));
            listFiturNegative.add(fitur);
            listFitur.add(fitur);
        }
        
//        for (int x = 0; x < listFiturNegative.get(0).size() ;){
//            Double min = 0.0;
//            Double max = 0.0;
//            for (int y = 0; y < listFiturNegative.size();y++ ){
//                if(y == 0){
//                    min = listFiturNegative.get(y).get(x);
//                } else {
//                    if(min > listFiturNegative.get(y).get(x)){
//                       min =  listFiturNegative.get(y).get(x);
//                    }
//                }
//            }
//            for (int y = 0; y < listFiturNegative.size();y++ ){
//                
//                Double value = listFiturNegative.get(y).get(x) -  min;
//                listFiturNegative.get(y).set(x,value);
//            }
//            
//            for (int y = 0; y < listFiturNegative.size();y++ ){
//                if(y == 0){
//                    max = listFiturNegative.get(y).get(x);
//                } else {
//                    if(max < listFiturNegative.get(y).get(x)){
//                       max =  listFiturNegative.get(y).get(x);
//                    }
//                }
//            }
//            for (int y = 0; y < listFiturNegative.size();y++ ){
//                
//                Double value = listFiturNegative.get(y).get(x) /  max;
//                listFiturNegative.get(y).set(x,value);
//            }
//            x++;
//        }
//        
//        for (int x = 0; x < listFiturPositive.get(0).size() ;){
//            Double min = 0.0;
//            Double max = 0.0;
//            for (int y = 0; y < listFiturPositive.size();y++ ){
//                if(y == 0){
//                    min = listFiturPositive.get(y).get(x);
//                } else {
//                    if(min > listFiturPositive.get(y).get(x)){
//                       min =  listFiturPositive.get(y).get(x);
//                    }
//                }
//            }
//            for (int y = 0; y < listFiturPositive.size();y++ ){
//                
//                Double value = listFiturPositive.get(y).get(x) -  min;
//                listFiturPositive.get(y).set(x,value);
//            }
//            
//            for (int y = 0; y < listFiturPositive.size();y++ ){
//                if(y == 0){
//                    max = listFiturPositive.get(y).get(x);
//                } else {
//                    if(max < listFiturPositive.get(y).get(x)){
//                       max =  listFiturPositive.get(y).get(x);
//                    }
//                }
//            }
//            for (int y = 0; y < listFiturPositive.size();y++ ){
//                
//                Double value = listFiturPositive.get(y).get(x) /  max;
//                listFiturPositive.get(y).set(x,value);
//            }
//            x++;
//        }
        
        PrintWriter writerNegative = new PrintWriter("FiturNegative.txt", "UTF-8");
        PrintWriter writer = new PrintWriter("TrainingData.txt", "UTF-8");
        for (int x = 0; x < 1000 ; x++){
//        for (int x = 0; x < listFiturNegative.size() ; x++){
            writerNegative.print("0 ");
            writer.print("0 ");
            for (int y = 0; y < listFiturNegative.get(x).size();y++ ){
                int count = y + 1;
                Double out = new Double(listFiturNegative.get(x).get(y) * 10000);
                int out_int = out.intValue();
                writerNegative.print(count + ":" + out_int + " ");
                writer.print(count + ":" + out_int + " ");
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
                Double out = new Double(listFiturPositive.get(x).get(y) * 10000);
                int out_int = out.intValue();
                writerPositive.print(count + ":" + out_int + " ");
                writer.print(count + ":" + out_int + " ");
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
