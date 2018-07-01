package listeners;

import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VierGewinntListener extends ListenerAdapter {

    public static int readReaction(GuildMessageReactionAddEvent guildMessageReactionAddEvent) {
        String emoteName = guildMessageReactionAddEvent.getReactionEmote().getName();

        switch (emoteName) {
            case "✅": return 1;

            case "❌": return 2;
        }

        return 0;
    }
}
