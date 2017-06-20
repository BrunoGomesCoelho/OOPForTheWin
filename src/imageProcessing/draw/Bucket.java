package imageProcessing.draw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;

/**
 *  Class for swapping a connected area with the same color with another, typically know as "bucket"
 *
 */
public class Bucket {
	/**
	 *
	 * @param srcImg - Original image
	 * @param x - The mouse's x coordinate
	 * @param y - The mouse's y coordinate
	 * @param c - The new collor to be used in filling the area
	 * @param level - Level of detail while filling the area, from 0 (maximum detail) to 8 (the whole image)
	 * @return A new image with the modifications done
	 */
	static public ImageModel paint(ImageModel srcImg, int x, int y, Color c, int level){
		ImageModel newImg = srcImg.copy(); 
		BufferedImage bImg = newImg.getBufferedImage();
		WritableRaster raster = bImg.getRaster();
		
		Color selected = new Color(
			raster.getSample(x, y, 0) >> level << level,
			raster.getSample(x, y, 1) >> level << level,
			raster.getSample(x, y, 2) >> level << level
		);
		
		int i, j;

		for(i = 0; i < raster.getHeight(); i++) {
			for(j = 0; j < raster.getWidth(); j++) {
				if(raster.getSample(j, i, 0) >> level << level == selected.getRed() &&
					raster.getSample(j, i, 1) >> level << level == selected.getGreen() &&
					raster.getSample(j, i, 2) >> level << level == selected.getBlue()) {
					
					raster.setSample(j, i, 0, c.getRed());
					raster.setSample(j, i, 1, c.getGreen());
					raster.setSample(j, i, 2, c.getBlue());
				}
			}
		}

		return new ImageModel(bImg);
	}
}
