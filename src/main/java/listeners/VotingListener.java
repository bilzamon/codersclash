package listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.PollData;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VotingListener extends ListenerAdapter {

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		if (event.getMember().getUser().isBot()) {
			return;
		}
		String emoteName = event.getReactionEmote().getName();
		event.getReaction().removeReaction(event.getUser()).queue();
		PollData pData = new PollData().getDbData(event.getMessageId());

		if (!pData.isOpen() || pData.getMessageId() == null) {
			return;
		}

		List<String> usersVoted = null;
		if (pData.getUsers() != null) {
			usersVoted = new ArrayList<String>(Arrays.asList(pData.getUsers().split(",")));
		} else {
			usersVoted = new ArrayList<String>(Arrays.asList());
		}

		if (usersVoted.contains(event.getUser().getId())) {
			return;
		}

		int options[] = pData.getOptions();

		switch (emoteName) {
		case "1⃣":
			options[0] += 1;
			break;
		case "2⃣":
			options[1] += 1;
			break;
		case "3⃣":
			options[2] += 1;
			break;
		case "4⃣":
			options[3] += 1;
			break;
		case "5⃣":
			options[4] += 1;
			break;
		case "6⃣":
			options[5] += 1;
			break;
		case "7⃣":
			options[6] += 1;
			break;
		case "8⃣":
			options[7] += 1;
			break;
		case "9⃣":
			options[8] += 1;
			break;
		default:
			break;
		}

		pData.setOptions(pData.getOptions());
		usersVoted.add(event.getUser().getId());

		StringBuilder users = new StringBuilder();

		for (String tmp : usersVoted) {
			users.append(tmp + ",");
		}

		pData.setUsers(users.toString());
		pData.saveToDb(pData);
	}
}
