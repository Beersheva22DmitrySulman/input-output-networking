package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LineOrientedStreams {
	private static final String fileNamePrintStream = "lines.txt";
	private static final String fileNamePrintWriter = "lines-writer.txt";
	private static final String line = "Hello world!";

	@Test
	void printStreamTest() throws Exception {
		try (PrintStream printStream = new PrintStream(fileNamePrintStream)) {
			printStream.println(line);
		}
	}

	@Test
	void printWriterTest() throws Exception {
		try (PrintWriter printWriter = new PrintWriter(fileNamePrintWriter)) {
			printWriter.println(line);
		}
	}

}
