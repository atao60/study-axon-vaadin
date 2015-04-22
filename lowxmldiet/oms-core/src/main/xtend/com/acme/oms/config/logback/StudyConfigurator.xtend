package com.acme.oms.config.logback

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.Configurator
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.Context
import ch.qos.logback.core.status.InfoStatus
import ch.qos.logback.core.status.Status
import java.util.Properties
import org.eclipse.xtend.lib.annotations.Accessors
import org.springframework.context.annotation.Configuration

import static java.lang.String.format

@Configuration
class StudyConfigurator implements Configurator{
    
    public static val PATTERN_LABEL = "pattern"
    public static val ROOT_LEVEL_LABEL = "root.level"
    
    static val SETTING_UP_CONFIGURATION_MSG = "Setting up OMS Core configuration."
    static val APPENDER_NAME = "console"
    static val DEFAULT_PATTERN = "Logback Config with Xtend: %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    
    val Properties properties
        
    @Accessors
    var Context context

    new() {
        properties = new Properties
        properties.load(StudyConfigurator.classLoader.getResourceAsStream("META-INF/logback.properties"))
    }

    def private doConfigure(LoggerContext lc) {
        val ca = new ConsoleAppender<ILoggingEvent> => [
            it.context = lc
            name = APPENDER_NAME
            encoder = new PatternLayoutEncoder => [
                it.context = lc
                pattern = (properties.get(PATTERN_LABEL) as String)?:DEFAULT_PATTERN
                start
            ]
            start
        ]
        lc.getLogger(Logger.ROOT_LOGGER_NAME) => [
            level = Level.toLevel(properties.get(ROOT_LEVEL_LABEL) as String)
            addAppender(ca)
        ]
    }

    override configure(LoggerContext lc) {
        
        val sm = lc.getStatusManager
        if (sm != null) {
            sm.add(new InfoStatus(SETTING_UP_CONFIGURATION_MSG, lc))
        }
        
        doConfigure(lc)
    }
    
    override addError(String arg0) {
        throw new NotImplementedException("addError(String arg0)")
    }

    override addError(String arg0, Throwable arg1) {
        throw new NotImplementedException("addError(String arg0, Throwable arg1)")
    }

    override addInfo(String arg0) {
        throw new NotImplementedException("addInfo(String arg0)")
    }

    override addInfo(String arg0, Throwable arg1) {
        throw new NotImplementedException("addInfo(String arg0, Throwable arg1)")
    }

    override addStatus(Status arg0) {
        throw new NotImplementedException("addStatus(String arg0)")
    }

    override addWarn(String arg0) {
        throw new NotImplementedException("addWarn(String arg0)")
    }

    override addWarn(String arg0, Throwable arg1) {
        throw new NotImplementedException("addWarn(String arg0, Throwable arg1)")
    }
    
    private final static class NotImplementedException extends UnsupportedOperationException {
        static val NOT_IMPLEMENTED = "%s is not implemented yet."
        new(String methodSig) {
            super(format(NOT_IMPLEMENTED, methodSig))
        }
    }

}