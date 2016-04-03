/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import UI.ProcessedImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            
//            System.out.println(fitur);
            System.out.println(fitur.size());
            PrintWriter writer = new PrintWriter("Fitur.txt", "UTF-8");
            
            for (int x = 0; x < fitur.size() ; x++){
                writer.print("1 ");
                for (int y = 0; y < fitur.get(x).size();y++ ){
                    int count = y + 1;
                    writer.print(count + ":" + fitur.get(x).get(y) + " ");
                }
                writer.println("");
            }
            
            output = new WriteImage().WriteImage(image, "Output");
            ProcessedImage processed = new ProcessedImage(output,originalimage);
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
