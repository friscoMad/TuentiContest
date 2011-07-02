package tuenti.contest;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Tiles {
	
	protected class Candidate {
		private int cost;
		private String option;
		public int getCost() {
			return cost;
		}
		public void setCost(int cost) {
			this.cost = cost;
		}
		public String getOption() {
			return option;
		}
		public void setOption(String option) {
			this.option = option;
		}
		public Candidate(int cost, String option) {
			super();
			this.cost = cost;
			this.option = option;
		}
		public boolean equals(Candidate c) {
			return (cost == c.getCost() &&  option.equals(c.getOption()));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tiles main = new Tiles();
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
			System.out.println(magic(input, output, add, rem, swap));
			i++;
		}
		in.close();
	}	
	
	private int magic (String input, String output, int add, int rem, int swap) {
		Logger log = Logger.getLogger("Magic");
		log.debug(input);
		log.debug(output);
		ArrayList<Character> chars = new ArrayList<Character>();
		for(int i = 0; i < output.length(); i++) {
			if (chars.indexOf(output.charAt(i)) < 0) {
				chars.add(output.charAt(i));
			}
		}
		int charsNotInOut = 0;
		for(int i = 0; i < input.length(); i++) {
			if (chars.indexOf(input.charAt(i)) < 0) {
				charsNotInOut++;
			}
		}
		log.debug("input chars not in out :" + charsNotInOut);
		log.debug("distance:" + distance(input, output));
		if (distance(input, output) == 0) {
			return 0;
		}
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate(0,input));
		int result = 0; 
		if (input.length() - output.length() > 0) { //Input is longer
			candidates = findBestRemove(output, candidates, chars);
			result = add * (input.length() - output.length());
		} else if (input.length() - output.length() < 0) { //Output is longer
			candidates = findBestInsert(output, candidates, chars);
			result = add * (output.length() - input.length());
		}
		log.debug("Candidatos:");
		for(Candidate candidate : candidates) {
			log.debug(candidate.getOption());
		}
		log.debug("---------");
		int distance = distance(candidates.get(0).getOption(), output);
		log.debug("distance:" + distance);
		//if (2*swap > add + rem) { //Try to find distant swaps improvements
		int minCost = (swap<=add+rem?swap:add+rem);
		boolean improved = false;
		ArrayList<Candidate> nextCandidates = new ArrayList<Candidate>();
		int length1 = output.length();
		String option = "";
		int diff = 0, cost = 0;
		int dist = 0;
		//Lets try to find chunks of i characters displaced
		do {
			log.debug("expected current distance:" + distance(candidates.get(0).getOption(), output));
			float best = 1.0f/ minCost, newRatio; 
			int bestCost = -1;
			improved = false;
			for (Candidate candidate : candidates) {
				log.debug(candidate.getOption());
				log.debug("current distance:" + distance(candidate.getOption(), output));
				int length2 = candidate.getOption().length();
				for (int i = 0; i < length2; i++) {
					char car = candidate.getOption().charAt(i);
					for (int j = 0; j < length1; j++) {
						if (i == j)
							continue;
						char car2 = output.charAt(j);
						if (car2 == car) {
							dist = i - j;
							for (int k = length1-1; k > j; k-- ) {
								if (k + dist < length2
										&&	output.charAt(k) == candidate.getOption().charAt(k + dist)) {
									cost = Math.abs(dist) * (add + rem);
									if (i < j) {
										diff = distance(output.substring(i,k+1), candidate.getOption().substring(i,k+1));
									} else {
										diff = distance(output.substring(j,k+dist+1), candidate.getOption().substring(j,k+dist+1));
									}
									diff = diff - distance(output.substring(j,k+1), candidate.getOption().substring(i,k+1+dist));
									newRatio = (diff*1.0f)/(cost*1.0f);
									if (diff > 0 && newRatio > best) {
										log.debug("found better one i: " + i + " j: " + j + " k: " + k);
										best = newRatio;
										bestCost = cost;										
										if (i < j) {
											option = candidate.getOption().substring(0, i);
											option = option + output.substring(i,j);
										} else {
											option = candidate.getOption().substring(0, j);
										}
										option = option + candidate.getOption().substring(i,k+1+dist);
										if (i < j) {
											option = option + candidate.getOption().substring(k+1);
										} else {
											option = option + output.substring(k+1,k+dist+1);
											option = option + candidate.getOption().substring(k+dist+1);
										}
										log.debug("found better cost: "+ cost + " new distance: " + (distance-diff) + " real NewDistance: " +distance(option, output));										
										log.debug(option);
										log.debug(output);
										nextCandidates.clear();									
										nextCandidates.add(new Candidate(cost + candidate.getCost(),option));
										improved = true;
									} else if (diff > 0 && newRatio == best && bestCost != -1) {
										if (bestCost == -1) {
											nextCandidates.clear();
										}
										bestCost = cost;
										if (i < j) {
											option = candidate.getOption().substring(0, i);
											option = option + output.substring(i,j);
										} else {
											option = candidate.getOption().substring(0, j);
										}
										option = option + candidate.getOption().substring(i,k+1+dist);
										if (i < j) {
											option = option + candidate.getOption().substring(k+1);
										} else {
											option = option + output.substring(k+1,k+dist+1);
											option = option + candidate.getOption().substring(k+dist+1);
										}
										if (!nextCandidates.contains(option)) {
											log.debug("found equal one i: " + i + " j: " + j + " k: " + k);
											log.debug("found equal cost: "+ cost + " new distance: " + (distance-diff) + " real NewDistance: " +distance(option, output));											
											nextCandidates.add(new Candidate(cost + candidate.getCost(),option));
											improved = true;
										}
									}								
								}
							}
						}
					}
				}
			}
			if (improved) {
				log.debug("Aplying improvements");
				candidates = nextCandidates;
				nextCandidates = new ArrayList<Candidate>();
			}			
		} while(improved);
		//Finding near misses
		option = candidates.get(0).getOption();
		result = result + candidates.get(0).getCost();
		for (int i = 1; i*1.0f < (2.0f*swap)/(add+rem); i++) {
			log.debug("Probando desplazamientos de i: "+i);
			for (int j = 0; j < length1-1; j++) {
				if (option.charAt(j) != output.charAt(j) 
						&& option.charAt(j) == output.charAt(j+i)) {
					result = result + (add+rem);
					option = option.substring(0,j) + output.substring(j,j+i+1) + option.substring(j+i+1);
					j = j +i;
				}
			}
			for (int j = 1; j < length1; j++) {
				if (option.charAt(j) != output.charAt(j) 
						&& option.charAt(j) == output.charAt(j-i)) {
					result = result + (add+rem);
					option = option.substring(0,j-i) + output.substring(j-i,j+1) + option.substring(j+1);
				}
			}
		}		
		
		result = result +  distance(option, output) * minCost;
		return result;
	}
	
	private int distance(String input, String output) {
		int len1 = input.length();
		int len2 = output.length();
		int distance = 0;
		for (int i = 0; i < len1 && i < len2; i++) {
			if (input.charAt(i) != output.charAt(i)) {
				distance++;
			}
		}
		return distance + (Math.abs(len1-len2));
	}
	private ArrayList<Candidate> findBestInsert (String output, ArrayList<Candidate> candidates, ArrayList<Character> chars) {
		ArrayList<Candidate> nextCandidates = new ArrayList<Candidate>();
		while (candidates.get(0).getOption().length() != output.length()) {
			int best = distance(output, candidates.get(0).getOption());
			String option = "";
			int diff = 0;
			int dist = 0;
			for (Candidate candidate : candidates) {
				for (Character car : chars) {					
					for (int i = 0; i <= candidate.getOption().length(); i++) {
						option = candidate.getOption().substring(0,i) + car +candidate.getOption().substring(i);
						dist = distance(output, option);
						diff = dist - best;
						if (diff == 0 && !nextCandidates.contains(option)) {
							nextCandidates.add(new Candidate(0,option));
						} else if (diff < 0) {
							nextCandidates.clear();
							nextCandidates.add(new Candidate(0,option));
							best = dist;
						}
					}
				}
			}
			candidates = nextCandidates;
			nextCandidates = new ArrayList<Candidate>();
		}
		return candidates;
	}
	private ArrayList<Candidate> findBestRemove(String output, ArrayList<Candidate> candidates, ArrayList<Character> chars) {
		ArrayList<Candidate> nextCandidates = new ArrayList<Candidate>();
		while (candidates.get(0).getOption().length() != output.length()) {
			int best = distance(output, candidates.get(0).getOption());
			String option = "";
			int diff = 0;
			int dist = 0;
			for (Candidate candidate : candidates) {
				for (int i = 0; i < candidate.getOption().length(); i++) {
					option = candidate.getOption().substring(0,i) + candidate.getOption().substring(i+1);
					dist = distance(output, option);
					diff = dist - best;
					if (diff == 0 && !nextCandidates.contains(option)) {
						nextCandidates.add(new Candidate(0,option));
					} else if (diff < 0) {
						nextCandidates.clear();
						nextCandidates.add(new Candidate(0,option));
						best = dist;
					}
				}
			}
			candidates = nextCandidates;
			nextCandidates = new ArrayList<Candidate>();
		}
		return candidates;
	}
	
	
}
