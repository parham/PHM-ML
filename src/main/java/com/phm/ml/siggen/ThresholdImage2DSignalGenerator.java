
package com.phm.ml.siggen;

import java.awt.Color;
import java.awt.image.BufferedImage;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;

/**
 *
 * @author PARHAM
 */
public class ThresholdImage2DSignalGenerator extends ListedSignalGenerator {

	public ThresholdImage2DSignalGenerator(BufferedImage img, Color thresh, ListedSignalGenerator.GeneratorPolicy gp) {
		super(convertImage2Data(img, thresh), gp);
		// convertImage2Data(img, thresh);
	}

	public static Dataset convertImage2Data(BufferedImage img, Color thresh) {
		Dataset ds = new DefaultDataset();
		// int t = thresh.getRGB();
		float thrsh = (thresh.getRed() + thresh.getGreen() + thresh.getBlue()) / 3;
		float width = img.getWidth();
		float height = img.getHeight();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color tmp = new Color(img.getRGB(x, y));
				float rgb = (tmp.getRed() + tmp.getGreen() + tmp.getBlue()) / 3;
				// int rgb = ;
				if (rgb > thrsh) {
					double[] arr = { x / width, (height - y) / height };
					ds.add(new DenseInstance(arr));
				}
			}
		}
		return ds;
	}
}
