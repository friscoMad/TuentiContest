package tuenti.contest;

import java.math.BigInteger;

import tuenti.scanner.TuentiScanner;

public class Christmas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Christmas main = new Christmas();
		main.process();
	}
	
	private void process() {
		TuentiScanner in = new TuentiScanner(System.in);
		int tests = in.nextInt();
		for (int i = 0; i <  tests; i++) {
			int lights = in.nextInt();
			BigInteger time = in.nextBigInt();
			System.out.println(magic(lights, time));
		}
		in.close();
	}	
	
	private String magic (int lights, BigInteger time) {
		String ret = "";
		BigInteger two = new BigInteger("2");
		if (time.mod(two) == BigInteger.ZERO) { //even			
			boolean first = true;
			for (BigInteger i = BigInteger.ONE; i.intValue() < lights && i.compareTo(time) < 0; i = i.add(two) ) {
				if (!first) {
					ret += " ";
				}
				first = false;
				ret += i.intValue();
			}
		} else { //odd
			boolean first = true;
			for (BigInteger i = BigInteger.ZERO; i.intValue() < lights && i.compareTo(time) < 0; i = i.add(two) ) {
				if (!first) {
					ret += " ";
				}
				first = false;
				ret += i.intValue();
			}			
		}
		if (ret.length() == 0) {
			ret = "All lights are off :(";
		}
		return ret;
	}

}
