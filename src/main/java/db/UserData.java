package db;

import util.Level;

// TODO: Auto-generated Javadoc
/**
 * The Class UserData.
 */
public class UserData {

	/** The id. */
	private int id;

	/** The user id. */
	private String userId;

	/** The total xp. */
	private long totalXp;

	/** The level. */
	private int level;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Gets the total xp.
	 *
	 * @return the total xp
	 */
	public long getTotalXp() {
		return totalXp;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTotalXp(long totalXp) {
		this.totalXp = totalXp;

		setLevel(Level.calcLevel(totalXp));
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void save(UserData data) {
		PostgreSQL.saveUserData(data);
	}

	public static UserData fromId(String id) {
		return PostgreSQL.loadFromId(id);
	}

}
