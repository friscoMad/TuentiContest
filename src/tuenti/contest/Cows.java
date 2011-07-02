package tuenti.contest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cows {

	protected class Cow {
		private int weight;
		private int milk;
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
		public int getMilk() {
			return milk;
		}
		public void setMilk(int milk) {
			this.milk = milk;
		}
		public String toString() {
			return "\t{Cow weight: "+this.weight + "\n\tCow milk: "+this.milk+"}";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Cows main = new Cows();
		main.process();
	}
	
	private void process() {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {		
			String line = in.nextLine();
			String[] params = line.split(" "); 
			int numCows = Integer.parseInt(params[0]);
			int maxWeight = Integer.parseInt(params[1]);
			String[] weights = params[2].split(",");
			String[] milk = params[3].split(",");
			if (weights.length != milk.length || milk.length != numCows) {
				continue;
			}
			ArrayList<Cow> herd = new ArrayList<Cow>(); 
			for (int i = 0; i < numCows; i++) {
				Cow cow = new Cow();
				cow.setMilk(Integer.parseInt(milk[i]));
				cow.setWeight(Integer.parseInt(weights[i]));
				herd.add(cow);
			}
			//System.out.println("1");
			System.out.println(optimizeHerd(maxWeight, herd));			
		}
		in.close();
	}
	
	private int optimizeHerd(int weight, ArrayList<Cow> herd) {
		if (herd.size() == 0 || weight == 0) {
			return 0;
		} else {
			int max = 0;			
			ArrayList<Cow> herd2 = (ArrayList<Cow>)herd.clone();
			while(herd2.size() > 0) {
				Cow cow = herd2.get(0);
				herd2.remove(0);
				if (cow.getWeight() <= weight) {					
					herd2.remove(cow);
					int local = optimizeHerd(weight-cow.getWeight(), herd2);
					if (cow.getMilk() + local > max) {
						max = cow.getMilk() + local;
					}
				}
			}
			return max;
		}		
	}	
}
