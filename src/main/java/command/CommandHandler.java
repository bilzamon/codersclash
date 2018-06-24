package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CommandHandler {
	private String invokeString;
	
	public CommandHandler(String invokeString) {
		this.invokeString = invokeString;
	}
	
	public abstract void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event);

	public String getInvokeString() {
		return invokeString;
	}
}
