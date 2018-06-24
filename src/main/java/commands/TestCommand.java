package commands;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * The Class CommandTest.
 */
public class TestCommand extends CommandHandler {

	/**
	 * Instantiates a new command test.
	 */
	public TestCommand() {
		super("test");
	}

	/* (non-Javadoc)
	 * @see command.CommandHandler#execute(command.CommandManager.ParsedCommandString, net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("Test command").queue();
	}

}
