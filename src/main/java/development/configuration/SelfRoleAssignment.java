package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelfRoleAssignment {
    @JsonProperty("message-link")
    String messageLink;

    @JsonProperty("select-one")
    boolean selectOne;

    @JsonProperty("reaction-roles")
    ReactionRole[] reactionRoles;

    public String getMessageLink() {
        return messageLink;
    }

    public boolean getSelectOne() {
        return selectOne;
    }

    public ReactionRole[] getReactionRoles() {
        return reactionRoles;
    }
}