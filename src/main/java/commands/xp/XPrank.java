package commands.xp;

import java.awt.Color;
import java.util.List;

import command.CommandHandler;
import command.CommandManager;
import db.MySQL;
import db.UserData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class XPrank extends CommandHandler {
    public XPrank() {
        super("xprank","xprank","get the top 10 ranks");
    }

    @Override
    public void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event) {

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
