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
import java.util.ArrayList;

/**
 *
 * @author achma
 */
public class TrainingData {
    public ArrayList<Double> ExtractFitur(String filepath,double posneg) throws IOException{
        BufferedImage image;
        String output;
        image = new ReadImage().ReadImage(filepath);
        
        int w = image.getWidth();
        int h = image.getHeight();
        ArrayList<Double> return_fitur = new ArrayList<Double>();
        
        ArrayList<Double> fitur = new ArrayList<Double>();
        fitur = new Wavelet().Training_Wavelet2D(image,3);
        return_fitur.add(posneg);
        for(int a=0;a<fitur.size();a++){
            return_fitur.add(fitur.get(a));
        }
        return return_fitur;
    }
    
    public ArrayList<ArrayList<Double>> TrainingData() throws IOException{
        String PathPositive = "E:\\Teknik Informatika 2012\\Tugas Akhir\\Dataset\\Dataset _CAR_AND SEPARATED\\car_and_separated_car";
        String PathNegative = "E:\\Teknik Informatika 2012\\Tugas Akhir\\Dataset\\Dataset _CAR_AND SEPARATED\\not_car";
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
            fitur = ExtractFitur(listPathPositive.get(i),1.0);
            listFiturPositive.add(fitur);
            listFitur.add(fitur);
        }
        
        for (int i = 0; i < listPathNegative.size(); i++) {
            fitur = ExtractFitur(listPathNegative.get(i),0.0);
            listFiturNegative.add(fitur);
            listFitur.add(fitur);
        }
        
        PrintWriter writerPositive = new PrintWriter("F.txt", "UTF-8");

        for (int x = 0; x < listFitur.size() ; x++){
            writerPositive.print(listFitur.get(x).get(0).intValue() + " ");
            for (int y = 1; y < listFitur.get(x).size();y++ ){
                int count = y +1;
                Double out = new Double(listFitur.get(x).get(y) * 10000);
                int out_int = out.intValue();
                writerPositive.print(count + ":" + out_int + " ");
            }
            writerPositive.println("");
        }
        writerPositive.close();
        return listFitur;
    }
}
