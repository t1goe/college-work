import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Backgammon {
	// This is the main class for the Backgammon game. It orchestrates the running
	// of the game.

	public static final int NUM_PLAYERS = 2;
	public static final boolean CHEAT_ALLOWED = false;
	private static final int DELAY = 1; // in milliseconds
	private static final String[] ALL_BOT_NAMES = { "Bot0", "Bot1", "BotRand0", "BotRand1" };

	private final Cube cube = new Cube();
	private final Players players = new Players();
	private final Board board = new Board(players);
	private final Game game = new Game(board, cube, players);
	private final Match match = new Match(game, cube, players);
	private BotAPI[] bots = new BotAPI[NUM_PLAYERS];
	private final UI ui = new UI(board, players, cube, match, bots);
	private String[] botNames = new String[NUM_PLAYERS];
	private boolean quitGame = false;

	private void setupBots(String[] args, int[] weightSet1, int[] weightSet2) {
		if (args.length < NUM_PLAYERS) {
			botNames[0] = "BotRand0";
			botNames[1] = "BotRand1";
		} else {
			for (int i = 0; i < NUM_PLAYERS; i++) {
				boolean found = false;
				for (int j = 0; (j < ALL_BOT_NAMES.length) && !found; j++) {
					if (args[i].equals(ALL_BOT_NAMES[j])) {
						found = true;
						botNames[i] = args[i];
					}
				}
				if (!found) {
					System.out.println("Error: Bot name not found");
					System.exit(-1);
				}
			}
		}
		if (args.length < NUM_PLAYERS + 1) {
			match.setLength(10);
		} else {
			match.setLength(Integer.parseInt(args[2]));
		}
		for (int i = 0; i < NUM_PLAYERS; i++) {
			try {
				Class<?> botClass = Class.forName(botNames[i]);
				Constructor<?> botCons = botClass.getDeclaredConstructor(PlayerAPI.class, PlayerAPI.class,
						BoardAPI.class, CubeAPI.class, MatchAPI.class, InfoPanelAPI.class, int[].class);
				if (i == 0) {
					bots[i] = (BotAPI) botCons.newInstance(players.get(0), players.get(1), board, cube, match,
							ui.getInfoPanel(), weightSet1);
				} else {
					bots[i] = (BotAPI) botCons.newInstance(players.get(1), players.get(0), board, cube, match,
							ui.getInfoPanel(), weightSet2);
				}
			} catch (IllegalAccessException ex) {
				System.out.println("Error: Bot instantiation fail (IAE)");
				Thread.currentThread().interrupt();
			} catch (InstantiationException ex) {
				System.out.println("Error: Bot instantiation fail (IE)");
				Thread.currentThread().interrupt();
			} catch (ClassNotFoundException ex) {
				System.out.println("Error: Bot instantiation fail (CNFE)");
				Thread.currentThread().interrupt();
			} catch (InvocationTargetException ex) {
				System.out.println("Error: Bot instantiation fail (ITE)");
				Thread.currentThread().interrupt();
			} catch (NoSuchMethodException ex) {
				System.out.println("Error: Bot instantiation fail (NSME)");
				Thread.currentThread().interrupt();
			}
		}
	}

	private void pause() throws InterruptedException {
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void getPlayerNames() {
		for (Player player : players) {
			ui.promptPlayerName();
			String name = ui.getName(player);
			player.setName(name);
			// ui.displayPlayerColor(player);
		}
	}

	private void rollToStart() throws InterruptedException {
		ui.display();
		do {
			for (Player player : players) {
				player.getDice().rollDice();
				// ui.displayDieRoll(player);
			}
			if (players.isEqualDice()) {
				// ui.displayDiceEqual();
			}
		} while (players.isEqualDice());
		players.setCurrentAccordingToDieRoll();
		// ui.displayDiceWinner(players.getCurrent());
		ui.display();
		pause();
	}

	private void playAGame() throws InterruptedException {
		Command command = new Command();
		boolean firstMove = true;
		game.reset();
		rollToStart();
		do {
			Player currentPlayer = players.getCurrent();
			Dice currentDice;
			if (firstMove) {
				currentDice = players.getOneDieFromEachPlayer();
				firstMove = false;
			} else {
				currentPlayer.getDice().rollDice();
				// ui.displayDiceRoll(currentPlayer);
				currentDice = currentPlayer.getDice();
			}
			Plays possiblePlays;
			possiblePlays = board.getPossiblePlays(currentPlayer, currentDice);
			if (possiblePlays.number() == 0) {
				// ui.displayNoMove(currentPlayer);
			} else if (possiblePlays.number() == 1) {
				// ui.displayForcedMove(currentPlayer);
				board.move(currentPlayer, possiblePlays.get(0));
			} else {
				// ui.displayPlays(currentPlayer,
				// possiblePlays);//---------------------------------------------------------------------------------------------------------------
				boolean turnOver = false, hasDoubled = false;
				do {
					command = ui.getCommand(currentPlayer, possiblePlays);
					if (command.isMove()) {
						board.move(currentPlayer, command.getPlay());
						turnOver = true;
					} else if (command.isDouble()) {
						Player opposingPlayer = players.getOpposingPlayer(currentPlayer);
						if (!hasDoubled) {
							if (match.canDouble(currentPlayer)
									&& (!cube.isOwned() || cube.getOwner().equals(currentPlayer))) {
								if (ui.getDoubleDecision(opposingPlayer)) {
									cube.accept(opposingPlayer);
									ui.display();
									hasDoubled = true;
								} else {
									game.resign(opposingPlayer);
									turnOver = true;
								}
							} else {
								ui.displayCannotDouble(currentPlayer);
							}
						} else {
							ui.displayHasDoubled(currentPlayer);
						}
					} else if (command.isCheat()) {
						board.cheat();
						turnOver = true;
					} else if (command.isQuit()) {
						quitGame = true;
						turnOver = true;
					}
					pause();
				} while (!turnOver);
			}
			ui.display();
			players.advanceCurrentPlayer();
		} while (!quitGame && !game.isOver());
		if (game.isOver()) {
			ui.displayGameWinner(game.getWinner());
		}
	}

	private int playAMatch() throws InterruptedException {
		// ui.displayStartOfGame();
		getPlayerNames();
		ui.displayString("Match length is " + match.getLength());
		do {
			playAGame();
			if (!quitGame) {
				int points = match.getPoints();
				match.updateScores(points);
				// ui.displayPointsWon(match.getWinner(),points);
				ui.displayScore(players, match);
			}
			pause();
		} while (!quitGame && !match.isOver());
		if (match.isOver()) {
			ui.displayMatchWinner(match.getWinner());
		}
		pause();
		pause();
		ui.closeUI();

		return match.getWinner().getId();
	}

	public static void main(String[] args) throws InterruptedException {
		final int NUMBER_OF_WEIGHTS = 10;
		final int UPPER_WEIGHT_BOUND = 100;
		int gameScore[] = new int[2];
		int[] weights1 = new int[NUMBER_OF_WEIGHTS];
		int[] weights2 = new int[NUMBER_OF_WEIGHTS];

		// Fills both weight arrays
		scrambleArray(weights1, UPPER_WEIGHT_BOUND);
		scrambleArray(weights2, UPPER_WEIGHT_BOUND);

		// Keeps track of what "generation" the current bot is (ie: bot is reset when
		// re-randomized)
		int generation1 = 0, generation2 = 0;

		// Keep playing games and outputting the winner
		while (true) {

			// Plays n games and takes the player who won the most games, greater n has
			// greater confidence but takes longer to compute
			int n = 10;
			for (int i = 0; i < n; i++) {
				Backgammon game = new Backgammon();
				game.setupBots(args, weights1, weights2);
				gameScore[game.playAMatch()]++;// Increases the size of gameScore at the position corrisponding to the
												// winner's id
				System.out.println("Game completed");
			}

			generation1++;
			generation2++;

			// Announces the winner and randomizes the loser; simply adjust the
			// scrambleArray function if you have a more efficient way of determining new
			// weight sets
			if (gameScore[0] > gameScore[1]) {
				System.out.println(
						"\n\nID:0 won with weights" + Arrays.toString(weights1) + "\nGeneraiton " + generation1);
				System.out.println("\nID:1 lost with weights" + Arrays.toString(weights2) + "\n");
				System.out.println("Score ID:0 = " + gameScore[0] + "\nScore ID:1 = " + gameScore[1]);
				scrambleArray(weights2, UPPER_WEIGHT_BOUND);
				generation2 = 0;
			} else {
				System.out.println(
						"\n\nID:1 won with weights" + Arrays.toString(weights2) + "\nGeneraiton " + generation2);
				System.out.println("\nID:0 lost with weights" + Arrays.toString(weights1) + "\n");
				System.out.println("Score ID:0 = " + gameScore[0] + "\nScore ID:1 = " + gameScore[1]);
				scrambleArray(weights1, UPPER_WEIGHT_BOUND);
				generation1 = 0;
			}
		}
	}

	private static void scrambleArray(int[] array, int upperBound) {
		Random rand = new Random();
		for (int i = 0; i < array.length; i++)
			array[i] = rand.nextInt(upperBound);
	}
}
