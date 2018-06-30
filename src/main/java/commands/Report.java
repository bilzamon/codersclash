package commands;

import java.awt.Color;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Report extends CommandHandler {

	public Report() {
		super("report","report <user> <reason>","report a user");
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		User reported = null;
		if (event.getMessage().getMentionedUsers().size() == 1) {
			reported = event.getMessage().getMentionedUsers().get(0);
		} else {
			event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("No user found").build())
					.queue();
			return;
		}

		String reason = String.join(" ", parsedCommand.getArgs()).replace(parsedCommand.getArgs()[0] + " ", "");

		int reportCount = MySQL.loadReportCount(reported.getId());
		MySQL.insertReportCount(reported.getId(), reportCount + 1);
		MySQL.insertReport(reported.getId(), reason);

		event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.orange).setTitle("Report")
				.setDescription(
						reported.getAsMention() + " wurde von " + event.getAuthor().getAsMention() + " reported!")
				.addField("Grund:", "```" + reason + "```", false)
				.setFooter(reported.getName() + " wurde zum " + (reportCount+1) + " mal reported!", null).build())
				.queue();
	}
}
