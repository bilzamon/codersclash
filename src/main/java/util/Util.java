package util;

import db.PollData;
import net.dv8tion.jda.core.entities.Message;

public class Util {

	public static String getVotingOptions(String[] input) {
		String options = "";
		for (int i = 2; i < input.length; i++) {
			switch (i - 1) {
			case 1:
				options += " :one: ";
				break;
			case 2:
				options += ":two: ";
				break;
			case 3:
				options += ":three: ";
				break;
			case 4:
				options += ":four: ";
				break;
			case 5:
				options += ":five: ";
				break;
			case 6:
				options += ":six: ";
				break;
			case 7:
				options += ":seven: ";
				break;
			case 8:
				options += ":eight: ";
				break;
			case 9:
				options += ":nine: ";
				break;
			default:
				break;
			}
			options += input[i] + "\n";
		}
		return options;
	}

	public static void addReactionsToMessage(Message message, int length) {
		for (int i = 2; i < length; i++) {
			switch (i - 1) {
			case 1:
				message.addReaction("1⃣").queue();
				break;
			case 2:
				message.addReaction("2⃣").queue();
				break;
			case 3:
				message.addReaction("3⃣").queue();
				break;
			case 4:
				message.addReaction("4⃣").queue();
				break;
			case 5:
				message.addReaction("5⃣").queue();
				break;
			case 6:
				message.addReaction("6⃣").queue();
				break;
			case 7:
				message.addReaction("7⃣").queue();
				break;
			case 8:
				message.addReaction("8⃣").queue();
				break;
			case 9:
				message.addReaction("9⃣").queue();
				break;
			default:
				break;
			}
		}

	}

	public static int[] getOptionFromMessage(PollData pData, String emoteName) {
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
		return options;
	}

}
