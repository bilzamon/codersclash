package core;

import java.util.Properties;

import javax.security.auth.login.LoginException;

import command.CommandManager;
import commands.TestCommand;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import sql.PostgreSQL;
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
		
		setupSQL();
		initJDA();
		
		CommandManager commandManager = new CommandManager();
		initCommandHandlers(commandManager);
	}

	/**
	 * Setup SQL.
	 */
	private void setupSQL() {
		PostgreSQL sql = new PostgreSQL(properties.getProperty("Host"), properties.getProperty("Port"),
				properties.getProperty("User"), properties.getProperty("Password"), properties.getProperty("Database"));
		sql.connect();

	}

	/**
	 * Inits the command handlers.
	 *
	 * @param commandManager
	 *            the command manager
	 */
	private void initCommandHandlers(CommandManager commandManager) {
		commandManager.setupCommandHandlers(new TestCommand());

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

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
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
