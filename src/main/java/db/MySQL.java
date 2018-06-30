package db;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import commands.Voting;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import util.Settings;

/**
 * The Class Mysql.
 */
public class MySQL {
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
		generateReportTable();
		generateReportCountTable();
		generatePollTable();

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
					.prepareStatement("REPLACE INTO `xp` (id,userid,totalxp,level,notify) VALUES(?,?,?,?,?)");
			ps.setInt(1, data.getId());
			ps.setString(2, data.getUserId());
			ps.setLong(3, data.getTotalXp());
			ps.setLong(4, data.getLevel());
			ps.setBoolean(5, data.getLvlupNotify());
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
				data.setLvlupNotify(rs.getBoolean(5));
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getRankUserId() {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `xp` ORDER BY `totalxp` DESC LIMIT 10");

			ResultSet rs = ps.executeQuery();
			List<String> top10 = new ArrayList<String>();
			while (rs.next()) {
				top10.add(rs.getString(1));
			}
			return top10;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void insertReport(String userId, String reason) {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `report` (userid,reason) VALUES(?,?)");
			ps.setString(1, userId);
			ps.setString(2, reason);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertReportCount(String userId, int count) {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement("REPLACE INTO `reportcount` (userid,count) VALUES(?,?)");
			ps.setString(1, userId);
			ps.setInt(2, count);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int loadReportCount(String userId) {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `reportcount` WHERE `userid` = ?");
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void savePollData(PollData data) {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement(
					"REPLACE INTO `poll` (pollid,userid,messageid,users,open,option1,option2,option3,option4,option5,"
							+ "option6,option7,option8,option9,time,channelId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, data.getPollId());
			ps.setString(2, data.getUserId());
			ps.setString(3, data.getMessageId());
			ps.setString(4, data.getUsers());
			ps.setBoolean(5, data.isOpen());
			for (int i = 6; i <= 14; i++) {
				if (data.getOptions() != null) {
					ps.setInt(i, data.getOptions()[i - 6]);
				} else {
					ps.setInt(i, 0);
				}
			}
			ps.setTimestamp(15, Timestamp.valueOf(data.getTime()));
			ps.setString(16, data.getChannelId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PollData getPollData(String messageId) {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PollData data = new PollData();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `poll` WHERE `messageid` = ?");
			ps.setString(1, messageId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				data.setPollId(rs.getInt(1));
				data.setUserId(rs.getString(2));
				data.setMessageId(rs.getString(3));
				data.setUsers(rs.getString(4));
				data.setOpen(rs.getBoolean(5));

				int[] options = new int[9];
				for (int i = 0; i <= 8; i++) {
					options[i] = rs.getInt(i + 6);
				}
				data.setOptions(options);
				data.setTime(rs.getTimestamp(15).toLocalDateTime());
				data.setChannelId(rs.getString(16));
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
			PreparedStatement ps = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS xp( `id` INT(11) NOT NULL AUTO_INCREMENT, `userid` VARCHAR(50) NOT NULL, "
							+ "`totalxp` BIGINT(12) NOT NULL, `level` INT(11) NOT NULL, `notify` BOOLEAN NOT NULL, "
							+ "PRIMARY KEY(`id`) ) ENGINE = InnoDB DEFAULT CHARSET = utf8");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void loadPollTimer() {
		try {
			if (connection.isClosed()) {
				connect();
			}

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `poll`");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PollData data = new PollData();
				data.setPollId(rs.getInt(1));
				data.setUserId(rs.getString(2));
				data.setMessageId(rs.getString(3));
				data.setUsers(rs.getString(4));
				data.setOpen(rs.getBoolean(5));
				int[] options = new int[9];
				for (int i = 0; i <= 8; i++) {
					options[i] = rs.getInt(i + 6);
				}
				data.setOptions(options);
				data.setTime(rs.getTimestamp(15).toLocalDateTime());
				data.setChannelId(rs.getString(16));

				if (data.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
						- LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() > 0
						&& data.isOpen()) {
					if (Main.getJda().getTextChannelById(data.getChannelId()) != null) {
						Voting.timerStart(data.getTime(), data.getMessageId(),
								Main.getJda().getTextChannelById(data.getChannelId()));
					}
				} else if (data.isOpen()) {
					data.setOpen(false);
					if (Main.getJda().getTextChannelById(data.getChannelId()) != null) {
						Main.getJda().getTextChannelById(data.getChannelId())
								.sendMessage(
										new EmbedBuilder().setColor(Color.blue).setDescription("Poll closed!").build())
								.queue();
					}
					data.saveToDb(data);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void generateReportTable() {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS report( `id` INT(11) NOT NULL AUTO_INCREMENT, `userid` VARCHAR(50) NOT NULL, "
							+ "`reason` VARCHAR(50) NOT NULL, PRIMARY KEY(`id`) ) ENGINE = InnoDB DEFAULT CHARSET = utf8");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void generateReportCountTable() {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS reportcount ( `userid` VARCHAR(50) NOT NULL, `count` INT(11) NOT NULL, "
							+ "PRIMARY KEY(`userid`) ) ENGINE = InnoDB DEFAULT CHARSET = utf8");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void generatePollTable() {
		try {
			if (connection.isClosed()) {
				connect();
			}
			PreparedStatement ps = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS poll ( `pollid` INT(11) NOT NULL AUTO_INCREMENT, `userid` VARCHAR(50) NOT NULL, "
							+ "`messageid` VARCHAR(50) NOT NULL, `users` TEXT(1000),`open` BOOLEAN NOT NULL,"
							+ "`option1` VARCHAR(50) NOT NULL,`option2` VARCHAR(50) NOT NULL,`option3` VARCHAR(50) NOT NULL,"
							+ "`option4` VARCHAR(50) NOT NULL,`option5` VARCHAR(50) NOT NULL,`option6` VARCHAR(50) NOT NULL,"
							+ "`option7` VARCHAR(50) NOT NULL,`option8` VARCHAR(50) NOT NULL,`option9` VARCHAR(50) NOT NULL,"
							+ "`time` TIMESTAMP NOT NULL,`channelId` VARCHAR(50) NOT NULL,"
							+ "PRIMARY KEY(`pollid`) ) ENGINE = InnoDB DEFAULT CHARSET = utf8");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
