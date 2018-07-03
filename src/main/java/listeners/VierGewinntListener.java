package listeners;

import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VierGewinntListener extends ListenerAdapter {
	
	
	public static int readReaction(PrivateMessageReactionAddEvent privateMessageReactionAddEvent) {
		String emoteName = privateMessageReactionAddEvent.getReactionEmote().getName();

		switch (emoteName) {
		case "✅":
			return 1;

		case "❌":
			return 2;
		}

		return 0;
	}
}
