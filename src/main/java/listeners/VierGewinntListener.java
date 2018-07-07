package listeners;

import core.Main;
import db.GameData;
import db.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class VierGewinntListener extends ListenerAdapter {

	private static JDA jda = Main.getJda();

	@Override
	public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent privateMessageReactionAddEvent) {
		if(MySQL.getGameData(privateMessageReactionAddEvent.getMessageId()) != null) {
			if(!privateMessageReactionAddEvent.getUser().isBot()) {
				GameData gameData = MySQL.getGameData(privateMessageReactionAddEvent.getMessageId());
				String emoteName = privateMessageReactionAddEvent.getReactionEmote().getName();

				Main.getJda().getPrivateChannelById(privateMessageReactionAddEvent.getChannel().getId())
						.deleteMessageById(privateMessageReactionAddEvent.getReaction().getMessageId()).queue();

				switch (emoteName) {

					case "✅":

						privateMessageReactionAddEvent.getChannel().sendMessage(new EmbedBuilder().setColor(Color.DARK_GRAY)
								.setDescription("Spiel wird vorbereitet...").build())
								.complete();

						startGame(gameData);
						break;

					case "❌":

						privateMessageReactionAddEvent.getChannel().sendMessage(new EmbedBuilder().setColor(Color.HSBtoRGB(85, 1, 100))
								.setDescription("Spielanfrage abgelehnt!").build())
								.complete();

						closeGame(gameData);
						break;
				}
			}
		}
	}

	private static void startGame(GameData gameData) {}

	private static void closeGame(GameData gameData) {
		jda.getUserById().openPrivateChannel().queue(privateChannel -> {
			privateChannel.sendMessage(jda.getUserById("225327305570910208").getName() + " hat deine Spielanfrage abgelehnt!").queue();
		});

	}
}
