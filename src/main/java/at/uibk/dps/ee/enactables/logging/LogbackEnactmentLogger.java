package at.uibk.dps.ee.enactables.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LogbackEnactmentLogger} is used to log information about the enactment. Depending
 * on the configuration of logback this data may be logged to STDOUT, a log file or a
 * relational database.
 */
public class LogbackEnactmentLogger implements EnactmentLogger {
  protected final Logger logger = LoggerFactory.getLogger(LogbackEnactmentLogger.class);

  @Override public void logEnactment(EnactmentLogEntry entry) {
    logger
        .info("TYPE {} ID {} EXEC TIME {} ms SUCCESS {}.", entry.getType(), entry.getId(),
            entry.getExecutionTime(), entry.isSuccess());
  }
}
