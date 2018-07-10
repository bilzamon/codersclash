package listeners;

import java.awt.Color;

import core.Main;
import db.GameData;
import db.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VierGewinntListener extends ListenerAdapter {

	private static JDA jda;

	@Override
	public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent privateMessageReactionAddEvent) {
		jda = Main.getJda();
		if (MySQL.getGameData(privateMessageReactionAddEvent.getMessageId()) != null) {
			if (!privateMessageReactionAddEvent.getUser().isBot()) {
				GameData gameData = MySQL.getGameData(privateMessageReactionAddEvent.getMessageId());
				String emoteName = privateMessageReactionAddEvent.getReactionEmote().getName();

				jda.getPrivateChannelById(privateMessageReactionAddEvent.getChannel().getId())
						.deleteMessageById(privateMessageReactionAddEvent.getReaction().getMessageId()).queue();

				switch (emoteName) {

				case "✅":

					privateMessageReactionAddEvent.getChannel().sendMessage(new EmbedBuilder().setColor(Color.DARK_GRAY)
							.setDescription("Spiel wird vorbereitet...").build()).complete();

					startGame(gameData);
					break;

				case "❌":

					privateMessageReactionAddEvent.getChannel().sendMessage(new EmbedBuilder()
							.setColor(Color.HSBtoRGB(85, 1, 100)).setDescription("Spielanfrage abgelehnt!").build())
							.complete();

					closeGame(gameData);
					break;
				}
			}
		}
	}

	private static void startGame(GameData gameData) {
	}

	private static void closeGame(GameData gameData) {
		jda.getUserById(gameData.getChallengerId()).openPrivateChannel().queue(privateChannel -> {
			System.out.println("test");
			privateChannel
					.sendMessage(
							jda.getUserById(gameData.getOpponentId()).getName() + " hat deine Spielanfrage abgelehnt!")
					.queue();
		});
	}
}
