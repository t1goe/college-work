import java.util.Arrays;

public class BotRand1 implements BotAPI {

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
    private int[] weights;

    BotRand1(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info, int[] weights) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
        this.weights = weights;
    }

    public String getName() {
        return "BotRand1"; // must match the class name
    }

    /*
     * (non-Javadoc)
     *
     * Main method where all commands are there
     *
     * @see BotAPI#getCommand(Plays)
     */
    public String getCommand(Plays possiblePlays) {
    	int maxMoves = 10000;
		int scores[] = new int[maxMoves];
    	
		
		int i = 0;
		for (Play play : possiblePlays) {

			for (Move move : play) {

				// Function to calculate all the weights
				scores[i] += checkWeights(move);

			}
			i++;
		}

		// Find the max
		int max = -999;
		int maxIndex = 0;
		for (i = 0; i < 10000; i++) {
			if (scores[i] > max) {
				max = scores[i];
				maxIndex = i;
			}
		}
		
		boolean offerDouble = offerDouble();
		if(offerDouble) {
			return "double";
		}
		
		return Integer.toString(maxIndex+1);
	}

    /*
     * Method with all the checks
     */
    public int checkWeights(Move move) {
    	int score = 0;
		// Check if there is a move where a pip has one disk of yours
		if (board.getNumCheckers(0, move.getToPip()) == 1) {
			score += weights[0];
		}

		// If its a hit
		if (move.isHit()) {
			score += weights [1];
		}

		// If the pip you are removing the disk from will have 1 disk left
		if (board.getNumCheckers(0, move.getFromPip()) == 2) {
			score += weights[2];
		}
		
		return score;
    }

    public int checkOneDisk(Move move) {

        // Checks if there is 1 enemy disk on the pipTo
        if (board.getNumCheckers(0, move.getToPip()) > 1) {
            return 10;
        }

        return 0;
    }
    
    public boolean offerDouble() {
		//Declarations
		double score = 0;
		int i = 1;
		double meScore = 0;
		double oppScore = 0;
		int numDiskOnPip;

		//Finds the probability of you wining and depending on outcome, double
		while (i < 25) {
			if (board.getNumCheckers(me.getId(), i) >= 1) {
				numDiskOnPip = board.getNumCheckers(me.getId(), i);
				meScore += numDiskOnPip * i;
			}
			if (board.getNumCheckers(opponent.getId(), i) >= 1) {
				numDiskOnPip = board.getNumCheckers(opponent.getId(), i);
				oppScore += numDiskOnPip * i;
			}
			i++;
			
			score = oppScore/meScore - 1;
		}
		
		//Threshold for doubling
		if(score >= 0.6) {
			return true;
		}
		return false;
	}

    public String getDoubleDecision() {
		
		//Declarations
		double score = 0;
		int i = 1;
		double meScore = 0;
		double oppScore = 0;
		int numDiskOnPip;

		//Finds the probability of you wining and depending on outcome, double
		while (i < 25) {
			if (board.getNumCheckers(me.getId(), i) >= 1) {
				numDiskOnPip = board.getNumCheckers(me.getId(), i);
				meScore += numDiskOnPip * i;
			}
			if (board.getNumCheckers(opponent.getId(), i) >= 1) {
				numDiskOnPip = board.getNumCheckers(opponent.getId(), i);
				oppScore += numDiskOnPip * i;
			}
			i++;
			
			score = oppScore/meScore - 1;
		}

		//TODO
		if(score > 0.6) {
			return "y";
		}else {
			return "n";
		}
	}
}
