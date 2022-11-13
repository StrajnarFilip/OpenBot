package development;

import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

import development.configuration.ConfigurationUtility;
import development.configuration.SlashCommand;
import development.listeners.SimpleListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App implements EventListener {
    public static void main(String[] args) throws LoginException {
        System.out.println(ConfigurationUtility.configurationFilePath());
        JDA jda = JDABuilder.createDefault(ConfigurationUtility.configuration.getBotToken())
                .enableIntents(allIntents)
                .addEventListeners(new App())
                .addEventListeners(new SimpleListener())
                .setActivity(Activity.playing("in the woods"))
                .build();

        for (SlashCommand slashCommand : ConfigurationUtility.configuration.getSlashCommands()) {
            jda.upsertCommand(slashCommand.getName(), slashCommand.getDescription());
        }
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent)
            System.out.println("API is ready!");
    }

    static List<GatewayIntent> allIntents = Arrays.asList(new GatewayIntent[] {
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
            GatewayIntent.DIRECT_MESSAGE_TYPING,
            GatewayIntent.GUILD_BANS,
            GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
            GatewayIntent.GUILD_INVITES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_MESSAGE_TYPING,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.GUILD_WEBHOOKS,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.SCHEDULED_EVENTS
    });
}
