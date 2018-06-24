package listeners;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * The listener interface for receiving ready events.
 * The class that is interested in processing a ready
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReadyListener<code> method. When
 * the ready event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReadyEvent
 */
public class ReadyListener extends ListenerAdapter {

	/* (non-Javadoc)
	 * @see net.dv8tion.jda.core.hooks.ListenerAdapter#onReady(net.dv8tion.jda.core.events.ReadyEvent)
	 */
	public void onReady(ReadyEvent event) {

		String out = "\nBlue lÃ¤uft auf:\n" + "----------------------------------\n";

		for (Guild g : event.getJDA().getGuilds()) {
			out += "-" + g.getName() + "\n";
		}

		Game[] games = new Game[] {

				Game.playing("xp-System"), Game.playing("Report-System"), Game.playing("MiniGame-Feature"),
				Game.playing("Voting System") };

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				event.getJDA().getPresence().setGame(games[ThreadLocalRandom.current().nextInt(4)]);
			}

		}, 0, 60000);

		System.out.println(out);
	}
}