package commands;

import java.awt.Color;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Say extends CommandHandler {

	public Say() {
		super("say");
	}

	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.green)
				.setDescription("Ping: " + event.getJDA().getPing() + "").build()).queue();
	}

}
