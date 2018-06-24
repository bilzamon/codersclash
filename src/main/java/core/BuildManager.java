package core;

import command.CommandManager;
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
	}

}