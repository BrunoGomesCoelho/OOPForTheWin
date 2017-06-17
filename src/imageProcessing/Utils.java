package utils;

public class Utils {
	static public int truncate(double x){
		int res;
		
		if(x > 255) res = 255;
		else if(x < 0) res = 0;
		else res = (int) x;
		
		return res;
	}
	
	static public double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
