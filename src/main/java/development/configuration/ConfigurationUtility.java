package development.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

public class ConfigurationUtility {
    static Gson gson = new Gson();

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
            FileReader configurationFile = new FileReader(configurationFilePath());
            return gson.fromJson(configurationFile, JsonConfiguration.class);
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    public static JsonConfiguration configuration = getJsonConfiguration();
}