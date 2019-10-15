
public class Bot0 implements BotAPI {

	// The public API of Bot must not change
	// This is ONLY class that you can edit in the program
	// Rename Bot to the name of your team. Use camel case.
	// Bot may not alter the state of the game objects
	// It may only inspect the state of the board and the player objects

	private PlayerAPI me, opponent;
	private BoardAPI board;
	private CubeAPI cube;
	private MatchAPI match;
	private InfoPanelAPI info;

	Bot0(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
		this.me = me;
		this.opponent = opponent;
		this.board = board;
		this.cube = cube;
		this.match = match;
		this.info = info;
	}

	public String getName() {
		return "Bot0"; // must match the class name
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Main method where all commands are there
	 * 
	 * @see BotAPI#getCommand(Plays)
	 */
	public String getCommand(Plays possiblePlays) {

		for (Play play : possiblePlays) {

			for (Move move : play) {

				// Function to calculate all the weights
				checkWeights(move);

			}
		}
		return "1";
	}

	/*
	 * Method with all the checks
	 */
	public void checkWeights(Move move) {

		// Check if there is a move where a pip has one disk of yours
		if (board.getNumCheckers(0, move.getToPip()) == 1) {
			// TODO Positive weight
		}

		// If its a hit
		if (move.isHit()) {
			// TODO Positive weight
		}

		// If the pip you are removing the disk from will have 1 disk left
		if (board.getNumCheckers(0, move.getFromPip()) == 2) {
			// TODO Negative weight
		}
	}

	public int checkOneDisk(Move move) {

		// Checks if there is 1 enemy disk on the pipTo
		if (board.getNumCheckers(0, move.getToPip()) > 1) {
			return 10;
		}

		return 0;
	}

	public String getDoubleDecision() {
		// Add your code here
		return "n";
	}
}
