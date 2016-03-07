/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import UI.ProcessedImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    
    public TugasAkhir(String filepath){
        try {
            BufferedImage image;
            String output;
            BufferedImage originalimage;
            image = new ReadImage().ReadImage(filepath);
            output = new WriteImage().WriteImage(image, "File Input");
//            image = Scalr.resize(image, Method.SPEED, image.getWidth()*3, OP_ANTIALIAS);
            originalimage = image;
            
            ArrayList<ArrayList<Integer>> aImage = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Double>> fitur = new ArrayList<ArrayList<Double>>();
            
//            output = new WriteImage().WriteImage(image, "Bigger");
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
            //fitur = new Wavelet().selectedLabel(aImage, 1);
            
            //fitur = new Wavelet().Wavelet2D(aImage, 3,originalimage);
            
//            System.out.println(fitur);
//            System.out.println(fitur.size() + " " + aImage.size());
//            System.out.println(fitur.get(0).size() + " " + aImage.get(0).size());
            
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

//            image = new RGBtoHSB().RGBtoHSB(image);
//            image = new Threshold().Threshold(image,180);
//            originalimage = image;
//            image = new Erosion().Erosion(image, 2, StructuringElement.STRUCTURING_ELEMENT_SHAPE.HORIZONTAL_LINE);
//            image = new ConvertGrayscale().ConvertGrayscale(image);
//            image = new Closing().Closing(image,3,StructuringElement.STRUCTURING_ELEMENT_SHAPE.VERTICAL_LINE);
