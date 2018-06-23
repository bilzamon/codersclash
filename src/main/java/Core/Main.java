package Core;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class Main {

	private JDA jda;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		initJDA();
	}

	private void initJDA() {
		JDABuilder builder = new JDABuilder(AccountType.BOT)
				.setToken("NDYwMDYxNTUxMjYxNjQ2ODU4.Dg_RWQ.G9pRhQI_LT0F6-UX6qPrYCDHGgg");
		builder.setGame(Game.playing("lol"));

		try {
			jda = builder.buildBlocking();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public JDA getJda() {
		return jda;
	}
}
