package imageProcessing.Filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;
import imageProcessing.utils.Utils;

/**
 * Class that modifies color bases on a RGB (red, green, blue) system.
 */
public class ColorScale {

	static private int scaleFilter(Raster raster, int i, int j, int channel, double alpha){
		return Utils.truncate(raster.getSample(j, i, channel) * alpha);
	}


	/**
	 * Methor for modifying a RGB channel using a multiplication factor
	 *
	 * Ex.: scale(img, 0, 1.25) - a 25% increase in red
	 * Ex.: scale(img, 2, 0.75) - a 75% decrease in the amount of blue in the original image
	 *
	 * @param originalImage: the original image
	 * @param op - the RGB channel that will be modified
	 * @param alpha - the multiplication factor of the channel. Values from 0 to 255
	 *              (but values from 0 to 5 have the most profound effect)
	 * @return the modified ImageModel
	 */
	static public ImageModel scale(ImageModel originalImage, String op, double alpha){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = cnvImage.getWritableRaster();
		int i, j, channel = selectChannel(op);
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				raster.setSample(j, i, channel, scaleFilter(raster, i, j, channel, alpha));
			}
		}
		
		return new ImageModel(img, "Color scale scale");
	}
	
	static private int addFilter(Raster raster, int i, int j, int channel, int n){
		return Utils.truncate(raster.getSample(j, i, channel) + n);
	}


	/**
	 * Method for modifying a RGB channel using a additive factor.
	 *
	 * Ex.: scale(img, 0, 25) - All the values of red will have a 25% increase.
	 *
	 * @param originalImage: The original iamge
	 * @param op - the RGB channel that will be modified
	 * @param n - The additive factor of the channel. Values from 0 to 255.
	 * @return the modified ImageModel
	 */
	static public ImageModel add(ImageModel originalImage, String op, int n){
		ImageModel cnvImage = originalImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		int i, j, channel = selectChannel(op);
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				raster.setSample(j, i, channel, addFilter(raster, i, j, channel, n));
			}
		}
		
		return new ImageModel(img, "Color scale add");
	}


	/**
	 * Function that select a channel (red, green, blue) decoding it into a int representation.
	 * (red = 0, blue = 1, green = 2)
	 * @param channel: The selected channel, as a string
	 * @return A int that represents that channel
	 */
	private static int selectChannel(String channel) {
		switch (channel) {
			case "red":
				return 0;
			case "grenn":
				return 1;
			case "blue":
				return 2;
		}
		throw new RuntimeException("Wrong color typed used");
	}
}
