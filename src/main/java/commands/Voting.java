package commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.PollData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * The Class Voting.
 */
public class Voting extends CommandHandler {
	
	/** The formatter. */
	SimpleDateFormat formatter = new SimpleDateFormat("DD.MM.uuuu HH:mm");

	/**
	 * Instantiates a new voting.
	 */
	public Voting() {
		super("vote");
	}

	/* (non-Javadoc)
	 * @see command.CommandHandler#execute(command.CommandManager.ParsedCommandString, net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {

		if (parsedCommand.getArgs().length == 0) {
			return;
		}

		// comand: create <title>; <time>; <option1>; <option2>;
		if (parsedCommand.getArgs()[0].equalsIgnoreCase("create")) {
			PollData pData = new PollData();
			pData.setUserId(event.getAuthor().getId());

			String text = String.join(" ", Arrays.asList(parsedCommand.getArgs()))
					.replace(parsedCommand.getArgs()[0] + " ", "");

			String[] input = text.split(";");
			if (input.length < 4) {
				event.getTextChannel().sendMessage("Invalid input");
				return;
			}

			EmbedBuilder eb = new EmbedBuilder().setColor(Color.green)
					.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl())
					.setDescription(input[0]);

			String options = "";
			// TODO own class for that stuff
			for (int i = 2; i < input.length; i++) {
				switch (i - 1) {
				case 1:
					options += " :one: ";
					break;
				case 2:
					options += ":two: ";
					break;
				case 3:
					options += ":three: ";
					break;
				case 4:
					options += ":four: ";
					break;
				case 5:
					options += ":five: ";
					break;
				case 6:
					options += ":six: ";
					break;
				case 7:
					options += ":seven: ";
					break;
				case 8:
					options += ":eight: ";
					break;
				case 9:
					options += ":nine: ";
					break;
				default:
					break;
				}
				options += input[i] + "\n";
			}

			eb.addField("Options", options, true);

			event.getTextChannel().sendMessage(eb.build()).queue((message) -> {
				pData.setMessageId(message.getId());
				pData.setChannelId(event.getChannel().getId());

				// TODO own class for that stuff
				for (int i = 2; i < input.length; i++) {
					switch (i - 1) {
					case 1:
						message.addReaction("1⃣").queue();
						break;
					case 2:
						message.addReaction("2⃣").queue();
						break;
					case 3:
						message.addReaction("3⃣").queue();
						break;
					case 4:
						message.addReaction("4⃣").queue();
						break;
					case 5:
						message.addReaction("5⃣").queue();
						break;
					case 6:
						message.addReaction("6⃣").queue();
						break;
					case 7:
						message.addReaction("7⃣").queue();
						break;
					case 8:
						message.addReaction("8⃣").queue();
						break;
					case 9:
						message.addReaction("9⃣").queue();
						break;
					default:
						break;
					}
				}

				LocalDateTime time = LocalDateTime.parse(input[1], DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm"));
				pData.setTime(time);
				pData.saveToDb(pData);
				timerStart(time, message.getId(), event.getTextChannel());
			});

		} else if (parsedCommand.getArgs()[0].equalsIgnoreCase("close")) {
			String text = String.join(" ", Arrays.asList(parsedCommand.getArgs()))
					.replace(parsedCommand.getArgs()[0] + " ", "");

			PollData pData = new PollData().getDbData(text);
			if (pData.isOpen()) {
				pData.setOpen(false);
				pData.saveToDb(pData);

				event.getTextChannel().getMessageById(text).queue((message) -> {
					MessageEmbed oldEm = message.getEmbeds().get(0);
					String[] values = oldEm.getFields().get(0).getValue().split("\n");
					StringBuilder strB = new StringBuilder();
					for (int i = 0; i < values.length; i++) {
						strB.append(" " + values[i] + "`" + pData.getOptions()[i] + "`" + "\n");
					}
					message.editMessage(new EmbedBuilder()
							.setAuthor(oldEm.getAuthor().getName(), null, oldEm.getAuthor().getIconUrl())
							.setDescription(oldEm.getDescription())
							.addField(oldEm.getFields().get(0).getName(), strB.toString(), true).build()).queue();
				});

				event.getTextChannel()
						.sendMessage(new EmbedBuilder().setColor(Color.blue).setDescription("Poll closed!").build())
						.queue();
			} else {
				event.getTextChannel()
						.sendMessage(
								new EmbedBuilder().setColor(Color.blue).setDescription("Poll already closed!").build())
						.queue();
			}
		} else if (parsedCommand.getArgs()[0].equalsIgnoreCase("open")) {
			String text = String.join(" ", Arrays.asList(parsedCommand.getArgs()))
					.replace(parsedCommand.getArgs()[0] + " ", "");

			PollData pData = new PollData().getDbData(text);
			if (!pData.isOpen()) {
				pData.setOpen(true);
				pData.saveToDb(pData);
				event.getTextChannel()
						.sendMessage(new EmbedBuilder().setColor(Color.blue).setDescription("Poll opened!").build())
						.queue();
			} else {
				event.getTextChannel()
						.sendMessage(
								new EmbedBuilder().setColor(Color.blue).setDescription("Poll already opened!").build())
						.queue();
			}
		} else {
			return;
		}
		event.getMessage().delete().queue();
	}

	/**
	 * Timer start.
	 *
	 * @param time the time
	 * @param messageId the message id
	 * @param channel the channel
	 */
	public static void timerStart(LocalDateTime time, String messageId, TextChannel channel) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		Runnable task = new Runnable() {
			public void run() {
				PollData timerPollData = new PollData().getDbData(messageId);
				timerPollData.setOpen(false);
				timerPollData.saveToDb(timerPollData);

				channel.getMessageById(messageId).queue((message) -> {
					MessageEmbed oldEm = message.getEmbeds().get(0);
					String[] values = oldEm.getFields().get(0).getValue().split("\n");
					StringBuilder strB = new StringBuilder();
					for (int i = 0; i < values.length; i++) {
						strB.append(values[i] + "`" + timerPollData.getOptions()[i] + "`" + "\n");
					}
					message.editMessage(new EmbedBuilder()
							.setAuthor(oldEm.getAuthor().getName(), null, oldEm.getAuthor().getIconUrl())
							.setDescription(oldEm.getDescription())
							.addField(oldEm.getFields().get(0).getName(), strB.toString(), true).build()).queue();
				});

				channel.sendMessage(new EmbedBuilder().setColor(Color.blue).setDescription("Poll closed!").build())
						.queue();
			}
		};
		scheduler.schedule(task,
				time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
						- LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
				TimeUnit.MILLISECONDS);
	}
}
