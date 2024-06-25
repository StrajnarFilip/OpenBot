package development.listeners;

import development.configuration.ConfigurationUtility;
import development.configuration.ContainsReply;
import development.configuration.JsonConfiguration;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ContainsReplyListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot())
        {
            return;
        }

        JsonConfiguration configuration = ConfigurationUtility.getJsonConfiguration();

        for (ContainsReply containsReply : configuration.getContainsReplies()) {
            String checkFor = containsReply.getContains().toLowerCase();
            String content = event.getMessage().getContentRaw().toLowerCase();

            boolean containsString = content.contains(checkFor);

            if (containsString) {
                event.getMessage().reply(containsReply.getResponse()).queue();
            }
        }
    }
}
