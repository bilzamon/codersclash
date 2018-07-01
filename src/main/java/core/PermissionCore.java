package core;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class PermissionCore {

    public static int getLevel(Member member) {

        if (member.equals(member.getGuild().getMemberById("343121212811247639")))
            return 4;

        if (member.equals(member.getGuild().getMemberById("225327305570910208")))
            return 4;

        if (member.equals(member.getGuild().getMemberById("235395943619493888")))
            return 4;

        if (member.equals(member.getGuild().getMemberById("317001855752339458")))
            return 4;

        if (member.isOwner())
            return 3;

        if (member.hasPermission(Permission.ADMINISTRATOR))
            return 2;

        if (member.hasPermission(Permission.MESSAGE_MANAGE))
                return 1;

        return 0;
    }

    public static boolean check(int level, MessageReceivedEvent event) {
        if (level > getLevel(event.getMember())) {
            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription(
                    "I'm sorry, but you do not have permission to use this command."
            ).build()).queue();
            return true;
        }
        return false;
    }

}
