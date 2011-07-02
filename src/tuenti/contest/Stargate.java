package tuenti.contest;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Stargate {

	private static Logger log = Logger.getLogger(Stargate.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Stargate main = new Stargate();
		main.process();
	}
	
	private void process() {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			int numPlanets = in.nextInt();
			int start = in.nextInt();
			int end = in.nextInt();
			int[][] times = new int[numPlanets][numPlanets];
			for (int i = 0; i < numPlanets; i++) {
				for (int j = 0; j < numPlanets; j++) {
					times[i][j] = Integer.MAX_VALUE;
				}
			}
			String line = in.nextLine();
			String[] chunks = line.split(" ");
			for (String distance : chunks) {
				if (distance.trim().length() == 0)
					continue;
				String[] values = distance.split(",");
				times[Integer.parseInt(values[0])][Integer.parseInt(values[1])] = Integer.parseInt(values[2]);
			}
			System.out.println(magic(start, end, times));
		}		
	}
	
	private String magic (int start, int end, int[][] times) {
		log.debug("Start: "+start + " end: "+ end + " planets: " + times.length);
		int[] dist = new int[times.length];
		ArrayList<Integer> nodesToSearch = new ArrayList<Integer>();
		for (int i = 0; i < times.length; i++) {
			dist[i] = Integer.MAX_VALUE;
			nodesToSearch.add(i);
		}
		dist[start] = 0;
		int node = start;
		do {
			if (dist[node] == Integer.MAX_VALUE) {
				break;
			}
			nodesToSearch.remove(new Integer(node));
			for (int i = 0; i < times.length; i++ ) {
				if (times[node][i] != Integer.MAX_VALUE) {
					int alt = dist[node] + times[node][i];
					if (alt < dist[i] && nodesToSearch.contains(new Integer(i))) {
						dist[i] = alt;
					} else if (alt < dist[i] && !nodesToSearch.contains(new Integer(i))) {
						return "BAZINGA";
					}
				}
			}
			if (nodesToSearch.size() > 0) {
				int min = Integer.MAX_VALUE;
				node = nodesToSearch.get(0);
				for (Integer nodetemp : nodesToSearch) {
					if (dist[nodetemp] < min) {
						node = nodetemp;
						min = dist[node];
					}
				}
			}
		} while (nodesToSearch.size() > 0);
		if (dist[end] == Integer.MAX_VALUE) {
			return "BAZINGA";
		} else {
			return (25000+dist[end])+"";
		}
	}
}
