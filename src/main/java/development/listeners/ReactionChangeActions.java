package development.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public interface ReactionChangeActions {
    void onRoleMatch(Guild guild, User user, Role role);

    void onRoleNotMatching(Guild guild, User user, Role role);
}
