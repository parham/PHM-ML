
package com.phm.ml.io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;

/**
 *
 * @author phm
 */
public class Image2DDataLoader extends FileDatasetLoader {

    protected Color backColor = Color.BLACK;
    
    public Image2DDataLoader(File fin, Color bg) {
        super(fin);
        backColor = bg;
    }
    public Image2DDataLoader (File fin) {
        this (fin, Color.BLACK);
    }

    public Color getBackgroundColor () {
        return backColor;
    }
    
    @Override
    public boolean load (Dataset ds) {
        try {
            BufferedImage img = ImageIO.read(fins);
            float thrsh = (backColor.getRed() + 
                           backColor.getGreen() + 
                           backColor.getBlue()) / 3;
            float width = img.getWidth();
            float height = img.getHeight();
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    Color tmp = new Color(img.getRGB(x, y));
                    float rgb = (tmp.getRed() + tmp.getGreen() + tmp.getBlue()) / 3;
                    if (rgb > thrsh) {
                        double [] arr = {x / width, (height - y) / height};
                        ds.add (new DenseInstance(arr, String.valueOf(tmp.getRGB())));
                    }
                }
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

//    @Override
//    public boolean load(Dataset ds, Object defClskey) {
//        try {
//            BufferedImage img = ImageIO.read(fins);
//            float thrsh = (backColor.getRed() + 
//                           backColor.getGreen() + 
//                           backColor.getBlue()) / 3;
//            float width = img.getWidth();
//            float height = img.getHeight();
//            for (int y = 0; y < img.getHeight(); y++) {
//                for (int x = 0; x < img.getWidth(); x++) {
//                    Color tmp = new Color(img.getRGB(x, y));
//                    float rgb = (tmp.getRed() + tmp.getGreen() + tmp.getBlue()) / 3;
//                    if (rgb > thrsh) {
//                        double [] arr = {x / width, (height - y) / height};
//                        DenseInstance inst = new DenseInstance(arr, String.valueOf(tmp.getRGB()));
//                        ds.add (inst);
//                    }
//                }
//            }
//        } catch (IOException ex) {
//            return false;
//        }
//        return true;
//    }
}
