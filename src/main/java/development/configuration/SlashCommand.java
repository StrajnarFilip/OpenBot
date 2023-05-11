package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlashCommand {
    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty("response")
    String response;

    @JsonProperty("ephemeral")
    Boolean ephemeral;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getResponse() {
        return response;
    }

    public Boolean getEphemeral() {
        return ephemeral;
    }
}
