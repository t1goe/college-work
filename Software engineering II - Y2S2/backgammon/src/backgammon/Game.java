package backgammon;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Game {

	String message;

	Player playerOne;
	Player playerTwo;

	// States who's turn it currently is
	static EnumValues.diskColors currentTurnColor = EnumValues.diskColors.BLACK;
	private int moveNumber = 2;
	private boolean gameStart = false;
	private int matchScore = 0;

	public static Score score;

	// Conditions for scores
	private boolean crawfordRuleActive = false;

	/**
	 * Sets up GUI and the board then begins the game
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Game() throws IOException, InterruptedException {
		new GUI();
		new Board();

		// Create 2 players
		playerOne = new Player();
		playerTwo = new Player();

		playGame();
	}

	public void playGame() throws InterruptedException, IOException {

		// Main game loop
		do {
			message = GUI.getCommand();
			GUI.displayString(message);
			String[] textData = message.trim().split("\\s+");

			try {
				if (textData[0].equalsIgnoreCase("play")) {

					gameStart = true;

					displayScores();

					getPlayerDetails();

					if (matchScore == 0) {
						setGameScore();
						GUI.displayString("The score is set to: " + matchScore + " Good Luck!");
					} else {
						GUI.displayString("SCORE");
						GUI.displayString(playerOne.getPlayerName() + " : " + playerOne.getPlayerScore() + " || "
								+ playerTwo.getPlayerName() + " : " + playerTwo.getPlayerScore());
					}
					GUI.displayString("PlayerOne score: " + playerOne.getPlayerScore());
					GUI.displayString("PlayerTwo score: " + playerTwo.getPlayerScore());

					displayScores();

					currentTurnColor = firstRoll(Board.diceArray[0], Board.diceArray[1]);

					if (currentTurnColor == EnumValues.diskColors.BLACK) {
						Move.switchPipNumbers(playerTwo.getPlayerColor());
					} else {
						Move.switchPipNumbers(playerOne.getPlayerColor());
					}

					moveNumber = 10;

					/*
					 * This is the main game loop, the do while loop will run until we have a winner
					 */
					do {
						int movesMade = 0;
						int diceTotal = Board.diceArray[0].getNumber() + Board.diceArray[1].getNumber();
						int numDice = 0;
						boolean doubles = Board.diceArray[0].getNumber() == Board.diceArray[1].getNumber();

						if (doubles) {// if dice are equal, set number of dice to 4 and set the total number of moves
										// to 4 times dice 0
							diceTotal = Board.diceArray[0].getNumber() * 4;
							numDice = 4;
						}
						do {
							/*
							 * Prints all the available valid moves
							 */
							if (doubles) {
								Move.printValidDoubleMoves(currentTurnColor, Board.diceArray[0].getNumber(), numDice);
							} else if (movesMade == Board.diceArray[0].getNumber()) {
								Move.printValidMoves(currentTurnColor, 0, Board.diceArray[1].getNumber());
							} else if (movesMade == Board.diceArray[1].getNumber()) {
								Move.printValidMoves(currentTurnColor, Board.diceArray[0].getNumber(), 0);
							} else {
								Move.printValidMoves(currentTurnColor, Board.diceArray[0].getNumber(),
										Board.diceArray[1].getNumber());
							}

							// if no moves are available, move to next player
							if ((Move.possibleMoves[0][0]) == "" && (Move.possibleMoves[0][1]) == "") {
								TimeUnit.MILLISECONDS.sleep(1000);
								GUI.displayString("Uh oh! You have no possible moves!");
								diceTotal = -1;
								// if only one move is available
							} else if ((Move.possibleMoves[0][0]) != "" && (Move.possibleMoves[0][1]) != ""
									&& (Move.possibleMoves[1][0]) == "" && (Move.possibleMoves[1][1]) == "") {
								GUI.displayString("There's only one possible move!");
								TimeUnit.MILLISECONDS.sleep(1000);
								int pipFrom = Move.convertStringCommand(Move.possibleMoves[0][0]);
								int pipTo = Move.convertStringCommand(Move.possibleMoves[0][1]);

								if (!Character.isLetter(Move.possibleMoves[0][0].toCharArray()[0]))
									pipFrom++;

								if (!Character.isLetter(Move.possibleMoves[0][1].toCharArray()[0]))
									pipTo++;

								move(pipFrom, pipTo);
								diceTotal = -1;
							} else {

								/*
								 * Inner loop is where all the additional commands can be made while choosing a
								 * position to move
								 */
								do {
									message = GUI.getCommand();
									message = message.replaceAll("\\s+", "");
									GUI.displayString(message);

									if (message.equalsIgnoreCase("cheat")) {
										GUI.displayString("CHEAT MODE");
										Board.cheat();
										doubles = Board.diceArray[0].getNumber() == Board.diceArray[1].getNumber();
										if (doubles) {
											Move.printValidDoubleMoves(currentTurnColor, Board.diceArray[0].getNumber(),
													numDice);
										} else if (movesMade == Board.diceArray[0].getNumber()) {
											Move.printValidMoves(currentTurnColor, 0, Board.diceArray[1].getNumber());
										} else if (movesMade == Board.diceArray[1].getNumber()) {
											Move.printValidMoves(currentTurnColor, Board.diceArray[0].getNumber(), 0);
										} else {
											Move.printValidMoves(currentTurnColor, Board.diceArray[0].getNumber(),
													Board.diceArray[1].getNumber());
										}
										TimeUnit.MILLISECONDS.sleep(100);
										movesMade = 0;
									} else if (message.equalsIgnoreCase("quit")) {
										System.exit(0);
									} else {
										int attemptSpaces = 0;
										attemptSpaces = enterMove(message, diceTotal, movesMade + diceTotal);
										movesMade = attemptSpaces;

										if (currentTurnColor == EnumValues.diskColors.BLACK) {
											movesMade *= -1;
										}

										diceTotal -= movesMade;
										if (doubles) {
											numDice--;
										} // reduce num of double dice by 1
										GUI.displayString(diceTotal + " spaces left to move\n");
									}
								} while (movesMade == 0);
							}
							System.out.println("YEET: diceTotal = " + diceTotal);
						} while (diceTotal > 0);

						//Returns false if there is a winner
						gameStart = checkForWinner();
						
						if (gameStart) {
							next();
						}

					} while (Board.blackBearZone.stackSize() != 15 && Board.whiteBearZone.stackSize() != 15 && gameStart);
				}

			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Incorrect number of arguments for given command ~205");
				e.printStackTrace();
			}
		} while (!message.equalsIgnoreCase("quit"));// If user inputs exit > end program
	}

	/*
	 * Check to see if player won round or the total score limit has been reached
	 */
	public boolean checkForWinner() {
		// Check to see which player belongs to white/black bear off zones
		if (Board.blackBearZone.stackSize() == 15) {
			if (playerOne.getPlayerColor() == Board.blackBearZone.diskStack.peek().getDiskColor()) {
				checkScore(playerOne, playerTwo);
				gameWinner(playerOne);
				return false;
			} else {
				checkScore(playerTwo, playerOne);
				gameWinner(playerTwo);
				return false;
			}
		} else if (Board.whiteBearZone.stackSize() == 15) {
			if (playerOne.getPlayerColor() == Board.whiteBearZone.diskStack.peek().getDiskColor()) {
				checkScore(playerOne, playerTwo);
				gameWinner(playerOne);
				return false;
			} else {
				checkScore(playerTwo, playerOne);
				gameWinner(playerTwo);
				return false;
			}
		}

		// Overall winner
		if (playerOne.getPlayerScore() >= matchScore) {
			gameWinner(playerOne);
		} else if (playerTwo.getPlayerScore() >= matchScore) {
			gameWinner(playerTwo);
		}

		return true;
	}

	/*
	 * Checks what the score is for the current match and adds it to the winning
	 * players total
	 */
	public void checkScore(Player winner, Player loser) {
		int gameScore = 0;
		if (loser.getPlayerColor() == EnumValues.diskColors.BLACK) {

			// This will give a game value of 1
			if (Board.blackBearZone.stackSize() > 0) {
				gameScore++;
			} else {// 2 points
				gameScore += 2;
			}
		} else if (loser.getPlayerColor() == EnumValues.diskColors.WHITE) {

			// This will give a game value of 1
			if (Board.whiteBearZone.stackSize() > 0) {
				gameScore++;
			} else {// 2 points
				gameScore += 2;
			}
		}

		// TODO
		// If the doubling cube is used, double the current game score
		if (true) {// TO BE CHANGED
			gameScore *= 2;
		}

		// Checks if this rule can be used. It can also only be used once
		if (crawfordRuleActive == false) {
			checkCrawfordRule();
		}

		checkDeadCube();

		winner.setPlayerScore(gameScore + winner.getPlayerScore());
	}

	/*
	 * Checks what the scores are and updates them
	 */
	public void displayScores() throws IOException {
		// If start of game, display score: 0 for both players, else display current
		// player score
		if (matchScore == 0) {
			Board.playerTwoScoreArray[0].getNumber().setVisible(true);
			Board.playerOneScoreArray[0].getNumber().setVisible(true);
		} else {
			Board.playerTwoScoreArray[playerTwo.getPlayerScore()].getNumber().setVisible(true);
			Board.playerOneScoreArray[playerOne.getPlayerScore()].getNumber().setVisible(true);
		}
		Board.totalScoreArray[0].getNumber().setVisible(false);
		Board.totalScoreArray[matchScore].getNumber().setVisible(true);
	}

	/*
	 * On the first game after either player reaches a match score which is one less
	 * than the match length, neither player can double. Players can double in
	 * subsequent games
	 */
	public void checkCrawfordRule() {
		if (playerOne.getPlayerScore() == matchScore - 1 || playerTwo.getPlayerScore() == matchScore - 1) {
			// TODO
			// Deactivate the doubling cube for 1 game and reactivate it for subsequent
			// games
		}
		// Can now no longer be used for the duration of this whole game
		crawfordRuleActive = true;
	}

	/*
	 * The cube is said to be dead when the player owning the cube has no reason to
	 * double.
	 */
	public void checkDeadCube() {
		// Check which player has a higher score
		if (playerOne.getPlayerScore() == matchScore - 2) {
			// TODO
			// Player 1 cannot activate the doubling cube
		} else if (playerTwo.getPlayerScore() == matchScore - 2) {
			// TODO
			// Player 2 cannot activate the doubling cube
		}
	}

	/**
	 * Announce the game winner & ask if user wants to play again
	 *
	 * @param player
	 */
	public void gameWinner(Player player) {

		if (player.getPlayerScore() >= matchScore) {
			GUI.displayString("\nCongratulations " + player.getPlayerName() + ", You have won the game with "
					+ player.getPlayerScore() + " points!");
			GUI.displayString("We will bring you back to the main menu");

			// TODO Damien's additional feature, can be ignored for now
			 GameSettings.getFrame().setVisible(true);
		} else {
			GUI.displayString("\nCongratulations " + player.getPlayerName() + ", You have won the round!");
			GUI.displayString("Press any key to continue (type 'quit' to end game).");

			message = GUI.getCommand();
			GUI.displayString(message);
			String[] textData = message.trim().split("\\s+");
			if ((textData[0].equalsIgnoreCase("quit"))) {
				System.exit(0);
			} else {
				GUI.getFrame().dispose();
				try {
					new GUI();
					GUI.getFrame().setVisible(true);
					new Board();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Method to get basic player details
	 */
	public void getPlayerDetails() {
		// Get details for player 1
		GUI.displayString("Please enter user-name for player 1 ");
		message = GUI.getCommand();

		playerOne.setPlayerColor(EnumValues.diskColors.BLACK);
		playerOne.setPlayerName(message);
		GUI.displayString("Welcome to the game " + playerOne.getPlayerName() + ". You are using Black disks");

		// Get details for player 2
		GUI.displayString("Please enter user-name for player 2 ");
		message = GUI.getCommand();

		playerTwo.setPlayerColor(EnumValues.diskColors.WHITE);
		playerTwo.setPlayerName(message);
		GUI.displayString("Welcome to the game " + playerTwo.getPlayerName() + ". You are using White disks");
	}

	/*
	 * Player can choose what score they want to play until
	 */
	public void setGameScore() {
		GUI.displayString("Please enter a score to play to.(maximum: 15)");
		message = GUI.getCommand();
		char[] moveArray = message.toCharArray();

		if (Character.isLetter(moveArray[0]) || Integer.parseInt(message) < 1 || (Integer.parseInt(message) > 15)) {
			setGameScore();
		}

		matchScore = Integer.parseInt(message);
	}

	private int invertPipNumber(int a) {
		if (a < 0)// barAndBear
			return a;
		else if (a >= 1 || a <= 24)
			return 25 - a;
		else
			System.out.println("Error: Invalid input into invertPipNumber ~171");

		return 0;
	}

	public EnumValues.diskColors firstRoll(Dice d1, Dice d2) throws InterruptedException {

		GUI.displayString("\n Now let's see who will go first! \n");
		int dice1 = 1;
		int dice2 = 2;

		do {
			DiceAnimator.diceRollTest(0);
			dice1 = d1.rollDice();
			GUI.displayString(playerOne.getPlayerName() + " rolled a " + dice1);
			DiceAnimator.diceRollTest(1);
			dice2 = d2.rollDice();
			GUI.displayString(playerTwo.getPlayerName() + " rolled a " + dice2);
		} while (dice1 == dice2);

		if (dice2 > dice1) {
			GUI.displayString(playerTwo.getPlayerName() + " (White) rolled higher so will go first \n");
			return playerTwo.getPlayerColor();
		} else {
			GUI.displayString(playerOne.getPlayerName() + " (Black) rolled higher so will go first \n");
			return playerOne.getPlayerColor();
		}
	}

	public void move(int pipFrom, int pipTo) {

		System.out.println("ATTEMPT TO MOVE TO, FROM: " + pipFrom + " " + pipTo);

		if (!gameStart || pipTo != -3) {
			if (pipFrom != pipTo) {

				EnumValues.diskColors pipFromColor = EnumValues.diskColors.NONE;

				// This should invert the input numbers if it's black to reference the correct
				// stack
				if (currentTurnColor == EnumValues.diskColors.BLACK) {
					pipFrom = invertPipNumber(pipFrom);
					pipTo = invertPipNumber(pipTo);
				}

				// Find the pipfrom color
				if (pipFrom >= 0) {
					pipFromColor = Board.pipArray[pipFrom - 1].getColor();
				} else if (pipFrom == -3) {
					if ((currentTurnColor == EnumValues.diskColors.BLACK && !Board.blackBar.diskStack.isEmpty())
							|| (currentTurnColor == EnumValues.diskColors.WHITE
									&& !Board.whiteBar.diskStack.isEmpty())) {
						pipFromColor = currentTurnColor;
					}
				}

				// System.out.println("YEET\n1: " + pipFromColor + "\n2: " + currentTurnColor +
				// "\n3: " + moveNumber);
				System.out.println("move " + pipFrom + " " + pipTo + " triggering sucsessfully");
				try {
					moveNumber -= Move.makeMove(pipFrom, pipTo);
				} catch (InterruptedException e) {
					System.out.println("Whoops, check out GUI.actionPerformed()");
					e.printStackTrace();
				}
			} else {
				System.out.println("Invalid input: Out of moves OR trying to move from a enemy controlled pip");

			}
		} else {
			System.out.println("Both source and destination pips are the same, invalid move");
		}
	}

	public void next() throws InterruptedException {
		moveNumber = 10;

		// Swaps color to other players turn
		if (currentTurnColor == EnumValues.diskColors.BLACK) {
			GUI.displayString("\n" + playerTwo.getPlayerName() + "'s  Turn (White)");
			currentTurnColor = EnumValues.diskColors.WHITE;
			Move.switchPipNumbers(playerOne.getPlayerColor());
		} else if (currentTurnColor == EnumValues.diskColors.WHITE) {
			GUI.displayString("\n" + playerOne.getPlayerName() + "'s  Turn (Black)");
			currentTurnColor = EnumValues.diskColors.BLACK;
			Move.switchPipNumbers(playerTwo.getPlayerColor());
		} else
			System.out.println("Error: currentTurnColor is invalid ~122");

		roll();
	}

	public void roll() throws InterruptedException {
		int dice1 = 1;
		int dice2 = 2;

		DiceAnimator.diceRollTest(0);
		dice1 = Board.diceArray[0].rollDice();
		DiceAnimator.diceRollTest(1);
		dice2 = Board.diceArray[1].rollDice();
		GUI.displayString("You rolled a " + dice1 + " and a " + dice2 + "!");

	}

	public int enterMove(String command, int spacesLeft, int totalSpaces) {
		char[] moveArray = command.toCharArray();
		int strnLen = moveArray.length;
		int moveIndex = 0;

		if (strnLen > 2) {
			GUI.displayString("Invalid Move! Try again");
			return 0;
		}

		for (int i = 0; i < strnLen; i++) {
			if (Character.isLetter(moveArray[0])) {
				char temp = Character.toUpperCase(moveArray[i]);
				moveArray[i] = temp;
				moveIndex += (moveArray[i] - 65) + 26 * i;
			}
			// if input is numerical
			else {
				GUI.displayString("Invalid Move! Try again");
				return 0;
			}
		}

		if (Move.possibleMoves[moveIndex][0].equals("")) {
			GUI.displayString("Invalid Move! Try again");
			return 0;
		}

		int pipFrom = Move.convertStringCommand(Move.possibleMoves[moveIndex][0]);
		int pipTo = Move.convertStringCommand(Move.possibleMoves[moveIndex][1]);

		if (!Character.isLetter(Move.possibleMoves[moveIndex][0].toCharArray()[0]))
			pipFrom++;

		if (!Character.isLetter(Move.possibleMoves[moveIndex][1].toCharArray()[0]))
			pipTo++;

		if (pipFrom == pipTo) {
			System.out.println("PIPS ARE EQUAL " + pipFrom);
		}

		if (currentTurnColor == EnumValues.diskColors.BLACK) {
			move(Pip.invertPipNumber(pipFrom), Pip.invertPipNumber(pipTo));
		} else {
			move(pipFrom, pipTo);
		}
		return Integer.parseInt(Move.possibleMoves[moveIndex][2]);

	}
}