package commands.xp;

import java.awt.Color;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.UserData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Level;

/**
 * The Class XP.
 */
public class XP extends CommandHandler {

	/**
	 * Instantiates a new xp.
	 */
	public XP() {
		super("xp");
	}

	/* (non-Javadoc)
	 * @see command.CommandHandler#execute(command.CommandManager.ParsedCommandString, net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		String id = "";
		if (parsedCommand.getArgs().length == 0) {
			UserData data = UserData.fromId(event.getAuthor().getId());
			if (data != null) {
				event.getChannel()
						.sendMessage(new EmbedBuilder().setColor(Color.green)
								.setTitle("Level: " + data.getLevel() + " (" + Level.remainingXp(data.getTotalXp())
										+ "/" + Level.xpToLevelUp(data.getLevel()) + ")" + "XP")
								.setFooter(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(),
										event.getAuthor().getAvatarUrl())
								.build())
						.queue();
			}
		} else if (event.getMessage().getMentionedMembers().size() > 0) {
			id = event.getMessage().getMentionedMembers().get(0).getUser().getId();
		} else if (event.getGuild().getMembersByEffectiveName(parsedCommand.getArgs()[0], true).size() > 0) {
			id = event.getGuild().getMembersByEffectiveName(parsedCommand.getArgs()[0], true).get(0).getUser().getId();
		}
		UserData data = UserData.fromId(id);
		if (data != null) {
			event.getChannel()
					.sendMessage(new EmbedBuilder().setColor(Color.green)
							.setTitle("Level: " + data.getLevel() + " (" + Level.remainingXp(data.getTotalXp()) + "/"
									+ Level.xpToLevelUp(data.getLevel()) + ")" + "XP")
							.setFooter(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(),
									event.getAuthor().getAvatarUrl())
							.build())
					.queue();

		}
	}
}
