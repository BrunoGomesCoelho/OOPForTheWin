package imageProcessing.Models;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *  Classe para a representa��o de uma regi�o em uma imagem, com coordenadas (x, y)
 *
 */
public class RegionModel {
	private HashMap<Integer, ArrayList<Integer>> points;
	
	public RegionModel(){
		points = new HashMap<Integer, ArrayList<Integer>>();
	}
	/**
	 * Método que adiciona um novo ponto (x, y) ao objeto. N�o existem pontos repetidos.
	 * Ela deve receber uma s�rie de pontos de leitura do mouse e armazenar eles nesta estrutura!
	 * 
	 * @param x - coordenadas do ponto
	 * @param y - coordenadas do ponto
	 */
	public void addPoint(int x, int y){
		ArrayList<Integer> list;
		
		if(points.containsKey(y)) {
			list = points.get(y);
			
			if(list.contains(x)) {
				return;
			}
			
		} else {
			list = new ArrayList<>();
			points.put(y, list);
		}
		
		list.add(x);
	}
	
	/**
	 * M[etodo que confere se um ponto (x, y) est� presente no objeto
	 * 
	 * @param x - coordenadas do ponto
	 * @param y - coordenadas do ponto
	 * @return um boolean indicando a exist�ncia ou n�o do ponto
	 */
	public boolean contains(int x, int y){
		ArrayList<Integer> list;
		
		if(points.containsKey(y)) {
			list = points.get(y);
			return list.contains(x);
		}
		
		return false;
	}
	
	/**
	 * Método para a ordena��o dos valores de y.
	 */
	public void sort(){
		ArrayList<Integer> list;
		
		for(Integer i : points.keySet()){
			list = points.get(i);
			Collections.sort(list);
		}
		
	}
	/**
	 * Método que retorna a estrutura que cont�m os pontos.
	 * 
	 * @return HashMap contendo os pontos. As chaves s�o os valores de y contidos na regi�o,
	 * enquanto que os valores s�o ArrayLists contendo os valores de x relacionados ao y especificado.
	 */
	public HashMap<Integer, ArrayList<Integer>> getPoints(){
		return points;
	}
	
	/**
	 * Método que confere se um ponto pertence a uma mesma regi�o indicada pela cor ref
	 * 
	 * @param raster - imagem analizada
	 * @param i - coordenadas do pixel
	 * @param j - coordenadas do pixel
	 * @param level - n�vel de detalhe da sele��o - 0 � 8 (ordem de detalhe decrescente)
	 * @param ref - cor de refer�ncia da regi�o
	 * @return // TODO, não sei o eu vem aqui
	 */
	static private boolean sameRegion(Raster raster, int i, int j, int level, Color ref){
		return (raster.getSample(j, i, 0) >> level << level == ref.getRed() &&
				raster.getSample(j, i, 1) >> level << level == ref.getGreen() &&
				raster.getSample(j, i, 2) >> level << level == ref.getBlue());
	}
	
	/**
	 * Método para cria��o de um objeto RegionModel baseada em sele��o por regi�o por cor
	 * 
	 * @param src - imagem original
	 * @param x - Coordenadas do clique do mouse
	 * @param y - Coordenadas do clique do mouse
	 * @param level - n�vel de detalhe da sele��o - 0 � 8 (ordem de detalhe decrescente)
	 * @return A regi�o selecionada
	 */
	
	static public RegionModel selectRegion(ImageModel src, int x, int y, int level){
		BufferedImage img = src.getBufferedImage();
		Raster raster = img.getRaster();
		RegionModel r = new RegionModel();
		int i, j;
		
		Color ref = new Color(
			raster.getSample(x, y, 0) >> level << level,
			raster.getSample(x, y, 1) >> level << level,
			raster.getSample(x, y, 2) >> level << level
		);
		
		for(i = 0; i < raster.getHeight(); i++) {
			for(j = 0; j < raster.getWidth(); j++) {
				if(sameRegion(raster, i, j, level, ref)){
					r.addPoint(j, i);
				}
			}
		}
		
		return r;
	}
}
