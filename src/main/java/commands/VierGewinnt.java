package commands;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import core.Main;
import db.GameData;
import db.MySQL;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Settings;

public class VierGewinnt extends CommandHandler {

	private static JDA jda;

	public VierGewinnt() {
		super("vg", "vg", "");
		// TODO add parameter usage
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) throws ArrayIndexOutOfBoundsException, IndexOutOfBoundsException {
		String[] args = parsedCommand.getArgs();
		
		if (args.length < 3) {

			event.getTextChannel().sendMessage(error.setDescription("Bitte benutze den Befehl so: " + Settings.PREFIX
					+ "vg <Breite(7-8)> <Höhe(Um 1 kleiner als die Breite)> <Gegenspieler>").build()).queue();
			return;
		}

		int width = Integer.parseInt(args[0]);
		int heigh = Integer.parseInt(args[1]);

		User challenger = event.getAuthor();
		String opponent = args[2];

		if (event.getGuild().getMembersByName(opponent, true).get(0).getOnlineStatus() != OnlineStatus.ONLINE) {

			event.getTextChannel()
					.sendMessage(error
							.setDescription(
									"Der User " + opponent + " konnte nicht gefunden werden oder ist nicht online!")
							.build())
					.queue();
			return;
		}

		if (width > 6 && width < 9) {
			if (heigh == width - 1) {
				if (opponent.toLowerCase().equals(challenger.getName().toLowerCase())) {
					System.out.println(challenger.getName().toLowerCase() + " vs. " + opponent.toLowerCase());
					GameData gameData = new GameData();
					gameData.setOpponentId(event.getGuild().getMembersByName(opponent, true).get(0).getUser().getId());
					gameData.setChallengerId(challenger.getId());
					gameData.setHeigh(heigh);
					gameData.setWidth(width);

					jda.getUsersByName(opponent, true).get(0).openPrivateChannel().queue(privateChannel -> {
						privateChannel.sendMessage(challenger.getName() + " hat dich zu einer Runde Vier-Gewinnt("
								+ heigh + "x" + width + ") herausgefordert!").queue(msg -> {
									msg.addReaction("✅").queue();
									msg.addReaction("❌").queue();
									gameData.setMessageId(msg.getId());
									MySQL.insertGameData(gameData);
								});
					});
				} else {
					event.getTextChannel()
							.sendMessage(error.setDescription("Du kannst dich nicht selbst herausfordern!").build())
							.queue();
					return;
				}
			} else {
				event.getTextChannel()
						.sendMessage(error.setDescription("Die Höhe muss um 1 kleiner sein als die Breite!").build())
						.queue();
				return;
			}
		} else {
			event.getTextChannel().sendMessage(error.setDescription("Die Breite muss zwischen 7 und 8 liegen!").build())
					.queue();
			return;
		}
	}

	public static void createGame(GameData gameData, int heigh, int width) {
		String fieldheigh = "", fieldwidth = "", nullfield = "";

		for(int i = 0; i < width; i++) {

			nullfield += "○ ";
		}

		fieldwidth = fieldwidth + nullfield + "\n";
		nullfield = "";

		for(int i = 0; i < heigh; i++) {

			fieldheigh += fieldwidth;
		}

		final String field = fieldheigh;

		jda.getUserById(gameData.getChallengerId()).openPrivateChannel().queue(privateChannel -> {
			privateChannel
					.sendMessage(field)
					.queue();
		});

		jda.getUserById(gameData.getOpponentId()).openPrivateChannel().queue(privateChannel -> {
			privateChannel
					.sendMessage(field)
					.queue();
		});
	}
}
