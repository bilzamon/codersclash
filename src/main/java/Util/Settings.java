package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * The Class Settings.
 */
public class Settings {

	/**
	 * Load settings.
	 *
	 * @return the properties
	 */
	public Properties loadSettings() {
		Properties properties = new Properties();
		try {
			FileInputStream file = new FileInputStream("./config.properties");
			properties.load(file);
			file.close();
		} catch (IOException e) {
			System.err.println("Error can't load properties check the config.properties file");
			// load

			// TODO remove Heroku data
			properties.setProperty("Prefix", System.getenv().get("Prefix"));
			properties.setProperty("Token", System.getenv().get("Token"));

			/**
			 * // Setup config file properties.setProperty("Prefix", "");
			 * properties.setProperty("Token", "");
			 */
			File f = new File("config.properties");
			OutputStream out;
			try {
				out = new FileOutputStream(f);
				properties.store(out, "Enter your data here");
				out.close();
				System.out.println("Please enter your data in the config file!");
				// System.exit(0);
			} catch (IOException e1) {
				System.err.println("Error can't create config file");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return properties;
	}
}
