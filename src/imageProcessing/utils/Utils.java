package utils;

public class Utils {
	static public int truncate(double x){
		int res;
		
		if(x > 255) res = 255;
		else if(x < 0) res = 0;
		else res = (int) x;
		
		return res;
	}
}
