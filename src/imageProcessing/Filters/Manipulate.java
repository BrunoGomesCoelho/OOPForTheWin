package imageProcessing.Filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import imageProcessing.Models.ImageModel;


public class Manipulate {

	/**
	 * Method for the normalization of a value "n", from de interval [min, max]  to [a, b]
	 *
	 * @param n   - value to be analised
	 * @param min - old minimum limit for n
	 * @param max - old maximum limit for n
	 * @param a   - new minimum limit for n
	 * @param b   - new maximum limit for n
	 * @return the new value for n in its new limits
	 */

	static private int normalize(int n, int min, int max, int a, int b) {
		return ((b - a) * (n - min) / (max - min) + a);
	}

	/**
	 * Method for resizing a image
	 *
	 * @param srcImage Original image
	 * @param sizeX    - X dimension of the new image, > 0
	 * @param sizeY    - Y dimension of the new image, > 0
	 * @return the resized image
	 */
	static public ImageModel resize(ImageModel srcImage, int sizeX, int sizeY) {
		ImageModel cnvImage = new ImageModel(sizeX, sizeY);
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();

		int i, j, k;
		int x, y;

		for (i = 0; i < img.getHeight(); i++) {
			for (j = 0; j < img.getWidth(); j++) {
				x = normalize(j, 0, sizeX, 0, srcRaster.getWidth());
				y = normalize(i, 0, sizeY, 0, srcRaster.getHeight());

				for (k = 0; k < 3; k++) {
					raster.setSample(j, i, k, srcRaster.getSample(x, y, k));
				}
			}
		}

		return new ImageModel(img);
	}

	/**
	 * Method for resizing a image given a resize factor
	 *
	 * @param srcImage Original image
	 * @param percent  - Resize factor. ex: 0.75, the new image will be 75% smaller than the original one
	 * @return the resized image
	 */
	static public ImageModel resize(ImageModel srcImage, double percent) {
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		int sizeX = (int) (srcRaster.getWidth() * percent);
		int sizeY = (int) (srcRaster.getHeight() * percent);
		int i, j, k, x, y;

		ImageModel cnvImage = new ImageModel(sizeX, sizeY);
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();

		for (i = 0; i < img.getHeight(); i++) {
			for (j = 0; j < img.getWidth(); j++) {
				x = normalize(j, 0, sizeX, 0, srcRaster.getWidth());
				y = normalize(i, 0, sizeY, 0, srcRaster.getHeight());

				for (k = 0; k < 3; k++) {
					raster.setSample(j, i, k, srcRaster.getSample(x, y, k));
				}
			}
		}

		return new ImageModel(img);
	}

	/**
	 * Methor that returns weather or not a given point is inside the image
	 *
	 * @param img - the image to be analised
	 * @param x   - X coordinate of the pixel
	 * @param y   - Y coordinate of the pixel
	 * @return a boolean value, true if it is inside, false otherwise
	 */
	static private boolean withinBorders(Raster img, int x, int y) {
		return (x > -1 && y > -1 && x < img.getWidth() && y < img.getHeight());
	}

	/**
	 * Method for rotating a image
	 *
	 * @param srcImage Original image
	 * @param angle    - the angle of rotation as radians, can be any double value
	 * @return the rotaded image
	 */
	static public ImageModel rotate(ImageModel srcImage, double angle) {
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		ImageModel cnvImage = new ImageModel(srcRaster.getWidth(), srcRaster.getHeight(), BufferedImage.TYPE_INT_ARGB);

		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();

		int i, j, k;
		int x, y;

		for (i = 0; i < img.getHeight(); i++) {
			for (j = 0; j < img.getWidth(); j++) {
				x = (int) ((j - img.getWidth() / 2) * Math.cos(angle) - (i - img.getHeight() / 2) * Math.sin(angle));
				x += img.getWidth() / 2;

				y = (int) ((j - img.getWidth() / 2) * Math.sin(angle) + (i - img.getHeight() / 2) * Math.cos(angle));
				y += img.getHeight() / 2;

				if (withinBorders(raster, x, y)) {
					for (k = 0; k < 3; k++) {
						raster.setSample(x, y, k, srcRaster.getSample(j, i, k));
						if (withinBorders(raster, x + 1, y))
							raster.setSample(x + 1, y, k, srcRaster.getSample(j, i, k));
						if (withinBorders(raster, x, y + 1))
							raster.setSample(x, y + 1, k, srcRaster.getSample(j, i, k));
					}

					raster.setSample(x, y, k, 255);
					if (withinBorders(raster, x + 1, y))
						raster.setSample(x + 1, y, k, 255);
					if (withinBorders(raster, x, y + 1))
						raster.setSample(x, y + 1, k, 255);
				}
			}
		}

		return new ImageModel(img);
	}


	/**
	 * Method that mirrors a image given a certain direction
	 *
	 * @param srcImage Original image
	 * @param dir      - The direction to mirror the image(horizontal, vertical, diagonally)
	 * @return the modified image
	 */
	static public ImageModel mirror(ImageModel srcImage, String dir) {
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		WritableRaster raster = img.getRaster();

		int i, j, k;
		int x, y;

		for (i = 0; i < img.getHeight(); i++) {
			for (j = 0; j < img.getWidth(); j++) {

				switch (dir) {
					case "vertical":
						x = j;
						y = img.getHeight() - 1 - i;
						break;
					case "horizontal":
						x = img.getWidth() - 1 - j;
						y = i;
						break;
					default:
						x = img.getWidth() - 1 - j;
						y = img.getHeight() - 1 - i;
						break;
				}

				for (k = 0; k < 3; k++) {
					raster.setSample(x, y, k, srcRaster.getSample(j, i, k));
				}
			}
		}

		return new ImageModel(img);
	}
}