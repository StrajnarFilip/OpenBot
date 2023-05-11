package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelfRoleAssignment {
    @JsonProperty("message-link")
    String messageLink;

    @JsonProperty("select-one")
    Boolean selectOne;

    @JsonProperty("reaction-roles")
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