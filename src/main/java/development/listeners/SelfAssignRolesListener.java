package development.listeners;

import java.util.List;

import development.configuration.ConfigurationUtility;
import development.configuration.ReactionRole;
import development.configuration.SelfRoleAssignment;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelfAssignRolesListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        SelfRoleAssignment[] selfRoleAssignments = ConfigurationUtility.configuration.getSelfRoleAssignments();
        for (SelfRoleAssignment selfRoleAssignment : selfRoleAssignments) {
            String[] messageLinkSegments = selfRoleAssignment.getMessageLink().split("/");
            // Last segment of message link is message ID
            String messageId = messageLinkSegments[messageLinkSegments.length - 1];
            // Second last segment of message link is message channel
            String channelId = messageLinkSegments[messageLinkSegments.length - 2];

            TextChannel channel = jda.getTextChannelById(channelId);
            Message roleAssignmentMessage = channel.retrieveMessageById(messageId).complete();

            for (ReactionRole reactionRole : selfRoleAssignment.getReactionRoles()) {
                EmojiUnion emoji = Emoji.fromFormatted(reactionRole.getReaction());
                roleAssignmentMessage.addReaction(emoji).queue();
            }
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        SelfRoleAssignment[] selfRoleAssignments = ConfigurationUtility.configuration.getSelfRoleAssignments();
        for (SelfRoleAssignment selfRoleAssignment : selfRoleAssignments) {
            // Check if reaction is on this message
            if (getMessageId(selfRoleAssignment).equals(event.getMessageId())) {
                for (ReactionRole reactionRole : selfRoleAssignment.getReactionRoles()) {
                    Guild guild = event.getGuild();
                    List<Role> matchingRoles = guild.getRolesByName(reactionRole.getRole(), false);
                    Role matchingRole = matchingRoles.get(0);
                    if (compareReactionRoleAndEventEmoji(reactionRole, event.getEmoji())) {
                        guild.addRoleToMember(event.getUser(), matchingRole).queue();
                    } else {
                        if (selfRoleAssignment.getSelectOne()) {
                            guild.removeRoleFromMember(event.getUser(), matchingRole).queue();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        SelfRoleAssignment[] selfRoleAssignments = ConfigurationUtility.configuration.getSelfRoleAssignments();
        for (SelfRoleAssignment selfRoleAssignment : selfRoleAssignments) {
            // Check if reaction is on this message
            if (getMessageId(selfRoleAssignment).equals(event.getMessageId())) {
                for (ReactionRole reactionRole : selfRoleAssignment.getReactionRoles()) {
                    Guild guild = event.getGuild();
                    if (compareReactionRoleAndEventEmoji(reactionRole, event.getEmoji())) {
                        List<Role> matchingRoles = guild.getRolesByName(reactionRole.getRole(), false);
                        Role matchingRole = matchingRoles.get(0);
                        guild.removeRoleFromMember(event.getUser(), matchingRole).queue();
                    }
                }
            }
        }
    }

    static boolean compareReactionRoleAndEventEmoji(ReactionRole reactionRole, EmojiUnion emojiUnion) {
        return emojiUnion.asUnicode().getAsCodepoints().equalsIgnoreCase(reactionRole.getReaction());
    }

    static String[] messageLinkSegments(SelfRoleAssignment selfRoleAssignment) {
        return selfRoleAssignment.getMessageLink().split("/");
    }

    static String getMessageId(SelfRoleAssignment selfRoleAssignment) {
        String[] segments = messageLinkSegments(selfRoleAssignment);
        return segments[segments.length - 1];
    }

    static String getChannelId(SelfRoleAssignment selfRoleAssignment) {
        String[] segments = messageLinkSegments(selfRoleAssignment);
        return segments[segments.length - 2];
    }

    static String getGuildId(SelfRoleAssignment selfRoleAssignment) {
        String[] segments = messageLinkSegments(selfRoleAssignment);
        return segments[segments.length - 3];
    }
}
