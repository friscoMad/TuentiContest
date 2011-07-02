package tuenti.contest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tuenti.scanner.TuentiScanner;

public class TLang {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TuentiScanner in = new TuentiScanner(System.in);
		Pattern first = Pattern.compile("\\^([#|@|=])\\s+(-?\\d+)\\s+(-?\\d+)\\$");
		Pattern others = Pattern.compile("\\^@\\s+(\\d+)\\$");
		while (in.hasNextLine()) {
			String line = in.nextLine();
	        Matcher m = first.matcher(line);
	        Matcher m2 = null;
	        boolean cont = m.find();
	        boolean cont2 = false;
	        String res = "";
	        while (cont || cont2) {
	        	if (cont) {
	        		res = operate(m.group(1), m.group(2), m.group(3));
	        		line = line.replace(m.group(0), res);
	        	} else {
	        		line = line.replace(m2.group(0), "-"+m2.group(1));
	        	}
	        	
	        	m = first.matcher(line.trim());
	        	m2 = others.matcher(line.trim());
	        	cont = m.find();
	        	cont2 = m2.find(); 
	        }
	        System.out.println(line.trim());	        
		}
	}
	
	private static String operate(String operator, String number1, String number2) {
		if (operator.equals("@")) {
			return (Integer.parseInt(number1) - Integer.parseInt(number2)) + "";
		} else if (operator.equals("#")) {
			return (Integer.parseInt(number1) * Integer.parseInt(number2)) + "";
		} else {
			return (Integer.parseInt(number1) + Integer.parseInt(number2)) + "";
		}

	}
}
