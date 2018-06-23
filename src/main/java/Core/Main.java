package Core;

import java.util.Properties;

import javax.security.auth.login.LoginException;

import Util.Settings;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class Main {

	private JDA jda;
	private static Properties properties;

	public static void main(String[] args) {
		properties = new Settings().loadSettings();
		new Main();
	}

	public Main() {
		initJDA();
	}

	private void initJDA() {
		JDABuilder builder = new JDABuilder(AccountType.BOT)
				.setToken(properties.getProperty("Token"));
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
