package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class PostgreSQL.
 */
public class PostgreSQL {
	/** The connection. */
	private static Connection connection;

	/** The host. */
	private static String host;

	/** The port. */
	private static String port;

	/** The user. */
	private static String user;

	/** The password. */
	private static String password;

	/** The database. */
	private static String database;

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

	/**
	 * Connect to postgresql
	 */
	public static void connect() {
		try {
			Class.forName("org.postgresql.Driver");

			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection("jdbc:postgresql://" + host + ':' + port + "/" + database, user,
					password);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't connect to database. Please check the config file or connection.");
			System.exit(0);
		}
		System.out.println("Connected to database successfully...");
		generateXpTable();
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

	public static Connection getConnection() {
		return connection;
	}

	public static void saveUserData(UserData data) {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO `xp` (userid,totalxp,level) VALUES(?,?,?) ON DUPLICATE KEY UPDATE userid=?");
			ps.setString(1, data.getUserId());
			ps.setLong(2, data.getTotalXp());
			ps.setLong(2, data.getLevel());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static UserData loadFromId(String userId) {
		UserData data = new UserData();
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement(
					"SELECT * FROM `xp` WHERE `userid` = ?");
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				rs.next();
				data.setId(rs.getInt(0));
				data.setUserId(rs.getString(1));
				data.setTotalXp(rs.getLong(2));
				data.setLevel(rs.getInt(4));
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void generateXpTable() {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `xp`(\r\n" + 
					"    `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"    `userid` VARCHAR(50) NOT NULL,\r\n" + 
					"    `totalxp` BIGINT(12) NOT NULL,\r\n" + 
					"    `level` INT(11) NOT NULL,\r\n" + 
					"    PRIMARY KEY(`id`)\r\n" + 
					") ENGINE = InnoDB DEFAULT CHARSET = utf8;");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
