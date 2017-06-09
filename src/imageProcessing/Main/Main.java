package imageProcessing.Main;

public class Main {

	public static void main(String[] args) {
		/*
		ImageModel i = new ImageModel("peeps.jpg");
		ImageModel i2 = new ImageModel("heart2.jpg");
		ImageModel res;

		res = Convolution.convolution(i, Convolution.SHARPEN);
		
		for(int a = 0 ; a < 3; a++) {
			res = Convolution.convolution(res, Convolution.SHARPEN);
		} 
	
		ImageModel.write(res, "batataSHARPEN", "jpg");

		res = Enhancement.radioactive(i);
		res = ImageModel.blend(i, res, 0, 0, 0.25);
		ImageModel.write(res, "batataRADIOACTIVE", "jpg");
		
		res = Enhancement.hue(i, -0.15);
		ImageModel.write(res, "batataHUE", "jpg");
		
		res = Enhancement.saturate(i, 0.8);
		ImageModel.write(res, "batataSATURATE", "jpg");
		
		res = Enhancement.bright(i, 0.15);
		ImageModel.write(res, "batataBRIGHT", "jpg");
		
		res = ImageModel.blend(i, i2, 50, 50, 0.38);
		ImageModel.write(res, "batataBLEND", "jpg");
		
		res = ImageModel.concat(i, i2, 50, 50);
		ImageModel.write(res, "batataCONCAT", "jpg");
		
		res = Enhancement.negative(i);
		ImageModel.write(res, "batataNEGATIVE", "jpg");
		
		res = Enhancement.vignette(i);
		ImageModel.write(res, "batataVIGNETTE_DIAGONAL", "jpg");
		
		res = Enhancement.vignette(i, 500);
		ImageModel.write(res, "batataVIGNETTE_500", "jpg");
		
		res = Enhancement.vignette(i, 750);
		ImageModel.write(res, "batataVIGNETTE_750", "jpg");
		
		int[] color = new int[3];
		color[0] = 255;
		color[1] = 255;
		color[2] = 255;
		res = Enhancement.vignette(i, 1000, color);
		ImageModel.write(res, "batataVIGNETTE_750", "jpg");
	
		res = Enhancement.grayScale(res);
		res = Enhancement.poster(res, 5);
		res = ColorScale.scale(res, 0, 1.85);
		res = ColorScale.scale(res, 1, 1.85);
		ImageModel.write(res, "batataVIGNETTE", "jpg");

		res = Enhancement.contrastModulation(i);
		ImageModel.write(res, "batataMOD", "jpg");
		
		res = Enhancement.noise(i, 25);
		ImageModel.write(res, "batataNOISE", "jpg");
		
		res = Enhancement.binary(i);
		ImageModel.write(res, "batataBINARY", "jpg");
		
		res = Enhancement.grayScale(i);
		ImageModel.write(res, "batataGRAYSCALE", "jpg");
		
		res = Enhancement.poster(i, 5);
		ImageModel.write(res, "batataPOSTER", "jpg");
		
		res = Convolution.convolution(i, Convolution.BLUR);
		ImageModel.write(res, "batataBLUR", "jpg");
		
		res = Convolution.convolution(i, Convolution.SHARPEN);
				
		ImageModel.write(res, "batataSHARPEN", "jpg");
		
		res = Convolution.convolution(i, Convolution.EMBOSS);
		ImageModel.write(res, "batataEMBOSS", "jpg");
		
		res = ColorScale.scale(i, 0, 1.25);
		ImageModel.write(res, "batataSCALE_RED_25", "jpg");
		
		res = ColorScale.scale(i, 1, 1.5);
		ImageModel.write(res, "batataSCALE_GREEN_50", "jpg");
		
		res = ColorScale.scale(i, 2, 1.75);
		ImageModel.write(res, "batataSCALE_BLUE_75", "jpg");
		
		res = ColorScale.add(i, 2, 20);
		ImageModel.write(res, "batataMORE_BLUE", "jpg");
		
		res = ColorScale.add(i, 1, -255);
		ImageModel.write(res, "batataNO_GREEN", "jpg");
		
		res = Enhancement.pixelate(i, 15);
		ImageModel.write(res, "batataPIXELATE", "jpg");
		
		res = Manipulate.mirror(i, Manipulate.VERTICAL);
		ImageModel.write(res, "batataVERTICAL", "jpg");		
		
		res = Manipulate.mirror(i, Manipulate.HORIZONTAL);
		ImageModel.write(res, "batataHORIZONTAL", "jpg");
		
		res = Manipulate.mirror(i, 3);
		ImageModel.write(res, "batataMIROR_DIAGONNAL", "jpg");
		
		ImageModel coolOldEffect = Convolution.convolution(i, Convolution.BLUR);
		coolOldEffect = Enhancement.noise(coolOldEffect, 12);
		coolOldEffect = Enhancement.poster(coolOldEffect, 5);
		coolOldEffect = Enhancement.grayScale(coolOldEffect);
		coolOldEffect = ColorScale.add(coolOldEffect, 0, 20);
		coolOldEffect = ColorScale.add(coolOldEffect, 1, 20);
		ImageModel.write(coolOldEffect, "wanted", "jpg");
		
		System.out.printf("THE END");
		*/
	}
}
