package commands;

import java.awt.Color;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.Mysql;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Report extends CommandHandler {

	public Report() {
		super("report");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		User reported = null;
		if (event.getMessage().getMentionedUsers().size() == 1) {
			reported = event.getMessage().getMentionedUsers().get(0);
		} else {
			event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("test").build())
					.queue();
			return;
		}

		String reason = String.join(" ", parsedCommand.getArgs()).replace(parsedCommand.getArgs()[0] + " ", "");

		int reportCount = Mysql.loadReportCount(reported.getId());
		Mysql.insertReportCount(reported.getId(), reportCount + 1);
		Mysql.insertReport(reported.getId(), reason);

		event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.orange).setTitle("Report")
				.setDescription(
						reported.getAsMention() + " wurde von " + event.getAuthor().getAsMention() + " reported!")
				.addField("Grund:", "```" + reason + "```", false)
				.setFooter(reported.getName() + " wurde zum " + (reportCount+1) + " mal reported!", null).build())
				.queue();
	}
}
