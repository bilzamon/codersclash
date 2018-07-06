package listeners;

import db.MySQL;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VierGewinntListener extends ListenerAdapter {

	@Override
	public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent privateMessageReactionAddEvent) {
		if(MySQL.getGameData(privateMessageReactionAddEvent.getMessageId()) != null){
			if(!privateMessageReactionAddEvent.getUser().isBot()) {

				privateMessageReactionAddEvent.getChannel().sendMessage("Gültig").queue();

			} else {

				privateMessageReactionAddEvent.getChannel().sendMessage("Ungültig").queue();
			}
		}
	}
}
