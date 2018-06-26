package commands;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.UserData;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Xp extends CommandHandler {

	public Xp() {
		super("xp");
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		if (parsedCommand.getArgs().length == 0) {
			event.getTextChannel().sendMessage(UserData.fromId(event.getAuthor().getId()).getLevel() + "").queue();
		} else {
			//TODO other users
		}
	}
}
