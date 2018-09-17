package core;

import command.CommandManager;
import listeners.*;
import net.dv8tion.jda.core.JDABuilder;

/**
 * The Class BuildManager.
 */
public class BuildManager {

	/** The builder. */
	private JDABuilder builder;

	/**
	 * Instantiates a new builds the manager.
	 *
	 * @param builder the builder
	 */
	public BuildManager(JDABuilder builder) {
		this.builder = builder;
		addEventListeners();
	}

	/**
	 * Adds the event listeners.
	 */
	private void addEventListeners() {
		builder.addEventListener(new CommandManager());
		builder.addEventListener(new ReadyListener());
		builder.addEventListener(new XPListener());
		builder.addEventListener(new VotingListener());
		builder.addEventListener(new ChannelCreateListener());
		builder.addEventListener(new VCPrivateTextChannel());
		builder.addEventListener(new Autochannel());
		builder.addEventListener(new PrivateVoice());
		//builder.addEventListener(new VierGewinntListener());
		builder.addEventListener(new FourConnectListener());
	}
}
