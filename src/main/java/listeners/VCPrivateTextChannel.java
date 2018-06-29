package listeners;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VCPrivateTextChannel extends ListenerAdapter {
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        if (event.getChannelJoined().getMembers().size()==1){
            event.getGuild().getController().createTextChannel(event.getChannelJoined().getName().toLowerCase()+"-vc-chat").complete().createPermissionOverride(event.getGuild().getSelfMember()).setAllow(Permission.ALL_PERMISSIONS).complete();
            String ChannelID = event.getGuild().getTextChannelsByName(event.getChannelJoined().getName().toLowerCase()+"-vc-chat",false).get(0).getId();
            event.getGuild().getTextChannelById(ChannelID).createPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.ALL_TEXT_PERMISSIONS).complete();
            event.getGuild().getTextChannelById(ChannelID).createPermissionOverride(event.getMember()).setAllow(Permission.ALL_TEXT_PERMISSIONS).complete();
            return;
        }
        event.getGuild().getTextChannelsByName(event.getChannelJoined().getName().toLowerCase()+"-vc-chat",false).get(0).createPermissionOverride(event.getMember()).setAllow(Permission.ALL_TEXT_PERMISSIONS).complete();

    }
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (event.getChannelLeft().getMembers().size()==0){
            event.getGuild().getTextChannelsByName(event.getChannelLeft().getName().toLowerCase()+"-vc-chat",false).get(0).delete().queue();
            return;
        }
        event.getGuild().getTextChannelsByName(event.getChannelLeft().getName().toLowerCase()+"-vc-chat",false).get(0).getPermissionOverride(event.getMember()).delete().complete();
    }
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        if (event.getChannelLeft().getMembers().size() == 0)
            event.getGuild().getTextChannelsByName(event.getChannelLeft().getName().toLowerCase()+"-vc-chat",false).get(0).delete().complete();
        if (event.getChannelJoined().getMembers().size()==1){
            event.getGuild().getController().createTextChannel(event.getChannelJoined().getName().toLowerCase()+"-vc-chat").complete().createPermissionOverride(event.getGuild().getSelfMember()).setAllow(Permission.ALL_TEXT_PERMISSIONS).complete();
            event.getGuild().getTextChannelsByName(event.getChannelJoined().getName().toLowerCase()+"-vc-chat",false).get(0).createPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.ALL_TEXT_PERMISSIONS).complete();
        }
        event.getGuild().getTextChannelsByName(event.getChannelJoined().getName().toLowerCase()+"-vc-chat",false).get(0).createPermissionOverride(event.getMember()).setAllow(Permission.ALL_TEXT_PERMISSIONS).complete();
        if (event.getChannelLeft().getMembers().size() == 0){
            event.getChannelLeft().delete().complete();
        }
    }
}