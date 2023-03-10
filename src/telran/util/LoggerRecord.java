package telran.util;

import java.time.Instant;

record LoggerRecord(Instant timestamp, String zoneId, Level level, String loggerName, String message) {
}
