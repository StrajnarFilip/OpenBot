package development.configuration;

import com.google.gson.annotations.SerializedName;

public class SelfRoleAssignment {
    @SerializedName("message-link")
    String messageLink;

    @SerializedName("select-one")
    Boolean selectOne;

    @SerializedName("reaction-roles")
    ReactionRole[] reactionRoles;

    public String getMessageLink() {
        return messageLink;
    }

    public Boolean getSelectOne() {
        return selectOne;
    }

    public ReactionRole[] getReactionRoles() {
        return reactionRoles;
    }
}