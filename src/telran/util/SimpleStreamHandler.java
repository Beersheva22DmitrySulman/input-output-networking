package telran.util;

import java.io.PrintStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleStreamHandler implements Handler {
	private final PrintStream stream;

	public SimpleStreamHandler(PrintStream stream) {
		this.stream = stream;
	}

	@Override
	public void publish(LoggerRecord loggerRecord) {
		String dateTimeFormatted = ZonedDateTime.ofInstant(loggerRecord.timestamp(), ZoneId.of(loggerRecord.zoneId()))
				.format(DateTimeFormatter.RFC_1123_DATE_TIME);
		stream.printf("%s %s\n", dateTimeFormatted, loggerRecord.loggerName());
		stream.printf("%s: %s\n", loggerRecord.level(), loggerRecord.message());
	}
}
