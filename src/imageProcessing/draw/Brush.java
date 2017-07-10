package imageProcessing.draw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


import imageProcessing.Models.ImageModel;
import static imageProcessing.utils.Utils.getDistance;


/**
 * Class for drawing on the image using the mouse, typically know as "brush".
 *
 */
public class Brush {
	/**
	 * 
	 * @param srcImg - Original image
	 * @param x - The point's x coordinate
	 * @param y - The point's y coordinate
	 * @param r - The size of the radius of the circular brush
	 * @param c - The brush's color
	 * @return a new image with the changes applied
	 */
	static public ImageModel paint(ImageModel srcImg, int x, int y, int r, Color c) {
		ImageModel newImg = srcImg.copy(); 
		BufferedImage bImg = newImg.getBufferedImage();
		WritableRaster raster = bImg.getRaster();
		
		int i, j;

		for(i = y - r; i < y + r && i < bImg.getHeight(); i++) {
			if(i > -1) {
				for(j = x - r; j < x + r && j < bImg.getWidth(); j++) {
					if(j > -1 && getDistance(x, y, j, i) <= r) {
						raster.setSample(j, i, 0, c.getRed());
						raster.setSample(j, i, 1, c.getGreen());
						raster.setSample(j, i, 2, c.getBlue());
					}
				}
			}
		}

		return new ImageModel(bImg);
	}
	
	/**
	 * 
	 * @param srcImg - Original image
	 * @param x - The point's x coordinate
	 * @param y - The point's y coordinate
	 * @param size - The size of the side of the brush's square
	 * @param c - The brush's color
	 * @return a new image with the changes applied
	 */
	
	static public ImageModel paintSquare(ImageModel srcImg, int x, int y, int size, Color c) {
		ImageModel newImg = srcImg.copy(); 
		BufferedImage bImg = newImg.getBufferedImage();
		WritableRaster raster = bImg.getRaster();
		
		int i, j;
		
		for(i = y - size; i < y + size && i < bImg.getHeight(); i++) {
			if(i > -1) {
				for(j = x - size; j < x + size && j < bImg.getWidth(); j++) {
					if(j > -1) {
						raster.setSample(j, i, 0, c.getRed());
						raster.setSample(j, i, 1, c.getGreen());
						raster.setSample(j, i, 2, c.getBlue());
					}
				}
			}
		}

		return new ImageModel(bImg);
	}
}
