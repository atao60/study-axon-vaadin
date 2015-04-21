import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

appender("Stdout", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
	pattern = "%d [%t] %-5p %-30.30c{1} %x - %m%n"
  }
}
root(DEBUG, ["Stdout"])
