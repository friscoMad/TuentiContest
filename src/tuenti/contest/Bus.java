package tuenti.contest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bus {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws IOException {
		Bus main = new Bus();
		main.process();
	}
	public void process() throws IOException {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			size = in.nextInt();
			capacity = new double[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					capacity[i][j] = 0;
				}
			}
			String test = in.nextLine();
			String[] values = test.split(" ");
			source = 0;
			sink = 1;
			Map<String, Integer> cities = new HashMap<String, Integer>();
			cities.put(values[1], 0);
			cities.put(values[2], 1);
			for(int i = 3; i < values.length; i++) {
				if (values[i].trim().length() == 0) {
					continue;
				}
				//System.out.println(values[i]);
				String[] edge = values[i].split(",");
				if (edge.length < 3) {
					continue;
				}
				int start, end;
				if (!cities.containsKey(edge[0])) {
					cities.put(edge[0], cities.size());					
				}
				start = cities.get(edge[0]);
				if (!cities.containsKey(edge[1])) {
					cities.put(edge[1], cities.size());					
				}
				end = cities.get(edge[1]);
				//System.out.println("Start: " + start + " End: "+ end);
				capacity[start][end] = Integer.parseInt(edge[2]);
			}
			System.out.println((int)maxFlow());
		}
		in.close();
	}

	private double[][] capacity;			
	private int size, source, sink;
			
	private static final int WHITE = 0, GRAY = 1;
	private int[] color, queue;
	private double[][] flow, res_capacity;
	private int[] parent;
	private double[] min_capacity;
	
	private double maxFlow()  // Edmonds-Karp algorithm with O(V³E) complexity
	{
		
		double max_flow = 0;
		
		flow = new double[size][size];
		res_capacity = new double[size][size];
		parent = new int[size];
		min_capacity = new double[size];
		color = new int[size];
		queue = new int[size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				res_capacity[i][j] = capacity[i][j];

		while (BFS(source))
		{
			max_flow += min_capacity[sink];
			int v = sink, u;
			while (v != source)
			{
				u = parent[v];
				flow[u][v] += min_capacity[sink];
				flow[v][u] -= min_capacity[sink];
				res_capacity[u][v] -= min_capacity[sink];
				res_capacity[v][u] += min_capacity[sink];
				v = u;
			}
		}
		return max_flow;
	}

	private boolean BFS(int source)  // Breadth First Search in O(V<sup>2</sup>)
	{
		int first, last;
		
		for (int i = 0; i < size; i++)
		{
			color[i] = WHITE;
			min_capacity[i] = Double.MAX_VALUE;
		}

		first = last = 0;
		queue[last++] = source;
		color[source] = GRAY;

		while (first != last)  // While "queue" not empty..
		{
			int v = queue[first++];
			for (int u = 0; u < size; u++)
				if (color[u] == WHITE && res_capacity[v][u] > 0)
				{
					min_capacity[u] = Math.min(min_capacity[v], res_capacity[v][u]);
					parent[u] = v;
					color[u] = GRAY;
					if (u == sink) return true;
					queue[last++] = u;
				}
		}
		return false;
	}
}
