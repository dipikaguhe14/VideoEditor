package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "logging")
public class LoggingConfig {
    private String level;
    private List<LoggerConfig> loggers;
    private List<AppenderConfig> appenders;

    // Getters and Setters
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<LoggerConfig> getLoggers() {
        return loggers;
    }

    public void setLoggers(List<LoggerConfig> loggers) {
        this.loggers = loggers;
    }

    public List<AppenderConfig> getAppenders() {
        return appenders;
    }

    public void setAppenders(List<AppenderConfig> appenders) {
        this.appenders = appenders;
    }

    public static class LoggerConfig {
        private String name;
        private String level;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    public static class AppenderConfig {
        private String type;
        private String threshold;
        private String target;
        private String currentLogFilename;
        private String archivedLogFilenamePattern;
        private int archivedFileCount;

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThreshold() {
            return threshold;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getCurrentLogFilename() {
            return currentLogFilename;
        }

        public void setCurrentLogFilename(String currentLogFilename) {
            this.currentLogFilename = currentLogFilename;
        }

        public String getArchivedLogFilenamePattern() {
            return archivedLogFilenamePattern;
        }

        public void setArchivedLogFilenamePattern(String archivedLogFilenamePattern) {
            this.archivedLogFilenamePattern = archivedLogFilenamePattern;
        }

        public int getArchivedFileCount() {
            return archivedFileCount;
        }

        public void setArchivedFileCount(int archivedFileCount) {
            this.archivedFileCount = archivedFileCount;
        }
    }
}
