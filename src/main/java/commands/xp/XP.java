package commands.xp;

import java.awt.Color;
import java.lang.reflect.Member;
import java.util.List;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.MySQL;
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

		if (parsedCommand.getArgs().length == 0)return;
		if (parsedCommand.getArgs()[0].equalsIgnoreCase("rank")){

			List<String> top10Ids = MySQL.getTop10Ranks();

			StringBuilder sb = new StringBuilder();
			int i = 1;
			for (String m : top10Ids) {
				UserData tmpData = UserData.fromId(m);
				sb.append("``#" + i + "`` - " + event.getGuild().getMemberById(m).getAsMention() + " - Level "
						+ tmpData.getLevel() + "(" + tmpData.getTotalXp() + "XP)\n");
				i++;
			}
			event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.cyan).setDescription(sb.toString()).build()).queue();
		}
	}
}
