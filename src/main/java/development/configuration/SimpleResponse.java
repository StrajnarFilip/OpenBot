package development.configuration;

import com.google.gson.annotations.SerializedName;

public class SimpleResponse {
    @SerializedName("command")
    String command;
    @SerializedName("response")
    String response;

    public String getCommand() {
        return command;
    }

    public String getResponse() {
        return response;
    }

}
