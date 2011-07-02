package tuenti.contest;

import java.util.Scanner;

public class Biologist {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Biologist main = new Biologist();
		main.process();
	}
	
	private void process() {
		Scanner in = new Scanner(System.in);
		int i = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] values = line.split(" ");
			if (values.length < 2) {
				continue;
			}
			String input = values[0];
			String output = values[1];
			System.out.println(magic(input, output));
		}
		in.close();
	}	
	
	private String magic (String s1, String s2) {
		if (s1.length() > s2.length()) {
			String s3 = s2;
			s2 = s1;
			s1 = s3;
		}
		int len1 = s1.length();
		int len2 = s2.length();
		int start = -1;
		int maxLen = 0;
		for( int i = 0; i < len1; i++) {
			for (int j = 0; j + maxLen < len2; j++) {
				if (s1.charAt(i) == s2.charAt(j) 
						&& i+maxLen < len1 && j+maxLen < len2
						&& s1.charAt(i+maxLen) == s2.charAt(j+maxLen) ) {
					//We verify the whole string is matching.
					if (!s1.substring(i,i+maxLen+1).equals(s2.substring(j,j+maxLen+1))) {
						continue;
					}
					int extraLen = 0;
					for (int k = 1; k + maxLen+j < len2 && i+maxLen+k < len1; k++) {
						if (s1.charAt(i+maxLen+k) == s2.charAt(j+maxLen+k)) {
							extraLen++;
						} else {
							break;
						}
					}
					if (extraLen > 0) {
						maxLen = maxLen + extraLen;
						start = i;
					}
				}
			}
		}
		return s1.substring(start,start+maxLen+1);
		
	}
}
