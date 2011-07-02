package tuenti.contest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HotKeys {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HotKeys main = new HotKeys();
		main.process();
	}
	
	private void process() {
		Scanner in = new Scanner(System.in);
		int hotkeys = in.nextInt();
		HashMap<String,ArrayList<String>> operationsPerKey = new HashMap<String,ArrayList<String>>();
		ArrayList<String> possible = new ArrayList<String>();
		in.nextLine();
		for (int i = 0; i <  hotkeys; i++) {
			String keyLine = in.nextLine();
			String operation = in.nextLine().trim();
			String[] keys = keyLine.split(" ");
			for (String key : keys) {
				if (!operationsPerKey.containsKey(key)) {
					operationsPerKey.put(key, new ArrayList<String>());
				}
				operationsPerKey.get(key).add(operation);
			}
			possible.add(operation);
		}
		int tests = in.nextInt();
		in.nextLine();
		for (int i = 0; i <  tests; i++) {
			String keyLine = in.nextLine();
			String[] keys = keyLine.split(" ");
			ArrayList<String> possibleTest = (ArrayList<String>)possible.clone();
			for (String key : keys) {
				if (!operationsPerKey.containsKey(key)) {
					continue;
				}
				ArrayList<String> operations = operationsPerKey.get(key);
				int j = 0; 
				while (j < possibleTest.size() ) {
					String operation = possibleTest.get(j);
					if (!operations.contains(operation)) {
						possibleTest.remove(operation);
					} else {
						j++;
					}
				}
			}
			if (possibleTest.size() != 1) {
				System.out.println("Houston tenemos un problema");
			}
			System.out.println(possibleTest.get(0));
		}
		in.close();
	}	
	
}
