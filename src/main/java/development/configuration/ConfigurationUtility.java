package development.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ConfigurationUtility {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static final JsonConfiguration configuration = getJsonConfiguration();

    private ConfigurationUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static String configurationFilePath() {
        // Save environmental variable "ConfigurationFilePath"
        String envConfigPath = System.getenv("OpenBotConfigurationPath");
        // If environmental variable is not set, assume configuration
        // file path is "config.json". If it's set, return environmental
        // variable instead.
        return envConfigPath == null ? "config.json" : envConfigPath;
    }

    public static JsonConfiguration getJsonConfiguration() {
        try {
            return objectMapper.readValue(new File(configurationFilePath()), JsonConfiguration.class);
        } catch (Exception ex) {
            return null;
        }
    }
}