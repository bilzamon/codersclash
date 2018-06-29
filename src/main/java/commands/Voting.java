package commands;

import java.awt.Color;
import java.util.Arrays;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Voting extends CommandHandler {

	public Voting() {
		super("vote");
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {

		if (parsedCommand.getArgs().length == 0) {
			return;
		}

		// comand: create <title> ; <option1>; <option2>;
		if (parsedCommand.getArgs()[0].equalsIgnoreCase("create")) {

			String text = String.join(" ", Arrays.asList(parsedCommand.getArgs()))
					.replace(parsedCommand.getArgs()[0] + " ", "");

			String[] input = text.split(";");
			if (input.length < 3) {
				event.getTextChannel().sendMessage("Invalid input");
				return;
			}

			EmbedBuilder eb = new EmbedBuilder().setColor(Color.green)
					.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl())
					.setDescription(input[0]);
			String options = "";
			for (int i = 1; i < input.length; i++) {
				switch (i) {
				case 1:
					options += ":one:";
					break;
				case 2:
					options += ":two:";
					break;
				case 3:
					options += ":three:";
					break;
				case 4:
					options += ":four:";
					break;
				case 5:
					options += ":five:";
					break;
				case 6:
					options += ":six:";
					break;
				case 7:
					options += ":seven:";
					break;
				case 8:
					options += ":eight:";
					break;
				case 9:
					options += ":nine:";
					break;
				default:
					break;
				}
				options += input[i] + "\n";
			}

			eb.addField("Options", options, true);

			event.getTextChannel().sendMessage(eb.build()).queue((message) -> {

				for (int i = 1; i < input.length; i++) {
					switch (i) {
					case 1:
						message.addReaction("1⃣ ").queue();
						break;
					case 2:
						message.addReaction("2⃣ ").queue();
						break;
					case 3:
						message.addReaction("3⃣ ").queue();
						break;
					case 4:
						message.addReaction("4⃣ ").queue();
						break;
					case 5:
						message.addReaction("5⃣ ").queue();
						break;
					case 6:
						message.addReaction("6⃣ ").queue();
						break;
					case 7:
						message.addReaction("7⃣ ").queue();
						break;
					case 8:
						message.addReaction("8⃣ ").queue();
						break;
					case 9:
						message.addReaction("9⃣ ").queue();
						break;
					default:
						break;
					}
				}
			});

			event.getMessage().delete().queue();
		} else {
			return;
		}
	}

}
