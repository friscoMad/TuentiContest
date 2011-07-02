package tuenti.contest;

import java.util.Scanner;

public class Clock {

	/**
	 * @param args
	 */
	
	int [] leds = new int[10];
	public static void main(String[] args) {
		Clock main = new Clock();
		main.process();
	}
	
	private void process() {
		leds[0] = 6;
		leds[1] = 2;
		leds[2] = 5;
		leds[3] = 5;
		leds[4] = 4;
		leds[5] = 5;
		leds[6] = 6;
		leds[7] = 3;
		leds[8] = 7;
		leds[9] = 6;
		
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			System.out.println(calcLeds(in.nextInt()));
		}
	}
	
	private int calcLeds(int seconds) {
		
		seconds = seconds+1;
		int digit10 = ledsUpTo10(10);
		int digit6 = ledsUpTo10(6);
		int digit1 = seconds/10 * digit10 + ledsUpTo10(seconds%10);
		int digit2 = 10 * digit6 * (seconds / 60);  
		digit2 = digit2 + 10 * ledsUpTo10((seconds%60)/10);
		digit2 = digit2 + seconds%10 * leds[(seconds%60)/10];
		int digit3 = 60 * digit10 * (seconds / 600);  
		digit3 = digit3 + 60 * ledsUpTo10((seconds%600)/60);
		digit3 = digit3 + seconds%60 * leds[(seconds%600)/60];
		int digit4 = 600 * digit6 * (seconds / 3600);  
		digit4 = digit4 + 600 * ledsUpTo10((seconds%3600)/600);
		digit4 = digit4 + seconds%600 * leds[(seconds%3600)/600];
		int digit5 = 3600 * digit10 * (seconds / 36000);  
		digit5 = digit5 + 3600 * ledsUpTo10((seconds%36000)/3600);
		digit5 = digit5 + seconds%3600 * leds[(seconds%36000)/3600];
		int digit62 = seconds * leds[0];//This is wrong for sure

		return digit1 + digit2 + digit3 + digit4 + digit5 + digit62;
	}
	
	private int ledsUpTo10(int changes) {

		int digit = 0;
		for(int i = 0; i < changes; i++) {
			digit = digit+leds[i%10];
		}
		return digit;
	}
	private int ledsUpTo6(int changes) {

		int digit = 0;
		for(int i = 0; i < changes; i++) {
			digit = digit+leds[i%6];
		}
		return digit;
	}
}
