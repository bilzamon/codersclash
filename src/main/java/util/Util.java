package util;

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

}
