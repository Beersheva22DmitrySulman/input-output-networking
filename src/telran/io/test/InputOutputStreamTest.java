package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InputOutputStreamTest {
	private static final String fileName = "test.txt";
	private static final String fileNameCopy = "test-copy.txt";
	private static final String hello = "Hello world";

	@BeforeAll
	static void setUp() throws Exception {
		Files.deleteIfExists(Path.of(fileName));
		Files.deleteIfExists(Path.of(fileNameCopy));
	}

	@Test
	@Order(1)
	void outputStreamTest() throws Exception {
		try (OutputStream outputStream = new FileOutputStream(fileName)) {
			byte[] helloBytes = hello.getBytes();
			outputStream.write(helloBytes);
		}
	}

	@Test
	@Order(2)
	void inputStreamTest() throws Exception {
		readFileTest(fileName);
	}

	private void readFileTest(String file) throws IOException, FileNotFoundException {
		try (InputStream inputStream = new FileInputStream(file)) {
			byte[] buffer = inputStream.readAllBytes();
			String str = new String(buffer);
			assertEquals(hello, str);
		}
	}

	@Test
	@Order(3)
	void transferToTest() throws Exception {
		try (InputStream input = new FileInputStream(fileName);
				OutputStream output = new FileOutputStream(fileNameCopy)) {
			input.transferTo(output);
		}
	}
	
	@Test
	@Order(4)
	void copyTest() throws Exception {
		readFileTest(fileNameCopy);
	}

	//3: transferTo, Files, 
	//arg1 file source, arg2 file2, canOverride (default false), buffer
	//count of bytes and time duration, bufferSize (default 1mb)
}
