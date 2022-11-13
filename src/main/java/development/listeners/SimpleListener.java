package development.listeners;

import development.configuration.ConfigurationUtility;
import development.configuration.SimpleResponse;
import development.configuration.SlashCommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SimpleListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Set up all simple responses. If message reveived is equal to
        // command name, respond with a simple response.
        SimpleResponse[] simpleResponses = ConfigurationUtility.getJsonConfiguration().getSimpleResponses();
        for (SimpleResponse simpleResponse : simpleResponses) {
            boolean contentMatches = event.getMessage().getContentRaw().contentEquals(simpleResponse.getCommand());
            boolean authorNotBot = !event.getAuthor().isBot();
            if (contentMatches && authorNotBot) {
                MessageChannel channel = event.getChannel();
                channel.sendMessage(simpleResponse.getResponse()).queue();
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommand[] slashCommands = ConfigurationUtility.configuration.getSlashCommands();
        for (SlashCommand slashCommand : slashCommands) {
            if (event.getName().equals(slashCommand.getName())) {
                event.reply(slashCommand.getResponse())
                        .setEphemeral(slashCommand.getEphemeral()).queue();
            }
        }
    }
}
