package Core;

import java.util.Properties;

import javax.security.auth.login.LoginException;

import Command.CommandManager;
import Util.Settings;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

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
		commandManager.setupCommandHandlers();

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
