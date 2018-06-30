package commands;

import java.awt.Color;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import core.PermissionCore;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help extends CommandHandler {
	public Help() {
		super("help","help","get help");
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {

		event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.cyan)
				.addField("GUILDADMIN","",false)
				.addField("ADMIN","",false)
				.addField("MODERATION","``clear`` - Deletes a certain number of messages - [Level 1]\n" + "",false)
				.addField("GERNERAL","``ping`` - Gives you the ping of the bot - [Level 0]\n" + "",false)
				.addField("FUN","",false)
				.setFooter(event.getAuthor().getName() + ", your Level is " + PermissionCore.getLevel(event.getMember()),null)
				.build()).queue();

	}
}
