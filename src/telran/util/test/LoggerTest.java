package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Handler;
import telran.util.Level;
import telran.util.Logger;
import telran.util.SimpleStreamHandler;

class LoggerTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLogger() throws IOException {
		Handler handler = new SimpleStreamHandler(System.err);
		Logger logger = new Logger(handler, "testLogger");
		logger.setLevel(Level.DEBUG);
		logger.trace("trace");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");

		String dateTimeFormatted;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (PrintStream printStream = new PrintStream(byteArrayOutputStream)) {
			handler = new SimpleStreamHandler(printStream);
			logger = new Logger(handler, "testLogger2");
			dateTimeFormatted = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
					.format(DateTimeFormatter.RFC_1123_DATE_TIME);
			logger.trace("trace");
			logger.debug("debug");
			logger.info("info");
			logger.warn("warn");
			logger.error("error");
		}
		String result = byteArrayOutputStream.toString();
		String expected = dateTimeFormatted + " testLogger2\n" + "INFO: info\n" 
				+ dateTimeFormatted + " testLogger2\n" + "WARN: warn\n"
				+ dateTimeFormatted + " testLogger2\n" + "ERROR: error\n";
		assertEquals(expected, result);

	}
}
