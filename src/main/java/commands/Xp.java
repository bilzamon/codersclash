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
			UserData data = UserData.fromId(event.getAuthor().getId());
			if (data != null) {
				event.getTextChannel().sendMessage("Level: " + data.getLevel()).queue();
			} else {
				event.getTextChannel().sendMessage("no xp").queue();
			}
		} else {
			// TODO other users
		}
	}
}
