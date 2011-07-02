package tuenti.contest;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

public class Riddle {

	private static Logger log = Logger.getLogger(Riddle.class); 
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Riddle main = new Riddle();
		main.process();
		//main.magic();
	}
	private void process() throws IOException {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			BASE64Decoder b64dec = new BASE64Decoder();
	        byte[] output = b64dec.decodeBuffer(in.nextLine());
		    FileOutputStream fos = new FileOutputStream("Riddle.png");
		    fos.write(output);
		    fos.close();
			System.out.println(3193 + 388);
		}
	}
	
	private void magic() throws IOException {
		File file = new File("../data/Riddle.png");
		BufferedImage img = ImageIO.read(file);
		int lastgreen = 0;
		int pixel;
		Color col;
		String text = "";
		//System.out.println("");
		int step = 0;
		for (int i = 0; i < img.getWidth(); i++) {			
			pixel = img.getRGB(i, 0);
			col = new Color(pixel);
			if (col.getBlue() == 0) {
				lastgreen++;
				continue;
			} else {
				step = 1;
				lastgreen =0;
			}
			for (int y = 0; y < img.getHeight(); y++) {
				pixel = img.getRGB(i, y);
				col = new Color(pixel);
				if (col.getBlue() == 0) {
					continue;
				}
				System.out.print((char)(col.getRed()));
				System.out.print((char)(col.getGreen()));
				System.out.print((char)(col.getBlue()));
			}
			
		}
		/*
		Freqs freqs = new Freqs(text);
		freqs.countChars();
		freqs.orderTable();
		freqs.printTable();
		*/
		//System.out.println(text); 
	}
}
