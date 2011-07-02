package tuenti.contest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Gas {

	private static Logger log = Logger.getLogger(Gas.class);
	private static HashMap<String,ArrayList<Long>> best = new HashMap<String,ArrayList<Long>>();;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gas main = new Gas();
		main.process();
	}
	
	private void process() {
		Scanner in = new Scanner(System.in);
		int numTests = in.nextInt();
		log.debug("Tests: " + numTests);
		for (int i = 0; i <  numTests; i++) {
			log.debug("Test------ ");
			in.nextLine();
			long deposit = in.nextLong();
			log.debug("Deposit: "+ deposit);
			in.nextLine();
			long distance = in.nextLong();
			in.nextLine();
			log.debug("Distance: "+ distance);
			long stationNumber = in.nextLong();
			in.nextLine();
			log.debug("stationNumber: " + stationNumber);
			long[] stations = new long[(new Long(stationNumber)).intValue()];  
			for (int j = 0; j < stationNumber; j++) {
				long station = in.nextLong();
				log.debug("\t"+station);
				stations[j] = station;
			}
			System.out.println(magic(distance, deposit, stations));
		}
	}
	
	private String magic (long distance, long deposit, long[] stations) {
		log.debug("Start processing");
		ArrayList<Long> solution = findBestRoute(distance, deposit, deposit, stations, 0, new ArrayList<Long>());
		/*
		log.debug("Stations: ");
		for (long station:stations) {
			log.debug("\t"+station);
		}
		log.debug("End Test------ ");
		*/
		if (solution.size() == 0) {
			return "No stops";
		} else {
			boolean first = true;
			String ret = "";
			for (Long station : solution) {
				if (!first) {
					ret += " ";
				}
				first = false;
				ret += station;
			}
			return ret;
		}
	}
	
	private ArrayList<Long> findBestRoute(long distance, long deposit, long currentdeposit, long[] stations, 
			int currentStation, ArrayList<Long> currentSolution) {
		String key = distance + "_" + currentdeposit;
		if (best.containsKey(key)) {
			return best.get(key);
		}
		if (distance <= currentdeposit) {
			return currentSolution;
		}
		long currentDistance = 0;
		if (currentStation == 0) {
			currentDistance = stations[currentStation];
		} else {
			currentDistance = stations[currentStation] - stations[currentStation-1];
		}
		if ( currentdeposit < currentDistance) {
			best.put(key, null);
			return null;
		} else if (currentdeposit == currentDistance) {
			currentSolution.add(stations[currentStation]);
			return findBestRoute(distance-currentDistance, deposit, deposit, stations, 
					currentStation+1, currentSolution);			
		}
		ArrayList<Long> sol1 = findBestRoute(distance-currentDistance, deposit, currentdeposit  - currentDistance, stations, 
				currentStation+1, currentSolution);
		ArrayList<Long>currentSolution2 = (ArrayList<Long>)currentSolution.clone();
		currentSolution2.add(stations[currentStation]);
		ArrayList<Long> sol2 = findBestRoute(distance-currentDistance, deposit, deposit, stations, 
				currentStation+1, currentSolution2);
		if (sol1 != null && sol2 != null) {
			best.put(key, (sol1.size()<=sol2.size())?sol1:sol2);
			return (sol1.size()<=sol2.size())?sol1:sol2; 
		} else if(sol1 == null) {
			best.put(key, sol2);
			return sol2;
		} else {
			best.put(key, sol1);
			return sol1;
		}		
	}
}
