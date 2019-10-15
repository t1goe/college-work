import java.util.Arrays;

public class BotRand0 implements BotAPI {

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

	BotRand0(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info,
			int[] weights) {
		this.me = me;
		this.opponent = opponent;
		this.board = board;
		this.cube = cube;
		this.match = match;
		this.info = info;
		this.weights = weights;
	}

	public String getName() {
		return "BotRand0"; // must match the class name
	}

	/*
	 * (non-Javadoc)
	 *
	 * Main method where all commands are there
	 *
	 * @see BotAPI#getCommand(Plays)
	 */
	public String getCommand(Plays possiblePlays) {
		int scores[] = new int[10000];
		int i = 0;
		for (Play play : possiblePlays) {

			for (Move move : play) {

				// Function to calculate all the weights
				scores[i] += checkWeights(move);
			}
			i++;
		}

		// Find the max
//		int max = -1001;
//		int maxIndex = -1;
//		for (i = 0; i < 10000; i++) {
//			if (scores[i] > max) {
//				max = scores[i];
//				maxIndex = i;
//			}
//		}

		boolean offerDouble = offerDouble();
		if (offerDouble) {
			return "double";
		}

		int highestPlay = 1;
		int playTotal = 0;
		int playCount = 1;
		String currentHighestPlay = "1";

		if (!unopposed()) {
			for (Play play : possiblePlays) {

				for (Move move : play) {
					playTotal += checkWeights(move);
				}

				if (playTotal > highestPlay) {
					highestPlay = playTotal;
					currentHighestPlay = Integer.toString(playCount);
				}
				playCount++;
			}
			return currentHighestPlay;
		}
		return "1";
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
			score += weights[1];
		}

		// If the pip you are removing the disk from will have 1 disk left
		if (board.getNumCheckers(0, move.getFromPip()) == 2) {
			score += weights[2];
		}

		int primeWeight = possiblePrime(move);
		score += primeWeight * weights[3];

		return score;
	}
	
	public int possiblePrime(Move move){
		int forwardPrimeSize = 0; 
		int backwardsPrimeSize = 0; 
		boolean diskInfront = false; 
		boolean possibleHit = false; 
		
		if(me.getId() == 1) { //if bot is player 1, aka green 
			int i = move.getToPip(); 
			while(i < 24) {
				if(board.getNumCheckers(0, i) >= 1) //if opponent has disk in front of possible prime 
					diskInfront = true;
				i++;
			}
			
			
			if(diskInfront || !unopposed()) {//if there is a disk in front and still opposed 
				if((board.getNumCheckers(1, (move.getToPip()+forwardPrimeSize)) >= 2 && move.getToPip()<25) ) {
					forwardPrimeSize++; 
				}
				
				if((board.getNumCheckers(0, (move.getToPip()-backwardsPrimeSize)) >= 2 && move.getToPip()>0) ) {
					backwardsPrimeSize++; 
				}
			}
		}
		
		else {//if bot is player 0, aka red
			int i = move.getToPip(); 
			while(i > 0) {
				if(board.getNumCheckers(1, i) >= 1)
					diskInfront = true;
				i--;
			}
			
			if(diskInfront && !unopposed()) {
				if((board.getNumCheckers(0, (move.getToPip()+forwardPrimeSize)) >= 2 && move.getToPip()<25) ) {
					forwardPrimeSize++; 
				}
				
				if((board.getNumCheckers(0, (move.getToPip()-backwardsPrimeSize)) >= 2 && move.getToPip()>0) ) {
					backwardsPrimeSize++; 
				}
			}
		}
		
		if(backwardsPrimeSize >= forwardPrimeSize)
			return backwardsPrimeSize; 
		else
			return forwardPrimeSize; 
		
	}

	public int checkOneDisk(Move move) {

		// Checks if there is 1 enemy disk on the pipTo
		if (board.getNumCheckers(0, move.getToPip()) > 1) {
			return 10;
		}

		return 0;
	}

	public boolean offerDouble() {
		double score = 0;
		int i = 1;
		double meScore = 0;
		double oppScore = 0;
		int numDiskOnPip;

		// Finds the probability of you wining and depending on outcome, double
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

			score = oppScore / meScore - 1;
		}
		if (score >= 0.8) {
			return true;
		}
		return false;
	}

	// can't hit the other player, race to finish
	public boolean unopposed() {
		int furthestA = 1;
		int furthestB = 24;

		for (int i = 1; i < 25; i++) {
			if (board.getNumCheckers(0, i) >= 1)
				furthestA = i;
		}

		for (int i = 24; i > 0; i--) {
			if (board.getNumCheckers(1, i) >= 1)
				furthestB = i;
		}

		if (furthestB - furthestA <= 0) {
			return false;
		}

		else
			return true;

	}

	public String getDoubleDecision() {
		double score = 0;
		int i = 1;
		double meScore = 0;
		double oppScore = 0;
		int numDiskOnPip;

		// Finds the probability of you wining and depending on outcome, double
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

			score = oppScore / meScore - 1;
		}

		if (score > 0.8) {
			return "y";
		} else {
			return "n";
		}
		
	}
}
