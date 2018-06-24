package Commands;

import Command.CommandHandler;
import Command.CommandManager.ParsedCommandString;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandTest extends CommandHandler {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		System.out.println("test");
	}

}
