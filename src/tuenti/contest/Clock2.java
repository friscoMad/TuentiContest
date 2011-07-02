package tuenti.contest;

import java.util.Scanner;

public class Clock2 {

	/**
	 * @param args
	 */
	
	int [] leds = new int[10];
	int start = 6;
	public static void main(String[] args) {
		Clock2 main = new Clock2();
		main.process();
	}
	
	private void process() {
		leds[0] = 6;
		leds[1] = 0;
		leds[2] = 4;
		leds[3] = 1;
		leds[4] = 1;
		leds[5] = 2;
		leds[6] = 1;
		leds[7] = 1;
		leds[8] = 4;
		leds[9] = 0;
		
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			System.out.println(calcLeds(in.nextInt()));
		}
	}
	
	private int calcLeds(int seconds) {
		
		seconds = seconds+1;
		int digit10 = ledsUpTo10(10)-5;
		int digit6 = ledsUpTo10(6)-4;
		int digit1 = seconds/10 * digit10 + ledsUpTo10(seconds%10);
		int digit2 = digit6 * (seconds / 60);  
		digit2 = digit2 + ledsUpTo10((seconds%60)/10 + 1);
		//digit2 = digit2 + seconds%10 * leds[(seconds%60)/10];
		int digit3 = digit10 * (seconds / 600);  
		digit3 = digit3 + ledsUpTo10((seconds%600)/60 + 1);
		//digit3 = digit3 + seconds%60 * leds[(seconds%600)/60];
		int digit4 = digit6 * (seconds / 3600);  
		digit4 = digit4 + ledsUpTo10((seconds%3600)/600 + 1);
		//digit4 = digit4 + seconds%600 * leds[(seconds%3600)/600];
		int digit5 = digit10 * (seconds / 36000);  
		digit5 = digit5 + ledsUpTo10((seconds%36000)/3600 + 1) ;
		//digit5 = digit5 + seconds%3600 * leds[(seconds%36000)/3600];
		int digit62 = leds[0];//This is wrong for sure

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
