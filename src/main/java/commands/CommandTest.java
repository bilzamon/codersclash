package commands;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * The Class CommandTest.
 */
public class CommandTest extends CommandHandler {

	/**
	 * Instantiates a new command test.
	 */
	public CommandTest() {
		super("test");
	}

	/* (non-Javadoc)
	 * @see command.CommandHandler#execute(command.CommandManager.ParsedCommandString, net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		System.out.println("test");
	}

}
