package listeners;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VotingListener extends ListenerAdapter {

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		Message message = event.getChannel().getMessageById(event.getMessageId()).complete();
		
		message.getReactions().get(0);
		
		event.getUser();
		
		event.getM
	}
}
