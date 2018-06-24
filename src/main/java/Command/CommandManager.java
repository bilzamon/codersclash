package Command;

import java.util.Arrays;
import java.util.HashMap;

import Core.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {
	private final static HashMap<String, CommandHandler> commandAssociations = new HashMap<>();

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		ParsedCommandString parsedMessage = parse(event.getMessage().getContentRaw());

		if (!event.getAuthor().isBot() && !event.getAuthor().isFake() && parsedMessage != null) {
			CommandHandler commandHandler = commandAssociations.get(parsedMessage.getCommand());

			if (commandHandler != null) {
				commandHandler.execute(parsedMessage, event);
				return;
			}
		} else {
			return;
		}
	}

	public void setupCommandHandlers(CommandHandler commandHandler) {
		if (commandAssociations.containsKey(commandHandler.getInvokeString().toLowerCase())) {
			return;
		} else {
			commandAssociations.put(commandHandler.getInvokeString(), commandHandler);
		}

	}

	public CommandHandler getCommandHandler(String invocationAlias) {
		return commandAssociations.get(invocationAlias.toLowerCase());
	}

	private ParsedCommandString parse(String message) {
		if (message.startsWith(Main.getProperties().getProperty("Prefix"))) {
			String beheaded = message.replaceFirst("\\" + Main.getProperties().getProperty("Prefix"), "");
			String[] args = beheaded.split("\\s+");
			String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
			return new ParsedCommandString(args[0], newArgs);
		}
		return null;
	}

	public static final class ParsedCommandString {
		private final String command;
		private final String[] args;

		public ParsedCommandString(String command, String[] args) {
			this.command = command;
			this.args = args;
		}

		public String getCommand() {
			return command;
		}

		public String[] getArgs() {
			return args;
		}
	}
}
