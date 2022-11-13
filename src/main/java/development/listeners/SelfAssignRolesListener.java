package development.listeners;

import development.configuration.ConfigurationUtility;
import development.configuration.ReactionRole;
import development.configuration.SelfRoleAssignment;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelfAssignRolesListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        SelfRoleAssignment[] selfRoleAssignments = ConfigurationUtility.configuration.getSelfRoleAssignments();
        for (SelfRoleAssignment selfRoleAssignment : selfRoleAssignments) {
            String[] messageLinkSegments = selfRoleAssignment.getMessageLink().split("/");
            // Last segment of message link is message ID
            String messageId = messageLinkSegments[messageLinkSegments.length - 1];
            // Second last segment of message link is message channel
            String channelId = messageLinkSegments[messageLinkSegments.length - 2];


            TextChannel channel= event.getJDA().getTextChannelById(channelId);
            Message roleAssignmentMessage= new MessageHistory(channel).getMessageById(messageId);
            Guild guild=roleAssignmentMessage.getGuild();

            for (ReactionRole reactionRole : selfRoleAssignment.getReactionRoles()) {
                // List<Role> guild.getRolesByName(reactionRole.getRole(), false);   
            }
        }
    }
}
