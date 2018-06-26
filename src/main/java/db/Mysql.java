package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Settings;

/**
 * The Class PostgreSQL.
 */
public class Mysql {
	/** The connection. */
	private static Connection connection;

	/**
	 * Connect to mysql
	 */
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://" + Settings.HOST + ":" + Settings.PORT + "/" + Settings.DATABASE
					+ "?useUnicode=true&serverTimezone=UTC&autoReconnect=true";

			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(url, Settings.USER, Settings.PASSWORD);
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
			PreparedStatement ps = connection
					.prepareStatement("REPLACE INTO `xp` (id,userid,totalxp,level) VALUES(?,?,?,?)");
			ps.setInt(1, data.getId());
			ps.setString(2, data.getUserId());
			ps.setLong(3, data.getTotalXp());
			ps.setLong(4, data.getLevel());
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
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `xp` WHERE `userid` = ?");
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				data.setId(rs.getInt(1));
				data.setUserId(rs.getString(2));
				data.setDBTotalXp(rs.getLong(3));
				data.setDBLevel(rs.getInt(4));
				return data;
			}
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
			PreparedStatement ps = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS xp( `id` INT(11) NOT NULL AUTO_INCREMENT, `userid` VARCHAR(50) NOT NULL, `totalxp` BIGINT(12) NOT NULL, `level` INT(11) NOT NULL, PRIMARY KEY(`id`) ) ENGINE = InnoDB DEFAULT CHARSET = utf8");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
