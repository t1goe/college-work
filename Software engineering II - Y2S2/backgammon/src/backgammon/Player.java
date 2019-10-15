package backgammon;

public class Player {
	
	private String playerName;
	private EnumValues.diskColors playerColor;
	private int score;
	
	public Player() {
		this.playerName = null;
		this.playerColor = EnumValues.diskColors.NONE;
		this.score = 0;
	}
	
	public Player(String playerName, EnumValues.diskColors diskColor) {
		this.playerName = playerName;
		this.playerColor = diskColor;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public int getPlayerScore() {
		return this.score;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setPlayerColor(EnumValues.diskColors playerColor) {
		this.playerColor = playerColor;
	}
	
	public void setPlayerScore(int score) {
		this.score = score;
	}
	
	public EnumValues.diskColors getPlayerColor(){
		return this.playerColor;
	}
}