/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author achma
 */
public class ReadImage {
    public BufferedImage ReadImage(String filepath) throws IOException {
        BufferedImage image;
        File input = new File(filepath);
        image = ImageIO.read(input);
        return image;
    }
}
