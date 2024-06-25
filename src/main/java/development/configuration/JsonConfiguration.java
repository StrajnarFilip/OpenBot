package development.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonConfiguration {
    @JsonProperty("bot-token")
    String botToken;

    @JsonProperty("simple-responses")
    SimpleResponse[] simpleResponses;

    @JsonProperty("slash-commands")
    SlashCommand[] slashCommands;

    @JsonProperty("self-role-assignment")
    SelfRoleAssignment[] selfRoleAssignments;

    @JsonProperty("contains-replies")
    ContainsReply[] containsReplies;

    @JsonProperty("bot-is-watching")
    String watching;

    public String getWatching() {
        return watching;
    }

    public String getBotToken() {
        return botToken;
    }

    public SimpleResponse[] getSimpleResponses() {
        return simpleResponses;
    }

    public SlashCommand[] getSlashCommands() {
        return slashCommands;
    }

    public SelfRoleAssignment[] getSelfRoleAssignments() {
        return selfRoleAssignments;
    }

    public ContainsReply[] getContainsReplies() {
        return containsReplies;
    }
}