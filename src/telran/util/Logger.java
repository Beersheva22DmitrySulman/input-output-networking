package telran.util;

import java.time.Instant;
import java.time.ZoneId;

public class Logger {
	private Level level;
	private final Handler handler;
	private final String name;

	public Logger(Handler handler, String name) {
		this.handler = handler;
		this.name = name;
		this.level = Level.INFO;
	}

	public void error(String message) {
		LoggerRecord loggerRecord = createLoggerRecord(message, Level.ERROR);
		publishRecord(loggerRecord);
	}

	public void warn(String message) {
		LoggerRecord loggerRecord = createLoggerRecord(message, Level.WARN);
		publishRecord(loggerRecord);
	}

	public void info(String message) {
		LoggerRecord loggerRecord = createLoggerRecord(message, Level.INFO);
		publishRecord(loggerRecord);
	}

	public void debug(String message) {
		LoggerRecord loggerRecord = createLoggerRecord(message, Level.DEBUG);
		publishRecord(loggerRecord);
	}

	public void trace(String message) {
		LoggerRecord loggerRecord = createLoggerRecord(message, Level.TRACE);
		publishRecord(loggerRecord);
	}

	private LoggerRecord createLoggerRecord(String message, Level level) {
		return new LoggerRecord(Instant.now(), ZoneId.systemDefault().getId() , level, name, message);
	}

	private void publishRecord(LoggerRecord loggerRecord) {
		if (loggerRecord.level().compareTo(level) >= 0) {
			handler.publish(loggerRecord);
		}
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Handler getHandler() {
		return handler;
	}

	public String getName() {
		return name;
	}
}
