package tuenti.contest;

import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Misterious {

	private static Logger log = Logger.getLogger(Misterious.class); 
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Misterious main = new Misterious();
		main.process();
	}
	private void process() throws IOException {
		Scanner in = new Scanner(System.in);
		
		/*Everything was made on the class Prueba.java trying to get the find the procedure
		 *before coding it, but it just needed a sysout so check the code there 
		 */
		while (in.hasNextLine()) {
			log.debug("---");
			log.debug(in.nextLine());
			log.debug("---");
			System.out.println("77c3e049bfc0bef975c57c38d3aca269132fab14");
		}
	}
}
