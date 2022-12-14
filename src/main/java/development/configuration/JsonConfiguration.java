package development.configuration;

import com.google.gson.annotations.SerializedName;

public class JsonConfiguration {
    @SerializedName("bot-token")
    String botToken;

    @SerializedName("simple-responses")
    SimpleResponse[] simpleResponses;

    @SerializedName("slash-commands")
    SlashCommand[] slashCommands;

    @SerializedName("self-role-assignment")
    SelfRoleAssignment[] selfRoleAssignments;

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
}