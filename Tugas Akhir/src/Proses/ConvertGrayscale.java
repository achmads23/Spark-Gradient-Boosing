/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author achma
 */
public class ConvertGrayscale {
    public BufferedImage ConvertGrayscale(BufferedImage img) throws IOException {
        BufferedImage colorImg = img;
        BufferedImage grayImg = null;

        int w = colorImg.getWidth();
        int h = colorImg.getHeight();

        grayImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImg.getGraphics();
        g.drawImage(colorImg, 0, 0, null);
        g.dispose();
        return grayImg;
    }
}
