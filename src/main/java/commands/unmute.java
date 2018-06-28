package commands;

import command.CommandHandler;
import command.CommandManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class unmute extends CommandHandler {
    public unmute(){
        super("unmute");
    }
    @Override
    public void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event) {
        EmbedBuilder error = new EmbedBuilder().setColor(Color.RED).setTitle("Error");
        Guild guild = event.getGuild();

        if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            error.setDescription("Keine berechtigung");
            event.getTextChannel().sendMessage(error.build()).queue();
            return;
        }

        if (event.getMessage().getMentionedMembers().size() != 1) {
            error.setDescription("Du musst einen User mentionen");
            event.getTextChannel().sendMessage(error.build()).queue();
            return;
        }

        guild.getController().removeRolesFromMember(event.getMessage().getMentionedMembers().get(0), guild.getRolesByName("blue-muted", false).get(0)).complete();

        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.green).setTitle("Erfolgreich").setDescription("User "+ event.getMessage().getMentionedMembers().get(0).getAsMention()+ " erfolgreich entmutet").build()).queue();

    }
}
