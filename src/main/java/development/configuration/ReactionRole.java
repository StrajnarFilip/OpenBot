package development.configuration;

import com.google.gson.annotations.SerializedName;

public class ReactionRole {
    @SerializedName("reaction")
    String reaction;

    @SerializedName("role")
    String role;

    public String getReaction() {
        return reaction;
    }

    public String getRole() {
        return role;
    }
}