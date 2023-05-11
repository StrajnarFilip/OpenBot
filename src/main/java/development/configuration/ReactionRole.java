package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReactionRole {
    @JsonProperty("reaction")
    String reaction;

    @JsonProperty("role")
    String role;

    public String getReaction() {
        return reaction;
    }

    public String getRole() {
        return role;
    }
}