package tuenti.contest;

import java.util.ArrayList;
import java.util.Scanner;

public class Emirps {
	public static void main(String[] args) {	
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int max = in.nextInt();
			ArrayList<Integer> primes = new ArrayList<Integer>();
			int total = 0; 			
			for (int i = 3; i <= max; i=i+2) {
				boolean prime = true;
				boolean emirp = true;				
				String reverse = new StringBuffer(i +"").reverse().toString(); 
				int reversei = Integer.parseInt(reverse);
				if (i == reversei || reversei % 2 == 0) {
					emirp = false;
				}
				for (Integer value : primes) {
					if (value > Math.sqrt(i) && value > Math.sqrt(reversei)) {
						break;
					}
					if (i % value == 0) {
						prime = false;
						break;
					}
					if (reversei % value == 0) {
						emirp = false;
						break;
					}
					
				}
				if (prime) {
					primes.add(new Integer(i));
					if (emirp) {
						total = total + i;
					}
				}
			}
			System.out.println(total);
		}
	}
	
}
