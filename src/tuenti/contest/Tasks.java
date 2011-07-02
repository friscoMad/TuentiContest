package tuenti.contest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Tasks {

	/**
	 * @param args
	 */
	private static HashMap<Integer, Integer> duration = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> maxDuration = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) throws FileNotFoundException {
		preprocessData();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] values = line.split(",");
			for (String value : values) {
				System.out.println(value + " " + maxDuration.get(new Integer(value)));
			}
		}
	}

	private static void preprocessData() throws FileNotFoundException {
		long start = System.currentTimeMillis();
	    File file = new File("../data/tasks/in");
	    FileInputStream fis = new FileInputStream(file);
	    BufferedInputStream bis = new BufferedInputStream(fis);
		Scanner in = new Scanner(bis);
		in.nextLine();
		int taskNumber = in.nextInt();
		String line = "";
		String[] values;
		while (duration.size() < taskNumber ) {
			line = in.nextLine();
			if (line.trim().equals("") || line.startsWith("#")) {
				continue;
			}
			values = line.split(",");
			duration.put(new Integer(values[0]), new Integer(values[1]));
		}
		ArrayList<String> lines = new ArrayList<String>();
		while (in.hasNextLine() ) {
			line = in.nextLine();
			if (line.trim().equals("") || line.startsWith("#")) {
				continue;
			}
			lines.add(line);
		}		
		//This only works for the test file
		Collections.reverse(lines); 
		Integer task;
		HashMap<Integer,Integer> blockingTasks = new HashMap<Integer,Integer>();
		HashMap<Integer,String> blockedLines = new HashMap<Integer,String>();
		for (String lineDep : lines) {
			values = lineDep.split(",");
			task = new Integer(values[0]);
			if (task + 1 < taskNumber) {
				for (int i = task + 1; i < taskNumber; i++ ) {
					if (!blockedLines.containsKey(i)) {
						maxDuration.put(i, duration.get(i));
					}
				}
			}
			checkSetMaxDuration(task, values, lineDep, blockingTasks, blockedLines);			
			taskNumber = task;
		}
		while (blockingTasks.size() > 0) {			
			for (Integer innerTask : blockingTasks.keySet()) {
				if (maxDuration.containsKey(innerTask)) {
					line = blockedLines.get(blockingTasks.get(innerTask));
					values = line.split(",");
					task = new Integer(values[0]);
					checkSetMaxDuration(task, values, line, blockingTasks, blockedLines);
					if (maxDuration.containsKey(blockingTasks.get(innerTask))) { //Succesfully calc'ed duration
						blockedLines.remove(task);
						blockingTasks.remove(innerTask);
						break;
					}
				}
			}
		}
		in.close();		
	}
	
	private static void checkSetMaxDuration(int task, String[] values, String line, HashMap<Integer,Integer> blockingTasks, HashMap<Integer,String> blockedLines) {
		int max = 0;
		int local = 0;
		Integer innerTask;
		for (int i = 1; i < values.length; i++) {
			innerTask = new Integer(values[i]);
			if (!maxDuration.containsKey(innerTask)) {
				blockingTasks.put(innerTask, task);
				blockedLines.put(task, line);
				max = -1;
				break;
			}
			if (max != -1 && (local = maxDuration.get(innerTask)) > max) {					
				max = local;
			}
		}
		if (max != -1) {
			if (maxDuration.containsKey(task)) {
				System.out.println("Error reseteando " + task);
			}
			maxDuration.put(task, max+duration.get(task));
		}
	}

}
