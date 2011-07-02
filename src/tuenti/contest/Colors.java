package tuenti.contest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Colors {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		Colors main = new Colors();
		main.process();
	}
	public void process() throws IOException {
		File file = new File("../data/colors/trabaja.bmp");
		BufferedImage img = ImageIO.read(file);
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String test = in.nextLine();
			char color = test.charAt(0);
			int line = Integer.parseInt(test.substring(1));
			int colorPos = 0;
			switch (color) {
			case 'r':
			case 'R':
				colorPos = 0;
				break;
			case 'g':
			case 'G':
				colorPos = 1;
				break;
			case 'b':
			case 'B':
				colorPos = 2;
				break;
			}
			System.out.println(magic(img,line)[colorPos] + 1);
		}
	}

	public int[] magic (BufferedImage img, int line) {
		int y = line;
		int[] total = new int[3];
		total[0] =0;
		total[1] =0;
		total[2] =0;
		int r,g,b, pixel;
		for (int i = 0; i < img.getWidth(); i++) {
			pixel = img.getRGB(i, y);
			b = pixel & 0x00FF;
			g = (pixel & 0x0FF00) >> 8;
			r = (pixel & 0xFF0000) >>16;
			//System.out.println("r:" + r + " g: " + g + " b: "+b);
			total[0] = total[0] + r;
			total[1] = total[1] + g;
			total[2] = total[2] + b;
		}
		return total;
	}
	
}
