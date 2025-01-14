package config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
public class VideoEditorConfig {
    private ApplicationConnector[] applicationConnectors;
    private ApplicationConnector[] adminConnectors;

    // Getters and Setters
    public ApplicationConnector[] getApplicationConnectors() {
        return applicationConnectors;
    }

    public void setApplicationConnectors(ApplicationConnector[] applicationConnectors) {
        this.applicationConnectors = applicationConnectors;
    }

    public ApplicationConnector[] getAdminConnectors() {
        return adminConnectors;
    }

    public void setAdminConnectors(ApplicationConnector[] adminConnectors) {
        this.adminConnectors = adminConnectors;
    }

    public static class ApplicationConnector {
        private String type;
        private int port;

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
