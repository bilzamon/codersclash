package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.org.apache.xml.internal.utils.URI;

/**
 * The Class PostgreSQL.
 */
public class PostgreSQL {
	/** The connection. */
	private Connection connection;

	/** The host. */
	private String host;

	/** The port. */
	private String port;

	/** The user. */
	private String user;

	/** The password. */
	private String password;

	/** The database. */
	private String database;

	/**
	 * Instantiates a new postgre SQL.
	 *
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @param database
	 *            the database
	 */
	public PostgreSQL(String host, String port, String user, String password, String database) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
	}

	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			URI dbUri = new URI(System.getenv("DATABASE_URL"));

			String username = dbUri.getUserinfo().split(":")[0];
			String password = dbUri.getUserinfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

			System.out.println(dbUrl + " " + username + " " + password);
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected to database successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't connect to database. Please check the config file or connection.");
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	/**
	 * Disconnect.
	 */
	public void disconnect() {
		System.out.println("Closing connection to database...");
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
