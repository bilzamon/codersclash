package listeners;

import db.MySQL;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VierGewinntListener extends ListenerAdapter {

	public static int readReaction(PrivateMessageReactionAddEvent privateMessageReactionAddEvent) {
		if(MySQL.getGameData(privateMessageReactionAddEvent.getMessageId()) != null){

			privateMessageReactionAddEvent.getChannel().sendMessage("Gültig!").queue();
			return 1;
		}
		privateMessageReactionAddEvent.getChannel().sendMessage("Ungültig!").queue();
		return 0;
	}
}
