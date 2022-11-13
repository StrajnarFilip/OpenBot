package development.configuration;

import com.google.gson.annotations.SerializedName;

public class SlashCommand {
    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("response")
    String response;

    @SerializedName("ephemeral")
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
