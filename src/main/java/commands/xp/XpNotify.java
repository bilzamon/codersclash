/**
 * 
 */
package commands.xp;

import command.CommandHandler;
import command.CommandManager.ParsedCommandString;
import db.UserData;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class XpNotify extends CommandHandler {

	public XpNotify() {
		super("xpnotify");
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see command.CommandHandler#execute(command.CommandManager.ParsedCommandString, net.dv8tion.jda.core.events.message.MessageReceivedEvent)
	 */
	@Override
	public void execute(ParsedCommandString parsedCommand, MessageReceivedEvent event) {
		UserData data = UserData.fromId(event.getAuthor().getId());
		
		if(data.getLvlupNotify()) {
			data.setLvlupNotify(false);
			event.getTextChannel().sendMessage("LevelUp message successful disabled").queue();
		}else {
			data.setLvlupNotify(true);
			event.getTextChannel().sendMessage("LevelUp message successful enabled").queue();
		}
		data.save(data);
		
	}
}
