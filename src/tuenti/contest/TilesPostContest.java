package tuenti.contest;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class TilesPostContest {
	private static Logger log = Logger.getLogger(TilesPostContest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TilesPostContest main = new TilesPostContest();
		//PropertyConfigurator.configure("../log4j.properties");
		main.process();
	}
	
	private void process() {
		Scanner in = new Scanner(System.in);
		int i = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] values = line.split(" ");
			if (values.length < 3) {
				continue;
			}
			String input = values[0];
			String output = values[1];
			values = values[2].split(",");
			if (values.length < 3) {
				continue;
			}
			int add = Integer.parseInt(values[0]);
			int rem = Integer.parseInt(values[1]);
			int swap = Integer.parseInt(values[2]);
			int minSwap = Math.min(add+rem, swap);
	    	log.debug("str1: " + input);
	    	log.debug("str2: " + output);
	    	log.debug("add: " + add + " rem: " + rem + " swap: " + swap + " minSwap: " + minSwap);
			System.out.println(computeLevenshteinDistance(input, output, add, rem, minSwap));
			i++;
		}
		in.close();
	}	
	
    private static int minimum(int a, int b, int c) {
        if(a<=b && a<=c)
        {
            return a;
        } 
        if(b<=a && b<=c)
        {
            return b;
        }
        return c;
    }
 
    private static int computeLevenshteinDistance(String str1, String str2, int add, int rem, int minSwap) {
        return computeLevenshteinDistance(str1.toCharArray(),
                                          str2.toCharArray(), add, rem, minSwap);
    }
 
    private static int computeLevenshteinDistance(char [] str1, char [] str2, int add, int rem, int minSwap) {
        int [][]distance = new int[str1.length+1][str2.length+1];
        String line = "";
        for(int i=0;i<=str1.length;i++)
        {
                distance[i][0]=i*rem;
        }
        for(int j=0;j<=str2.length;j++)
        {
                distance[0][j]=j*add;
                line += "\t" + distance[0][j] ;
        }
        log.debug(line);
        for(int i=1;i<=str1.length;i++)
        {
        	line = "\t" + distance[i][0] ;
            for(int j=1;j<=str2.length;j++)
            { 
                  distance[i][j]= minimum(distance[i-1][j]+rem,
                                        distance[i][j-1]+add,
                                        distance[i-1][j-1]+
                                        ((str1[i-1]==str2[j-1])?0:minSwap));
                   line += "\t" + distance[i][j];
            }
            log.debug(line);
        }
        return distance[str1.length][str2.length];
 
    }
}
