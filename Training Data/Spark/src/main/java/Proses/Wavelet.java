/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author achma
 */
public class Wavelet {
    
    BufferedImage image;
    
    private int getHaarMaxCycles(int hw) {
        int cycles = 0;
        while (hw > 1) {
            cycles++;
            hw /= 2;
        }
        return cycles;
    }

    private boolean isCycleAllowed(int maxCycle, int cycles) {
        return cycles <= maxCycle;
    }
    
    public ArrayList<Double> Wavelet2D(BufferedImage originalImage,int cycles) throws IOException {
        
        ArrayList<Double> Fitur = new ArrayList<Double>();
        
        image = new ConvertGrayscale().Grayscale(originalImage);
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        int maxCycle = 0;
        if(w<h)
            maxCycle = getHaarMaxCycles(w);
        else 
            maxCycle = getHaarMaxCycles(h);
        boolean isCycleAllowed = isCycleAllowed(maxCycle, cycles);

        int height = h;
        int width = w;
        if (isCycleAllowed) {
            double[][] dsR = new double[h][w];
            double[][] dsG = new double[h][w];
            double[][] dsB = new double[h][w];
            double[][] tempdsR = new double[h][w];
            double[][] tempdsG = new double[h][w];
            double[][] tempdsB = new double[h][w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    Color warna = new Color(image.getRGB(j,i));
                    dsR[i][j] = warna.getRed();
                    dsG[i][j] = warna.getGreen();
                    dsB[i][j] = warna.getBlue();
                }
            }

            for (int i = 0; i < cycles; i++) {
                w /= 2;
                for (int j = 0; j < h; j++) {
                    for (int k = 0; k < w; k++) {
                        double aR = dsR[j][2 * k];
                        double bR = dsR[j][2 * k + 1];
                        double addR = aR + bR;
                        double subR = aR - bR;
                        double avgAddR = addR / 2;
                        double avgSubR = subR / 2;
                        tempdsR[j][k] = avgAddR;
                        tempdsR[j][k + w] = avgSubR;

                        double aG = dsG[j][2 * k];
                        double bG = dsG[j][2 * k + 1];
                        double addG = aG + bG;
                        double subG = aG - bG;
                        double avgAddG = addG / 2;
                        double avgSubG = subG / 2;
                        tempdsG[j][k] = avgAddG;
                        tempdsG[j][k + w] = avgSubG;

                        double aB = dsB[j][2 * k];
                        double bB = dsB[j][2 * k + 1];
                        double addB = aB + bB;
                        double subB = aB - bB;
                        double avBAddB = addB / 2;
                        double avBSubB = subB / 2;
                        tempdsB[j][k] = avBAddB;
                        tempdsB[j][k + w] = avBSubB;
                    }
                }
                for (int j = 0; j < h; j++) {
                    for (int k = 0; k < w; k++) {
                        dsR[j][k] = tempdsR[j][k];
                        dsR[j][k + w] = tempdsR[j][k + w];
                        dsG[j][k] = tempdsG[j][k];
                        dsG[j][k + w] = tempdsG[j][k + w];
                        dsB[j][k] = tempdsB[j][k];
                        dsB[j][k + w] = tempdsB[j][k + w];
                    }
                }
                h /= 2;
                for (int j = 0; j < w; j++) {
                    for (int k = 0; k < h; k++) {
                        double aR = dsR[2 * k][j];
                        double bR = dsR[2 * k + 1][j];
                        double addR = aR + bR;
                        double subR = aR - bR;
                        double avgAddR = addR / 2;
                        double avgSubR = subR / 2;
                        tempdsR[k][j] = avgAddR;
                        tempdsR[k + h][j] = avgSubR;

                        double aG = dsG[2 * k][j];
                        double bG = dsG[2 * k + 1][j];
                        double addG = aG + bG;
                        double subG = aG - bG;
                        double avgAddG = addG / 2;
                        double avgSubG = subG / 2;
                        tempdsG[k][j] = avgAddG;
                        tempdsG[k + h][j] = avgSubG;

                        double aB = dsB[2 * k][j];
                        double bB = dsB[2 * k + 1][j];
                        double addB = aB + bB;
                        double subB = aB - bB;
                        double avBAddB = addB / 2;
                        double avBSubB = subB / 2;
                        tempdsB[k][j] = avBAddB;
                        tempdsB[k + h][j] = avBSubB;

                    }
                }
                for (int j = 0; j < w; j++) {
                    for (int k = 0; k < h; k++) {
                        dsR[k][j] = tempdsR[k][j];
                        dsR[k + h][j] = tempdsR[k + h][j];
                        dsG[k][j] = tempdsG[k][j];
                        dsG[k + h][j] = tempdsG[k + h][j];
                        dsB[k][j] = tempdsB[k][j];
                        dsB[k + h][j] = tempdsB[k + h][j];
                    }
                }
            }


            for (int a = 0; a < cycles; a++) {
                int index = a * 18;

                //atas kanan
                double yR = 0;
                double y2R = 0;
                double yG = 0;
                double y2G = 0;
                double yB = 0;
                double y2B = 0;

                for (int i = 0; i < height/2; i++) {
                    for (int j = width/2; j < width; j++) {
                        yR = yR + dsR[i][j];
                        y2R = y2R + (dsR[i][j] * dsR[i][j]);
                        yG = yG + dsG[i][j];
                        y2G = y2G + (dsG[i][j] * dsG[i][j]);
                        yB = yB + dsB[i][j];
                        y2B = y2B + (dsB[i][j] * dsB[i][j]);
                    }
                }

                //R
                double pembilang = (y2R) - ((yR*yR)/((height*width)/4));
                double stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                double mean = yR / ((height*width)/4);
                Fitur.add(index + 0,stDeviasi);
                Fitur.add(index + 1,mean);

                //G
                pembilang = (y2G) - ((yG*yG)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yG / ((height*width)/4);
                Fitur.add(index + 2,stDeviasi);
                Fitur.add(index + 3,mean);

                //B
                pembilang = (y2B) - ((yG*yB)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yB / ((height*width)/4);
                Fitur.add(index + 4,stDeviasi);
                Fitur.add(index + 5,mean);



                //kiri bawah
                yR = 0;
                y2R = 0;
                yG = 0;
                y2G = 0;
                yB = 0;
                y2B = 0;
                for (int i = height/2; i < height; i++) {
                    for (int j = 0; j < width/2; j++) {
                        yR = yR + dsR[i][j];
                        y2R = y2R + (dsR[i][j] * dsR[i][j]);
                        yG = yG + dsG[i][j];
                        y2G = y2G + (dsG[i][j] * dsG[i][j]);
                        yB = yB + dsB[i][j];
                        y2B = y2B + (dsB[i][j] * dsB[i][j]);
                    }
                }
                 //R
                pembilang = (y2R) - ((yR*yR)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yR / ((height*width)/4);
                Fitur.add(index + 6,stDeviasi);
                Fitur.add(index + 7,mean);

                //G
                pembilang = (y2G) - ((yG*yG)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yG / ((height*width)/4);
                Fitur.add(index + 8,stDeviasi);
                Fitur.add(index + 9,mean);

                //B
                pembilang = (y2B) - ((yG*yB)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yB / ((height*width)/4);
                Fitur.add(index + 10,stDeviasi);
                Fitur.add(index + 11,mean);

                //kiri bawah
                yR = 0;
                y2R = 0;
                yG = 0;
                y2G = 0;
                yB = 0;
                y2B = 0;
                for (int i = height/2; i < height; i++) {
                    for (int j = width/2; j < width; j++) {
                        yR = yR + dsR[i][j];
                        y2R = y2R + (dsR[i][j] * dsR[i][j]);
                        yG = yG + dsG[i][j];
                        y2G = y2G + (dsG[i][j] * dsG[i][j]);
                        yB = yB + dsB[i][j];
                        y2B = y2B + (dsB[i][j] * dsB[i][j]);
                    }
                }
                 //R
                pembilang = (y2R) - ((yR*yR)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yR / ((height*width)/4);
                Fitur.add(index + 12,stDeviasi);
                Fitur.add(index + 13,mean);

                //G
                pembilang = (y2G) - ((yG*yG)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yG / ((height*width)/4);
                Fitur.add(index + 14,stDeviasi);
                Fitur.add(index + 15,mean);

                //B
                pembilang = (y2B) - ((yG*yB)/((height*width)/4));
                stDeviasi = sqrt(pembilang / (((height*width)/4) - 1));
                mean = yB / ((height*width)/4);
                Fitur.add(index + 16,stDeviasi);
                Fitur.add(index + 17,mean);
                height = height/2;
                width = width/2;
            }
        }
        return Fitur;
    }
}
