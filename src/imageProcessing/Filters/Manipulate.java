package filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;

import models.ImageModel;
import models.RegionModel;

public class Manipulate {
	static public int VERTICAL = 0;
	static public int HORIZONTAL = 1;
	
	/**
	 * Fun��o para normaliza��o de um valor n, do intervalo [min, max] para [a, b];
	 * Quais valores servem de entrada, desde que min != max e min < max
	 * 
	 * @param n - valor de an�lise
	 * @param min - antigo limite de n
	 * @param max - antigo limite de n
	 * @param a - novo limite de n
	 * @param b - novo limite de n
	 * @return o novo valor de n nos novos limites
	 * 
	 */
	
	static private int normalize(int n, int min, int max, int a, int b){
		return (int) ((b - a)*(n - min)/(max - min) + a);
	}
	
	/**
	 * M�todo de redimensionamento de uma imagem
	 * 
	 * @param srcImage - imagem original
	 * @param sizeX - dimens�es da nova imagem, sendo > 0
	 * @param sizeY - dimens�es da nova imagem, sendo > 0
	 * @return imagem redimensionada
	 */
	static public ImageModel resize(ImageModel srcImage, int sizeX, int sizeY) {
		ImageModel cnvImage = new ImageModel(sizeX, sizeY);
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		int x, y;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				x = normalize(j, 0, sizeX, 0, srcRaster.getWidth());
				y = normalize(i, 0, sizeY, 0, srcRaster.getHeight());
					
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, srcRaster.getSample(x, y, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	/**
	 * M�todo de redimensionamento de uma imagem de acordo com um fator de escala
	 * 
	 * @param srcImage - imagem original
	 * @param percent - fator de escala. se percent == 0.75 a nova imagem ser� 75% menor que a original
	 * @return imagem redimensionada
	 */
	static public ImageModel resize(ImageModel srcImage, double percent) {
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		int sizeX = (int) (srcRaster.getWidth() * percent);
		int sizeY = (int) (srcRaster.getHeight() * percent);
		int i, j, k, x, y;
		
		ImageModel cnvImage = new ImageModel(sizeX, sizeY);
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				x = normalize(j, 0, sizeX, 0, srcRaster.getWidth());
				y = normalize(i, 0, sizeY, 0, srcRaster.getHeight());
					
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, srcRaster.getSample(x, y, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	/**
	 * M�todo que retorna se um ponto est� ou n�o contido em uma imagem img
	 * 
	 * @param img - imagem analizada
	 * @param x - coordenadas do pixel
	 * @param y - coordenadas do pixel
	 * @return um boolean 
	 */
	static private boolean withinBorders(Raster img, int x, int y){
		return (x > -1 && y > -1 && x < img.getWidth() && y < img.getHeight()); 
	}
	
	/**
	 * @param srcImage - Imagem original
	 * @param angle - �ngulo da rota��o - pode ser qualquer valor de double
	 * @return a imagem rotacionada
	 */
	static public ImageModel rotate(ImageModel srcImage, double angle) {
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		ImageModel cnvImage = new ImageModel(srcRaster.getWidth(), srcRaster.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		int x, y;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				x = (int) ((j - img.getWidth() / 2)* Math.cos(angle) - (i - img.getHeight() / 2) * Math.sin(angle));
				x += img.getWidth() / 2; 
				
				y = (int) ((j - img.getWidth() / 2) * Math.sin(angle) + (i - img.getHeight() / 2) * Math.cos(angle));
				y += img.getHeight() / 2;
				
				if(withinBorders(raster, x, y) && withinBorders(raster, x + 1, y)) {
					for(k = 0; k < 3; k++) {
						raster.setSample(x, y, k, srcRaster.getSample(j, i, k));
						raster.setSample(x + 1, y, k, srcRaster.getSample(j, i, k));
					}
					
					raster.setSample(x, y, k, 255);
					raster.setSample(x + 1, y, k, 255);
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	/**
	 * M�todo que estica uma imagem de acordo com um tamanho passado por par�metro.
	 * 
	 * @param srcImage - imagem original
	 * @param size - nova dimens�o da imagem esticada (maior que 0).
	 * @param dir - dire��o na qual a imagem ser� esticada (horizontal ou vertical)
	 * @return imagem modificada
	 */
	static public ImageModel strech(ImageModel srcImage, int size, int dir) {
		ImageModel cnvImage;
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		
		if(dir == VERTICAL) {
			cnvImage = new ImageModel(srcRaster.getWidth(), size);
		} else {
			cnvImage = new ImageModel(size, srcRaster.getHeight());
		}
		
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		int x, y;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				if(dir == VERTICAL){
					x = j;
					y = normalize(i, 0, size, 0, srcRaster.getHeight());
				} else {
					x = normalize(j, 0, size, 0, srcRaster.getWidth());
					y = i;
				}
				for(k = 0; k < 3; k++) {
					raster.setSample(j, i, k, srcRaster.getSample(x, y, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	/**
	 * M�todo que espelha uma imagem de acordo a dire��o dada.
	 * 
	 * @param srcImage - imagem original
	 * @param dir - dire��o na qual a imagem ser� espelhada (horizontal, vertical ou diagonal)
	 * @return imagem modificada
	 */
	static public ImageModel mirror(ImageModel srcImage, int dir) {
		ImageModel cnvImage = srcImage.copy();
		BufferedImage img = cnvImage.getBufferedImage();
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		int x, y;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {
				
				if(dir == VERTICAL) {
					x = j;
					y = img.getHeight() - 1 - i;
				} else if(dir == HORIZONTAL) {
					x = img.getWidth() - 1 - j;
					y = i;
				} else {
					x = img.getWidth() - 1 - j;
					y = img.getHeight() - 1- i;
				}
				
				for(k = 0; k < 3; k++) {					
					raster.setSample(x, y, k, srcRaster.getSample(j, i, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
	/**
	 * M�todo de recorte retangular da imagem
	 * 
	 * @param srcImage - imagem original
	 * @param x1 - coordenadas do primeiro ponto (esquerda e acima)
	 * @param y1 - coordenadas do primeiro ponto (esquerda e acima)
	 * @param x2 - coordenadas do segundo ponto (direita e abaixo) 
	 * @param y2 - coordenadas do segundo ponto (direita e abaixo)
	 * @return uma nova imagem com a parte selecionada da imagem original
	 */
	static public ImageModel crop(ImageModel srcImage, int x1, int y1, int x2, int y2) {
		ImageModel cnvImage = new ImageModel((x2 - x1), (y2 - y1));
		BufferedImage img = cnvImage.getBufferedImage();
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		WritableRaster raster = img.getRaster();
		
		int i, j, k;
		
		for(i = 0; i < img.getHeight(); i++) {
			for(j = 0; j < img.getWidth(); j++) {				
				for(k = 0; k < 3; k++) {					
					raster.setSample(j, i, k, srcRaster.getSample(j + x1, i + y1, k));
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	/**
	 * M�todo de recorte direto da imagem, atrav�s da classe RegionModel
	 * 
	 * @param srcImage
	 * @param r - regi�o que ser� cortada
	 * @return imagem com a regi�o destacada
	 */
	static public ImageModel crop(ImageModel srcImage, RegionModel r) {
		Raster srcRaster = srcImage.getBufferedImage().getRaster();
		ImageModel cnvImage = new ImageModel(srcRaster.getWidth(), srcRaster.getHeight());
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		HashMap<Integer, ArrayList<Integer>> points = r.getPoints();
		ArrayList<Integer> list;
		
		int j, k, l, start, end;
		
		for(Integer i : points.keySet()){
			list = points.get(i);
			
			for(l = 0; l < list.size(); l++){
				start = list.get(l);
				end = (l + 1 < list.size()) ? list.get(l + 1) : 0;
				
				for(j = start; j < end; j++) {
					for(k = 0; k < 3; k++) {					
						raster.setSample(j, i, k, srcRaster.getSample(j, i, k));
					}
				}
			}
		}
		
		return new ImageModel(img);
	}
	
	/**
	 * M�todo que, dado um ponto (x, y) contido na imagem, seleciona e recorta uma regi�o da imagem
	 * baseada na semelhan�a por cor.
	 * 
	 * @param srcImage - imagem original
	 * @param x - coordenadas do ponto
	 * @param y - coordenadas do ponto
	 * @param level - n�vel de detalhe do recorte. 0 � 8 (ordem de detalhe decrescente)
	 * @return imagem com a regi�o selecionada
	 */
	
	static public ImageModel selectionCrop(ImageModel srcImage, int x, int y, int level) {
		WritableRaster srcRaster = srcImage.getBufferedImage().getRaster();
		ImageModel cnvImage = new ImageModel(srcRaster.getWidth(), srcRaster.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage img = cnvImage.getBufferedImage();
		WritableRaster raster = img.getRaster();
		
		RegionModel r = RegionModel.selectRegion(srcImage, x, y, level);
		HashMap<Integer, ArrayList<Integer>> points = r.getPoints();
		ArrayList<Integer> list;
		
		int j, k;
		
		for(Integer i : points.keySet()){
			list = points.get(i);
			
			for(j = 0; j < list.size(); j++){				
				for(k = 0; k < 3; k++) {					
					raster.setSample(list.get(j), i, k, srcRaster.getSample(list.get(j), i, k));
				}
				
				raster.setSample(list.get(j), i, 3, 255);
			}
		}
		
		return new ImageModel(img);
	}
}
