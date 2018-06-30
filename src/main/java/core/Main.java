package core;

import javax.security.auth.login.LoginException;

import command.CommandManager;
import commands.*;
import commands.xp.XP;
import commands.xp.XPNotify;
import commands.xp.XPrank;
import db.MySQL;
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

	private static MySQL sql;

	/**
	 * Instantiates a new main.
	 */
	public Main() {
		Settings.loadSettings();

		MySQL.connect();

		initJDA();

		CommandManager commandManager = new CommandManager();
		initCommandHandlers(commandManager);
		
		MySQL.loadPollTimer();
	}

	/**
	 * Inits the command handlers.
	 *
	 * @param commandManager
	 *            the command manager
	 */
	private void initCommandHandlers(CommandManager commandManager) {
		commandManager.setupCommandHandlers(new Clear());
		commandManager.setupCommandHandlers(new Stop());
		commandManager.setupCommandHandlers(new GuildInfo());
		commandManager.setupCommandHandlers(new Ping());
		commandManager.setupCommandHandlers(new Say());
		commandManager.setupCommandHandlers(new UserInfo());
		commandManager.setupCommandHandlers(new XP());
		commandManager.setupCommandHandlers(new XPrank());
		commandManager.setupCommandHandlers(new Report());
		commandManager.setupCommandHandlers(new Voting());
		commandManager.setupCommandHandlers(new Mute());
		commandManager.setupCommandHandlers(new UnMute());
		commandManager.setupCommandHandlers(new XPNotify());
		commandManager.setupCommandHandlers(new AddUserToPrivateVoiceChannel());
		commandManager.setupCommandHandlers(new VierGewinnt());
		commandManager.setupCommandHandlers(new Help());
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

	public static MySQL getSql() {
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
