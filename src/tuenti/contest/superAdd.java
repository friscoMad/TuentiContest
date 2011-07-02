package tuenti.contest;

import java.util.Scanner;

import tuenti.scanner.TuentiScanner;

public class superAdd {
	public static void main(String[] args) {
		TuentiScanner in = new TuentiScanner(System.in);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			Scanner lineIn = new Scanner(line);
			String total = "0";
			while (lineIn.hasNext("[+-]{0,1}\\d+")) {
				String sNumber = lineIn.next("[+-]{0,1}\\d+").trim();
				if (sNumber.startsWith("+")) {
					sNumber = sNumber.substring(1);
				} 
				if (!sNumber.startsWith("-") && !total.startsWith("-")) {
					total = add(total, sNumber);
				} else if (sNumber.startsWith("-") && total.startsWith("-")) {
					total = "-" + add(sNumber.substring(1), total.substring(1));
				} else {
					String unsigned1 = total;
					String unsigned2 = sNumber;
					if (unsigned1.startsWith("-")) {
						unsigned1 = unsigned1.substring(1);
					}
					if (unsigned2.startsWith("-")) {
						unsigned2 = unsigned2.substring(1);
					}

					if (unsigned2.length() < unsigned1.length() || 
							(unsigned2.length() == unsigned1.length() && unsigned2.compareTo(unsigned1) < 0)) {
						if (sNumber.startsWith("-")) {
							total = rem(unsigned1, unsigned2);
						} else {
							total = "-" + rem(unsigned1, unsigned2);
						}
					} else {
						if (sNumber.startsWith("-")) {
							total = "-" + rem(unsigned2, unsigned1);
						} else {
							total = rem(unsigned2, unsigned1);
						}						
					}
				}
				if (total.startsWith("-")) {
					total = total.substring(1);
					while (total.startsWith("0") && total.length() > 1) {
						total = total.substring(1);
					}
					if (!total.equals("0")) {
						total = "-" + total;
					}
				} else {
					while (total.startsWith("0") && total.length() > 1) {
						total = total.substring(1);
					}
				}
			}
			lineIn.close();
			System.out.println(total);
		}
	}
	
	private static String add(String number1, String number2, boolean carry, boolean minus) {
		if (number1.equals("") && number2.equals("")) {
			if (carry) {
				return "1";
			} else {
				return "";
			}
		}
		if (number1.equals("")) {
			number1 = number2;
			number2 = "";
		}
		if (number2.equals("")) {
			if (!carry) {
				return number1;
			}
			char digit = number1.charAt(number1.length()-1);
			int value = 0;
			if (minus) {
				value = Integer.parseInt(digit+"") - 1;
			} else {
				value = Integer.parseInt(digit+"") + 1;
			}
			String number1res = number1.substring(0, number1.length()-1);
			if (value >= 10) {
				return add(number1res,"",true, minus) + (value%10);
			} else if (value < 0) {
				return add(number1res,"",true, minus) + (value + 10);
			} else {
				return number1res + value;
			}
		} else {
			char digit1 = number1.charAt(number1.length()-1);			
			char digit2 = number2.charAt(number2.length()-1);
			int value = 0;
			if (minus) {
				value = Integer.parseInt(digit1+"") - Integer.parseInt(digit2+"") - (carry?1:0);
			} else {
				value = Integer.parseInt(digit2+"") + Integer.parseInt(digit1+"") + (carry?1:0);
			}
			String number1res = number1.substring(0, number1.length()-1);
			String number2res = number2.substring(0, number2.length()-1);
			if (value >= 10) {
				return add(number1res,number2res,true, minus) + (value%10);
			} else if (value < 0) {
				return add(number1res,number2res,true, minus) + (value + 10);
			} else {
				return add(number1res,number2res,false, minus) + value;
			}			
		}
	}
	
	private static String add(String number1, String number2) {
		return add(number1, number2, false, false);
		
	}
	private static String rem(String number1, String number2) {
		return add(number1, number2, false, true);
	}
	
}
