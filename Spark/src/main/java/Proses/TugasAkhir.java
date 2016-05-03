/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import UI.ProcessedImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.OP_BRIGHTER;
/**
 *
 * @author achmad
 */
public class TugasAkhir {
    
    private TugasAkhir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void TemporaryFunction() throws IOException{
        BufferedImage image = null;
        String output;
         /** Start Grayscale and Invers Grayscale **/
            BufferedImage imageInversGrayscale;
            imageInversGrayscale = new ConvertGrayscale().InversGrayscale(image);
            output = new WriteImage().WriteImage(imageInversGrayscale, "Invert Grayscale");
            
            BufferedImage imageGrayscale;
            imageGrayscale = new ConvertGrayscale().Grayscale(image);
            output = new WriteImage().WriteImage(imageGrayscale, "Grayscale");
        /** End Grayscale and Invers Grayscale **/
            
        /** Start Cek HSB **/
            BufferedImage imageSaturation;
            imageSaturation = new RGBtoHSB().Saturation(image);
            output = new WriteImage().WriteImage(imageSaturation, "Saturation");

            BufferedImage imageHue;
            imageHue = new RGBtoHSB().Hue(image);
            output = new WriteImage().WriteImage(imageHue, "Hue");

            BufferedImage imageBrigthness;
            imageBrigthness = new RGBtoHSB().Brightness(image);
            output = new WriteImage().WriteImage(image, "Brightness");

            BufferedImage imageHSB;
            imageHSB = new RGBtoHSB().RGBtoHSB(image);
            output = new WriteImage().WriteImage(image, "RGBtoHSB");
        /** End Cek HSB **/
    }
    
    public TugasAkhir(String filepath){
        try {
            BufferedImage image;
            String output;
            BufferedImage originalimage;
            image = new ReadImage().ReadImage(filepath);
            output = new WriteImage().WriteImage(image, "File Input");
            image = Scalr.resize(image, Method.SPEED, 1000, OP_ANTIALIAS);
            originalimage = image;
            
            ArrayList<ArrayList<Integer>> aImage = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Double>> fitur = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Integer>> Candidates = new ArrayList<ArrayList<Integer>>();
            
            image = new MedianFilter().MedianFilter(image,1,StructuringElement.STRUCTURING_ELEMENT_SHAPE.ROUND);
            output = new WriteImage().WriteImage(image, "Median Filter");
             
            image = new HighPassFilter().HighPassFilter(image);
            output = new WriteImage().WriteImage(image, "HPF");
            
            image = new RGBtoHSB().Saturation(image);
            output = new WriteImage().WriteImage(image, "Saturation");
            
            image = new FuzzyDilation().FuzzyDilation(image,4,StructuringElement.STRUCTURING_ELEMENT_SHAPE.ROUND);
            output = new WriteImage().WriteImage(image, "Fuzzy Dilation");
            
            image = new Threshold().Threshold(image); //otsu
            output = new WriteImage().WriteImage(image, "Threshold");
            
            image = new Opening().Opening(image,1,StructuringElement.STRUCTURING_ELEMENT_SHAPE.VERTICAL_LINE,StructuringElement.STRUCTURING_ELEMENT_SHAPE.ROUND,9,9);
            output = new WriteImage().WriteImage(image, "Opening");
            
            aImage = new ConnectedComponent().ConnectedComponent(image, originalimage);
            
            Candidates = new Wavelet().selectedLabel(aImage);
            fitur = new Wavelet().Wavelet2D(Candidates, 3,originalimage);
            
            for (int x = 0; x < fitur.size() ;){
                Double min = fitur.get(x).get(5);
                Double max = min;
                for (int y = 6; y < fitur.get(x).size();y++ ){
                    if(min > fitur.get(x).get(y)){
                       min =  fitur.get(x).get(y);
                    }
                    
                    if(max < fitur.get(x).get(y)){
                       max =  fitur.get(x).get(y);
                    }
                }
                
                for (int y = 5; y < fitur.get(x).size();y++ ){
                    Double value = fitur.get(x).get(y) -  min;
                    fitur.get(x).set(y,value);
                    
                    value = fitur.get(x).get(y) / max;
                    fitur.get(x).set(y,value);
                }
                x++;
            }
            
            System.out.println(fitur.size());
            String path = "Fitur.txt";
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            
            for (int x = 0; x < fitur.size() ; x++){
                writer.print("1 ");
                for (int y = 5; y < fitur.get(x).size();y++ ){
                    int count = y - 4;
                    DecimalFormat df = new DecimalFormat("#.###############");
                    df.setRoundingMode(RoundingMode.CEILING);
                    writer.print(count + ":" + df.format(fitur.get(x).get(y)) + " ");
                }
                writer.println("");
            }
            writer.close();
            
            writer = new PrintWriter("Connected Component.txt", "UTF-8");
            
            for (int x = 0; x < fitur.size() ; x++){
                writer.print(x + ". ");
                for (int y = 0; y < 5;y++ ){
                    writer.print(fitur.get(x).get(y) + " ");
                }
                writer.println("");
            }
            writer.close();
            
            ArrayList<Double> hasil = new ArrayList<Double>();
            hasil = new Spark().Spark(path);
            System.out.println("OUTPUT BROO : " + hasil.size());
            int jumlah = 0;
            for (int x = 0; x < hasil.size() ; x++){
                if(hasil.get(x) == 1){
                    for(int y = fitur.get(x).get(1).intValue(); y < fitur.get(x).get(2).intValue(); y++){
                        if(y < originalimage.getWidth() && y > 0){
                            int newR = 0;
                            int newG = 0;
                            int newB = 0;

                            Color newColor = new Color(255,newG,newB);
                            originalimage.setRGB(y, fitur.get(x).get(3).intValue(), newColor.getRGB());
                            originalimage.setRGB(y, fitur.get(x).get(4).intValue(), newColor.getRGB());
                            
                        }
                    }
                    
                    for(int y = fitur.get(x).get(3).intValue(); y < fitur.get(x).get(4).intValue(); y++){
                        if(y < originalimage.getHeight() && y > 0){
                            int newR = 0;
                            int newG = 0;
                            int newB = 0;

                            Color newColor = new Color(255,newG,newB);
                            originalimage.setRGB(fitur.get(x).get(1).intValue(), y, newColor.getRGB());
                            originalimage.setRGB(fitur.get(x).get(2).intValue(), y, newColor.getRGB());
                        }
                    }
                    jumlah++;
                }
            }
            output = new WriteImage().WriteImage(image, "Output");
            ProcessedImage processed = new ProcessedImage(output,originalimage,jumlah);
            processed.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(TugasAkhir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws Exception{
        TugasAkhir obj = new TugasAkhir();
        //obj.CountPixel();
    }
}
