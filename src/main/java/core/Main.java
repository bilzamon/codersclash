package core;

import java.util.Properties;

import javax.security.auth.login.LoginException;

import command.CommandManager;
import commands.CommandTest;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import util.Settings;

/**
 * The Class Main.
 */
public class Main {

	/** The jda. */
	private JDA jda;

	/** The properties. */
	private static Properties properties;

	/**
	 * Instantiates a new main.
	 */
	public Main() {
		properties = new Settings().loadSettings();
		initJDA();
		CommandManager commandManager = new CommandManager();
		initCommandHandlers(commandManager);
	}

	private void initCommandHandlers(CommandManager commandManager) {
		commandManager.setupCommandHandlers(new CommandTest());

	}

	/**
	 * Inits the JDA.
	 */
	private void initJDA() {
		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(properties.getProperty("Token"));

		new BuildManager(builder);

		try {
			jda = builder.buildBlocking();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the jda.
	 *
	 * @return the jda
	 */
	public JDA getJda() {
		return jda;
	}

	public static Properties getProperties() {
		return properties;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new Main();
	}
}
