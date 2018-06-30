package commands;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import core.PermissionCore;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Help extends CommandHandler {
	public Help() {
		super("help");
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
