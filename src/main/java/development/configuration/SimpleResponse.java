package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleResponse {
    @JsonProperty("command")
    String command;

    @JsonProperty("response")
    String response;

    public String getCommand() {
        return command;
    }

    public String getResponse() {
        return response;
    }

}
