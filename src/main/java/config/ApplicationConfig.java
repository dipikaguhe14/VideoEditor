package config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {
    private String name;
    private String version;
    private String environment;
    private int maxRequestSizeMb;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getMaxRequestSizeMb() {
        return maxRequestSizeMb;
    }

    public void setMaxRequestSizeMb(int maxRequestSizeMb) {
        this.maxRequestSizeMb = maxRequestSizeMb;
    }
}
