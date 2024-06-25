package development.listeners;

import development.configuration.ConfigurationUtility;
import development.configuration.ReactionRole;
import development.configuration.SelfRoleAssignment;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class SelfAssignRolesListener extends ListenerAdapter {
    static void onReactionChange(GenericMessageReactionEvent event, ReactionChangeActions actions) {
        SelfRoleAssignment[] selfRoleAssignments = ConfigurationUtility.configuration.getSelfRoleAssignments();
        for (SelfRoleAssignment selfRoleAssignment : selfRoleAssignments) {
            // Check if reaction is on this message
            if (getMessageId(selfRoleAssignment).equals(event.getMessageId())) {
                for (ReactionRole reactionRole : selfRoleAssignment.getReactionRoles()) {
                    Guild guild = event.getGuild();
                    List<Role> matchingRoles = guild.getRolesByName(reactionRole.getRole(), false);
                    Role matchingRole = matchingRoles.get(0);
                    if (compareReactionRoleAndEventEmoji(reactionRole, event.getEmoji())) {
                        actions.onRoleMatch(guild, event.getUser(), matchingRole);
                    } else if (selfRoleAssignment.getSelectOne()) {
                        actions.onRoleNotMatching(guild, event.getUser(), matchingRole);
                    }
                }
            }
        }
    }

    static boolean compareReactionRoleAndEventEmoji(ReactionRole reactionRole, EmojiUnion emojiUnion) {
        return emojiUnion.asUnicode().getAsCodepoints().equalsIgnoreCase(reactionRole.getReaction())
                || emojiUnion.asUnicode().getAsReactionCode().equalsIgnoreCase(reactionRole.getReaction());
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
        onReactionChange(event, new ReactionChangeActions() {
            @Override
            public void onRoleMatch(Guild guild, User user, Role role) {
                guild.addRoleToMember(user, role).queue();

            }

            @Override
            public void onRoleNotMatching(Guild guild, User user, Role role) {
                guild.removeRoleFromMember(user, role).queue();
            }
        });
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        onReactionChange(event, new ReactionChangeActions() {
            @Override
            public void onRoleMatch(Guild guild, User user, Role role) {
                guild.removeRoleFromMember(user, role).queue();
            }

            @Override
            public void onRoleNotMatching(Guild guild, User user, Role role) {
                // Do nothing
            }
        });
    }
}
