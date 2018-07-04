package db;

public class GameData {
	private String messageId;
	private String opponentId;
	private String challengerId;

	public GameData(String messageId, String opponentId, String challengerId) {
		this.messageId = messageId;
		this.opponentId = opponentId;
		this.challengerId = challengerId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getOpponentId() {
		return opponentId;
	}

	public void setOpponentId(String opponentId) {
		this.opponentId = opponentId;
	}

	public String getChallengerId() {
		return challengerId;
	}

	public void setChallengerId(String challengerId) {
		this.challengerId = challengerId;
	}

}
