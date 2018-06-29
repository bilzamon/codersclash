package db;

// TODO: Auto-generated Javadoc
/**
 * The Class PollData.
 */
public class PollData {

	/** The poll id. */
	private int pollId;

	/** The user id. */
	private String userId;

	/** The message id. */
	private String messageId;

	/** The users. */
	private String users;

	/** The time. */
	//private Time time;

	/** The options. */
	private int[] options;

	/** The open. */
	private boolean open = true;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * Gets the poll id.
	 *
	 * @return the poll id
	 */
	public int getPollId() {
		return pollId;
	}

	/**
	 * Sets the poll id.
	 *
	 * @param pollId
	 *            the new poll id
	 */
	public void setPollId(int pollId) {
		this.pollId = pollId;
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
	 * Sets the user id.
	 *
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the message id.
	 *
	 * @return the message id
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * Sets the message id.
	 *
	 * @param string
	 *            the new message id
	 */
	public void setMessageId(String string) {
		this.messageId = string;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public String getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users
	 *            the new users
	 */
	public void setUsers(String users) {
		this.users = users;
	}

	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public int[] getOptions() {
		return options;
	}

	/**
	 * Sets the options.
	 *
	 * @param options
	 *            the new options
	 */
	public void setOptions(int[] options) {
		this.options = options;
	}

	/**
	 * Save to db.
	 *
	 * @param data
	 *            the data
	 */
	public void saveToDb(PollData data) {
		Mysql.savePollData(data);
	}

	/**
	 * Save to db.
	 *
	 * @param data
	 *            the data
	 * @return
	 */
	public PollData getDbData(String messageId) {
		return Mysql.getPollData(messageId);
	}
}
