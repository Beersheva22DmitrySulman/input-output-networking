package telran.io.application;

import java.io.*;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SourceDestination {
	
	private static final Logger LOGGER = Logger.getLogger(SourceDestination.class.getName());

	public static void main(String[] args) {
		Handler handler = new ConsoleHandler();
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.SEVERE);
		
		if (args.length > 1) {
			LOGGER.fine(String.format("args %s", Arrays.deepToString(args)));
			try (Reader reader = args[0].equalsIgnoreCase("console") ? new InputStreamReader(System.in)
					: new FileReader(args[0]);
					PrintWriter writer = args[1].equalsIgnoreCase("console") ? new PrintWriter(System.out)
							: new PrintWriter(args[1])) {
				sourceToDestination(reader, writer);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("too few arguments");
			LOGGER.severe("No arguments");
		}

	}

	private static void sourceToDestination(Reader source, PrintWriter dest) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(source);
		String line = bufferedReader.readLine();
		while (line != null) {
			dest.println(line);
			line = bufferedReader.readLine();
		}
	}
}
