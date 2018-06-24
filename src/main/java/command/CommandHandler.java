package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * The Class CommandHandler.
 */
public abstract class CommandHandler {
	
	/** The invoke string. */
	private String invokeString;
	
	/**
	 * Instantiates a new command handler.
	 *
	 * @param invokeString the invoke string
	 */
	public CommandHandler(String invokeString) {
		this.invokeString = invokeString;
	}
	
	/**
	 * Execute.
	 *
	 * @param parsedCommand the parsed command
	 * @param event the event
	 */
	public abstract void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event);

	/**
	 * Gets the invoke string.
	 *
	 * @return the invoke string
	 */
	public String getInvokeString() {
		return invokeString;
	}
}
