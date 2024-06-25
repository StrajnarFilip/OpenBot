package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContainsReply {
    @JsonProperty("contains")
    String contains;

    @JsonProperty("response")
    String response;

    public String getContains() {
        return contains;
    }

    public String getResponse() {
        return response;
    }
}
