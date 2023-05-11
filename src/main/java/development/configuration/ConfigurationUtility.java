package development.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationUtility {
    static ObjectMapper objectMapper = new ObjectMapper();

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
        } catch (FileNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static JsonConfiguration configuration = getJsonConfiguration();
}