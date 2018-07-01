package commands;

import command.CommandHandler;
import command.CommandManager;
import core.Main;
import listeners.VierGewinntListener;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Settings;

public class VierGewinnt extends CommandHandler {

    private JDA jda = Main.getJda();

    public VierGewinnt() {super("vg"); }

    @Override
    public void execute(CommandManager.ParsedCommandString parsedCommand, MessageReceivedEvent event) {
        String[] args = parsedCommand.getArgs();
        int heigh = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);

        User challenger = event.getAuthor();
        String opponent = args[2];


        if(args.length < 3) {

            event.getTextChannel().sendMessage(error.setDescription("Bitte benutze den Befehl so: " + Settings.PREFIX + "vg <Höhe(7-8)> <Breite(Um 1 kleiner als Höhe)> <Gegenspieler>").build()).queue();
        }

        if(event.getGuild().getMembersByName(opponent, true).get(0).getOnlineStatus() != OnlineStatus.ONLINE) {

            event.getTextChannel().sendMessage(error.setDescription("Der User " + opponent + " konnte nicht gefunden werden oder ist nicht online!").build()).queue();
        }

        if(heigh > 6 && heigh < 9) {
            if(width == heigh - 1) {
                if(opponent != challenger.getName()) {
                    try {

                        jda.getUsersByName(opponent, true).get(0).openPrivateChannel().queue(privateChannel -> {
                            privateChannel.sendMessage(challenger.getName() + " hat dich zu einer Runde Vier-Gewinnt(" + heigh + "x" + width + ") herausgefordert!").queue(message -> {
                                message.addReaction("\u2705").queue();
                                message.addReaction("\u274C").queue();

                                //Hier soll dann die auslesen sein
                            });
                        });

                    } catch(Exception exeception) {

                        exeception.printStackTrace();
                    }
                } else {

                    event.getTextChannel().sendMessage(error.setDescription("Du kannst dich nicht selbst herausfordern!").build()).queue();
                }

            } else {

                event.getTextChannel().sendMessage(error.setDescription("Die Breite muss um 1 kleiner sein als die Höhe!").build()).queue();
            }

        } else {

            event.getTextChannel().sendMessage(error.setDescription("Die Höhe muss zwischen 7 und 8 liegen!").build()).queue();
        }
    }
}
