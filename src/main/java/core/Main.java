package core;

import javax.security.auth.login.LoginException;

import command.CommandManager;
import commands.Clear;
import commands.GuildInfo;
import commands.Ping;
import commands.Report;
import commands.Say;
import commands.UserInfo;
import commands.Voting;
import commands.mute;
import commands.unmute;
import commands.xp.Xp;
import commands.xp.XpNotify;
import db.Mysql;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import util.Settings;

/**
 * The Class Main.
 */
public class Main {

	/** The jda. */
	private static JDA jda;

	private static Mysql sql;

	/**
	 * Instantiates a new main.
	 */
	public Main() {
		Settings.loadSettings();

		Mysql.connect();

		initJDA();

		CommandManager commandManager = new CommandManager();
		initCommandHandlers(commandManager);
	}

	/**
	 * Inits the command handlers.
	 *
	 * @param commandManager
	 *            the command manager
	 */
	private void initCommandHandlers(CommandManager commandManager) {
		commandManager.setupCommandHandlers(new Clear());
		commandManager.setupCommandHandlers(new GuildInfo());
		commandManager.setupCommandHandlers(new Ping());
		commandManager.setupCommandHandlers(new Say());
		commandManager.setupCommandHandlers(new UserInfo());
		commandManager.setupCommandHandlers(new Xp());
		commandManager.setupCommandHandlers(new Report());
		commandManager.setupCommandHandlers(new Voting());
		commandManager.setupCommandHandlers(new mute());
		commandManager.setupCommandHandlers(new unmute());
		commandManager.setupCommandHandlers(new XpNotify());
	}

	/**
	 * Inits the JDA.
	 */
	private void initJDA() {
		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(Settings.TOKEN);

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
	public static JDA getJda() {
		return jda;
	}

	public static Mysql getSql() {
		return sql;
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
