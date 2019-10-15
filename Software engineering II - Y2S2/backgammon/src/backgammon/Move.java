package backgammon;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

public class Move {

    // Standard space between disks
    private final static int DISKSPACING = 50;

    // image of disk
    public static JLabel diskImg;

    // public static EnumValues.diskColors currentPlayerTurn;
    public static boolean blackBearingOff;
    public static boolean whiteBearingOff;

    // Array of possible moves
    // Stored in the form "x y", has to be a string to be able to accept "bar, black
    // or white"
    // Stores the array coordinates
    public static String[][] possibleMoves = new String[100][3];

    // Number to keep track of how many possible moves have been listed
    static int commandNumber;

    public Move() {
    }

    // -1 black zone -2 white zone -3 bar
    public static int makeMove(int pipFrom, int pipTo) throws InterruptedException {
        System.out.println("\nYEET ~65\nmakeMove input\npipTo = " + pipTo + "\npipFrom = " + pipFrom + "\n");
        int successful = 0;
        if (-4 == pipFrom || -4 == pipTo)
            return 0;

        if (pipFrom < 0 || pipTo < 0) {// Involving barAndBear class
            if (pipFrom > 0) {// bearOff()
                if (pipTo == -1) {
                    if (pipFrom < 7) {
                        bearOff(pipFrom - 1, EnumValues.diskColors.BLACK);
                        successful = 1;
                    } else {
                        System.out.println("NOT IN RANGE FOR BEARING OFF ~46");
                    }
                } else if (pipTo == -2) {
                    if (pipFrom > 18) {
                        bearOff(pipFrom - 1, EnumValues.diskColors.WHITE);
                        successful = 1;
                    } else {
                        System.out.println("NOT IN RANGE FOR BEARING OFF ~53");
                    }
                } else if (pipTo == -3) {
                    bearOff(pipFrom - 1, EnumValues.diskColors.NONE);
                    successful = 1;
                } else {
                    System.out.println("ERROR IN MAKE MOVE 306");
                }
            } else if (pipTo > 0) {// fromBar()
                fromBar(pipTo - 1, Game.currentTurnColor);
                successful = 1;
            }
        } else {// move from pip to pip
            if (Board.pipArray[pipTo - 1].stackSize() > 1
                    && Board.pipArray[pipTo - 1].getColor() != Board.pipArray[pipFrom - 1].getColor()) {
                System.out.println("INVALID MOVE, ENEMY OCCUPATION ~313");
            } else if (Board.pipArray[pipTo - 1].stackSize() == 1
                    && Board.pipArray[pipTo - 1].getColor() != Board.pipArray[pipFrom - 1].getColor()) {
                // Squash enemy disk
                bearOff(pipTo - 1, EnumValues.diskColors.NONE);
                moveDiskPipToPip(pipFrom - 1, pipTo - 1);
                successful = 1;
            } else if (Board.pipArray[pipTo - 1].stackSize() == 0
                    || Board.pipArray[pipTo - 1].getColor() == Board.pipArray[pipFrom - 1].getColor()) {
                // move to empty pip OR pip of same color
                moveDiskPipToPip(pipFrom - 1, pipTo - 1);
                successful = 1;
            } else {
                System.out.println("UHHH? ERROR IN GUI.MAKEMOVE ~322");
            }
        }
        return successful;
    }

    public static void moveDiskPipToPip(int pipFrom, int pipTo) throws InterruptedException {
        if (Board.pipArray[pipFrom].stackSize() == 0) {
            System.out.println("EMPTY PIP; INVALID MOVE");
        } else {
            Disk tempDisk = Board.pipArray[pipFrom].diskStack.pop();
            diskImg = tempDisk.getImage();

            int xPos = Board.pipArray[pipTo].getBaseXPos();
            int yPos = Board.pipArray[pipTo].getBaseYPos();

            if (Board.pipArray[pipTo].getQuadrant() == EnumValues.quadrant.FIRST
                    || Board.pipArray[pipTo].getQuadrant() == EnumValues.quadrant.SECOND) {
                yPos -= Board.pipArray[pipTo].stackSize() * DISKSPACING;
            } else {
                yPos += Board.pipArray[pipTo].stackSize() * DISKSPACING;
            }

            animateMove(xPos, yPos, diskImg);

            Board.pipArray[pipTo].diskStack.push(tempDisk);
            diskImg.setBounds(xPos, yPos, 100, 100);
            diskImg.repaint();
            GUI.redrawPip(Board.pipArray[pipFrom].diskStack);
            GUI.redrawPip(Board.pipArray[pipTo].diskStack);
        }
    }

    // NONE zone color will move to bar
    public static void bearOff(int pipFrom, EnumValues.diskColors zoneColor) throws InterruptedException {
        if (Board.pipArray[pipFrom].stackSize() == 0) {
            System.out.println("EMPTY PIP; INVALID MOVE");
        } else {
            Disk tempDisk = Board.pipArray[pipFrom].diskStack.pop();
            diskImg = tempDisk.getImage();

            int xPos;
            int yPos;

            if (zoneColor == EnumValues.diskColors.BLACK) {
                xPos = Board.blackBearZone.getPosX();
                yPos = Board.blackBearZone.getBottomPosY();
                yPos -= Board.blackBearZone.stackSize() * DISKSPACING;
                Board.blackBearZone.diskStack.push(tempDisk);
            } else if (zoneColor == EnumValues.diskColors.WHITE) {
                xPos = Board.whiteBearZone.getPosX();
                yPos = Board.whiteBearZone.getBottomPosY();
                yPos -= Board.whiteBearZone.stackSize() * DISKSPACING;
                Board.whiteBearZone.diskStack.push(tempDisk);
            } else if (zoneColor == EnumValues.diskColors.NONE) {
                if (tempDisk.getDiskColor() == EnumValues.diskColors.BLACK) {// if black
                    xPos = Board.blackBar.getPosX();
                    yPos = Board.blackBar.getBottomPosY();
                    yPos -= Board.blackBar.stackSize() * DISKSPACING;
                    Board.blackBar.diskStack.push(tempDisk);
                } else if (tempDisk.getDiskColor() == EnumValues.diskColors.WHITE) {// if white
                    xPos = Board.whiteBar.getPosX();
                    yPos = Board.whiteBar.getBottomPosY();
                    yPos -= Board.whiteBar.stackSize() * DISKSPACING;
                    Board.whiteBar.diskStack.push(tempDisk);
                } else {
                    xPos = 0;
                    yPos = 0;
                    System.out.println("Error in Move.bearOff() ~191");
                }
            } else {
                System.out.println("INVALID COLOR");
                return;
            }

            animateMove(xPos, yPos, diskImg);

            diskImg.setBounds(xPos, yPos, 100, 100);
            diskImg.repaint();

            GUI.redrawPip(Board.blackBearZone.diskStack);
            GUI.redrawPip(Board.blackBar.diskStack);
            GUI.redrawPip(Board.whiteBar.diskStack);
            GUI.redrawPip(Board.whiteBearZone.diskStack);
            GUI.redrawPip(Board.pipArray[pipFrom].diskStack);
        }
    }

    public static void fromBar(int pipTo, EnumValues.diskColors barFrom) throws InterruptedException {
        if ((Board.blackBar.stackSize() == 0 && barFrom == EnumValues.diskColors.BLACK)
                || (Board.whiteBar.stackSize() == 0 && barFrom == EnumValues.diskColors.WHITE)) {
            System.out.println("Invalid move, no pips of your color on the bar ~217");
        } else {
            if (Board.pipArray[pipTo].diskStack.size() < 2 || Board.pipArray[pipTo].haveColor(barFrom)) {
                // Check if the pipTo is ok to be moved to

                if (Board.pipArray[pipTo].diskStack.size() == 1 && !Board.pipArray[pipTo].haveColor(barFrom)) {
                    // Pops off disk if necessary

                    bearOff(pipTo, EnumValues.diskColors.NONE);

                }

                Disk tempDisk;
                if (barFrom == EnumValues.diskColors.BLACK) {
                    tempDisk = Board.blackBar.diskStack.pop();
                } else {
                    tempDisk = Board.whiteBar.diskStack.pop();
                }
                diskImg = tempDisk.getImage();

                int xPos = Board.pipArray[pipTo].getBaseXPos();
                int yPos = Board.pipArray[pipTo].getBaseYPos();

                if (Board.pipArray[pipTo].getQuadrant() == EnumValues.quadrant.FIRST
                        || Board.pipArray[pipTo].getQuadrant() == EnumValues.quadrant.SECOND) {
                    yPos -= Board.pipArray[pipTo].stackSize() * DISKSPACING;
                } else {
                    yPos += Board.pipArray[pipTo].stackSize() * DISKSPACING;
                }

                animateMove(xPos, yPos, diskImg);

                Board.pipArray[pipTo].diskStack.push(tempDisk);
                diskImg.setBounds(xPos, yPos, 100, 100);
                diskImg.repaint();

                GUI.redrawPip(Board.pipArray[pipTo].diskStack);
                if (barFrom == EnumValues.diskColors.BLACK) {
                    GUI.redrawPip(Board.blackBar.diskStack);
                } else {
                    GUI.redrawPip(Board.whiteBar.diskStack);
                }
            } else {
                System.out.println("Pip occupied by enemy move ~251");
            }
        }
    }

    // Animates moving a jlable to (toX, toY)
    private static void animateMove(int toX, int toY, JLabel image) throws InterruptedException {

        final int change = 10;
        int fromX = image.getX();
        int fromY = image.getY();
        int xInc = (toX - fromX) / change;
        int yInc = (toY - fromY) / change;

        System.out.println(change);

        for (int i = 0; i < change; i++) {
            fromX += xInc;
            fromY += yInc;

            diskImg.setBounds(fromX, fromY, 100, 100);
            diskImg.repaint();
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    /**
     * Method to switch pip numbers for each player
     */
    public static void switchPipNumbers(EnumValues.diskColors playerColor) {

        if (playerColor == EnumValues.diskColors.BLACK) {
            Board.pipNumberArray[0].setBounds(466, 446, 500, 500);
            Board.pipNumberArray[1].setBounds(-9, 446, 500, 500);
            Board.pipNumberArray[2].setBounds(-7, -172, 500, 500);
            Board.pipNumberArray[3].setBounds(468, -172, 500, 500);
        } else {
            Board.pipNumberArray[0].setBounds(468, -172, 500, 500);
            Board.pipNumberArray[1].setBounds(-7, -172, 500, 500);
            Board.pipNumberArray[2].setBounds(-9, 446, 500, 500);
            Board.pipNumberArray[3].setBounds(466, 446, 500, 500);
        }

        for (int i = 0; i < 4; i++) {
            Board.pipNumberArray[i].repaint();
        }
    }

    public static void printValidMoves(EnumValues.diskColors currentPlayer, int d1, int d2) {

        if (d1 == 0 && d2 == 0)
            return;

        commandNumber = 0;

        // Clearing the possibleMoves array
        for (int i = 0; i < 100; i++) {
            possibleMoves[i][0] = "";
            possibleMoves[i][1] = "";
            possibleMoves[i][2] = "";
        }

        int[] inputArray = {d1, d2, d1 + d2};

        if (d1 == 0 || d2 == 0)
            inputArray[2] = 0;

        // Black goes down the array so this inverts all moves to go down the array
        if (currentPlayer == EnumValues.diskColors.BLACK) {
            for (int i = 0; i < 3; i++)
                inputArray[i] *= -1;
        }

        for (int i = 0; i < 3; i++)
            System.out.println("YEET inputArray[i] = " + inputArray[i]);

        boolean hasBarMoves = false;

        // Move off bar
        String hitmarker;
        if (currentPlayer == EnumValues.diskColors.WHITE) {
            if (!Board.whiteBar.diskStack.isEmpty()) {
                System.out.println("YEET: WHITE BAR HAS SOMEtHING");
                hasBarMoves = true;
                for (int i = 0; i < 3; i++) {
                    if (inputArray[i] != 0) {
                        hitmarker = "";
                        if (Board.pipArray[inputArray[i] - 1].getColor() == currentPlayer
                                || Board.pipArray[inputArray[i] - 1].stackSize() < 2) {
                            if (Board.pipArray[inputArray[i] - 1].getColor() == EnumValues.diskColors.BLACK)
                                hitmarker = "*";
                            GUI.displayString(getCommandString(commandNumber) + ": Move " + "bar-" + inputArray[i]
                                    + hitmarker + "   (" + inputArray[i] + " spaces)");

                            System.out.println(getCommandString(commandNumber) + ": Move " + "bar-" + inputArray[i]
                                    + hitmarker + "   (" + inputArray[i] + " spaces)");

                            // Storing the move in the possibleMoves array
                            possibleMoves[commandNumber][0] = "bar";
                            possibleMoves[commandNumber][1] = Integer.toString(inputArray[i] - 1);
                            possibleMoves[commandNumber][2] = Integer.toString(inputArray[i]);
                            commandNumber++;
                        }
                    }
                }
            }
        } else if (currentPlayer == EnumValues.diskColors.BLACK) {
            if (!Board.blackBar.diskStack.isEmpty()) {
                System.out.println("YEET: Blakc BAR HAS SOMEtHING");
                hasBarMoves = true;
                for (int i = 0; i < 3; i++) {
                    if (inputArray[i] != 0) {
                        hitmarker = "";
                        if (Board.pipArray[24 + inputArray[i]].getColor() == currentPlayer
                                || Board.pipArray[24 + inputArray[i]].stackSize() < 2) {
                            if (Board.pipArray[24 + inputArray[i]].getColor() == EnumValues.diskColors.WHITE)
                                hitmarker = "*";
                            GUI.displayString(
                                    getCommandString(commandNumber) + ": Move " + "bar-" + Math.abs(inputArray[i])
                                            + hitmarker + "   (" + Math.abs(inputArray[i]) + " spaces)");

                            System.out.println(
                                    getCommandString(commandNumber) + ": Move " + "bar-" + Math.abs(inputArray[i])
                                            + hitmarker + "   (" + Math.abs(inputArray[i]) + " spaces)");

                            // Storing the move in the possibleMoves array
                            possibleMoves[commandNumber][0] = "bar ";
                            possibleMoves[commandNumber][1] = Integer.toString(24 + inputArray[i]);
                            possibleMoves[commandNumber][2] = Integer.toString(inputArray[i]);

                            System.out.println(
                                    "YEET\npossibleMoves[commandNumber][0] = " + possibleMoves[commandNumber][0]
                                            + "\npossibleMoves[commandNumber][1] = " + possibleMoves[commandNumber][1]
                                            + "\npossibleMoves[commandNumber][2] = " + possibleMoves[commandNumber][2]);

                            commandNumber++;
                        }
                    }
                }
            }
        }

        // TODO: Fix bug where you can use both dice to bear off, where using 1 is possible
        // Tests all moves with all pips
        if (!hasBarMoves) {
            for (int j = 0; j < 3; j++) {// Goes through all moves
                if (inputArray[j] != 0) {// Only bothers testing if there is a dice in that slot
                    for (int i = 0; i < 24; i++) {// Goes through all pips
                        printSingleValidMove(currentPlayer, i, inputArray[j]);
                    }
                } else {
                    System.out.println("Value zero dice ~279");
                }
            }
        }
        printPossibleMovesArray();
    }

    public static void printValidDoubleMoves(EnumValues.diskColors currentPlayer, int dice, int numberOfDice) {
        System.out.println("BEFORE if. current player: " + currentPlayer + " dice : " + dice);
        // if (dice == 0 || numberOfDice == 0)
        // return;

        // Black goes down the array
        if (currentPlayer == EnumValues.diskColors.BLACK) {
            dice *= -1;
        }

        commandNumber = 0;

        // Clearing the possibleMoves array
        for (int i = 0; i < 100; i++) {
            possibleMoves[i][0] = "";
            possibleMoves[i][1] = "";
            possibleMoves[i][2] = "";
        }

        boolean hasBarMoves = false;

        // Move off bar
        if (currentPlayer == EnumValues.diskColors.WHITE) {
            if (!Board.whiteBar.diskStack.isEmpty()) {
                hasBarMoves = true;
                moveOffBarDoubles(EnumValues.diskColors.WHITE, dice);
            }
        } else if (currentPlayer == EnumValues.diskColors.BLACK) {
            if (!Board.blackBar.diskStack.isEmpty()) {//if black bar is not empty
                hasBarMoves = true;
                moveOffBarDoubles(EnumValues.diskColors.BLACK, dice);
            }
        }

        // Standard pipToPip movement
        if (!hasBarMoves) {
            for (int i = 0; i < 24; i++) {
                for (int j = 1; j <= numberOfDice; j++) {
                    if (!(i + (dice * j) >= 24 || i + (dice * j) <= 0) && isValidMovePTP(i, i + (dice * j))) {
                        // Command for a valid pipToPip movement
                        printSingleValidMove(currentPlayer, i, (dice * j));
                    }
                }
            }
        }

    }

    /*
     * Method for moving disks off of a bar
     */
    public static void moveOffBarDoubles(EnumValues.diskColors currentPlayer, int dice) {
        String hitmarker;
        int destination, move;


        if (currentPlayer == EnumValues.diskColors.WHITE) {
            for (int i = 0; i < 4; i++) {
                hitmarker = "";

                //Move being made
                move = dice * (i + 1);

                //Positon in pip array being moved too
                destination = move - 1;

                if (Board.pipArray[destination].getColor() == currentPlayer
                        || Board.pipArray[destination].stackSize() < 2) {

                    // Off bar doubles for white
                    if (Board.pipArray[destination].getColor() == EnumValues.diskColors.BLACK)
                        hitmarker = "*";
                    GUI.displayString(getCommandString(commandNumber) + ": Move " + "bar-" + (destination + 1)
                            + hitmarker + "   (" + move + " spaces)");

                    System.out.println(getCommandString(commandNumber) + ": Move " + "bar, " + (destination + 1) + "   ("
                            + (move) + " spaces)");

                    // Storing the move in the possibleMoves array
                    possibleMoves[commandNumber][0] = "bar";
                    possibleMoves[commandNumber][1] = Integer.toString(destination);
                    possibleMoves[commandNumber][2] = Integer.toString(move);

                    commandNumber++;
                }
            }
        } else if (currentPlayer == EnumValues.diskColors.BLACK) {
            for (int i = 0; i < 4; i++) {
                hitmarker = "";

                //Move being made
                move = dice * (i + 1);

                //Positon in pip array being moved too
                destination = 24 + move;

                if (Board.pipArray[destination].getColor() == currentPlayer
                        || Board.pipArray[destination].stackSize() < 2) {

                    // Off bar doubles for white
                    if (Board.pipArray[destination].getColor() == EnumValues.diskColors.WHITE)
                        hitmarker = "*";
                    GUI.displayString(getCommandString(commandNumber) + ": Move " + "bar-" + (destination + 1)
                            + hitmarker + "   (" + Math.abs(move) + " spaces)");

                    System.out.println(getCommandString(commandNumber) + ": Move " + "bar, " + (destination + 1) + "   ("
                            + Math.abs(move) + " spaces)");

                    // Storing the move in the possibleMoves array
                    possibleMoves[commandNumber][0] = "bar";
                    possibleMoves[commandNumber][1] = Integer.toString(destination);
                    possibleMoves[commandNumber][2] = Integer.toString(move);

                    commandNumber++;
                }

            }
        }
    }

    // PTP = Pip to Pip
    // Input array position
    private static boolean isValidMovePTP(int from, int to) {
        if (from >= 0 && from <= 23 && to >= 0 && to <= 23)
            return Board.pipArray[from].stackSize() > 0
                    && (Board.pipArray[from].getColor() == Board.pipArray[to].getColor()
                    || Board.pipArray[to].stackSize() < 2);
        else
            return false;
    }

    private static void printSingleValidMove(EnumValues.diskColors currentPlayer, int pipFrom, int move) {

        // System.out.println("YEET: SINGLEVALIDMOVECOLOR : " + currentPlayer +
        // "\npipFrom: " + pipFrom + "\nmove: " + move);

        if (Board.pipArray[pipFrom].getColor() == currentPlayer) {
            // To see if we are moving from a player controlled pip
            reevaluateBearingOff();
            if ((pipFrom + move <= 23 && pipFrom + move >= 0)) {
                // Command for a valid pipToPip movement

                if (isValidMovePTP(pipFrom, pipFrom + move)) {
                    // To see that it is a valid move
                    if (!checkDuplicate(pipFrom, pipFrom + move)) {

                        if (currentPlayer == EnumValues.diskColors.BLACK) {
                            GUI.displayString(getCommandString(commandNumber) + ": Move "
                                    + (Pip.invertPipNumber(pipFrom + 1)) + ", "
                                    + (Pip.invertPipNumber(pipFrom + move + 1)) + "   (" + Math.abs(move) + " spaces)");

                            System.out.println(getCommandString(commandNumber) + ": Move "
                                    + (Pip.invertPipNumber(pipFrom + 1)) + ", "
                                    + (Pip.invertPipNumber(pipFrom + move + 1)) + "   (" + Math.abs(move) + " spaces)");

                            // Storing the move in the possibleMoves arrays
                            possibleMoves[commandNumber][0] = Integer.toString(pipFrom);
                            possibleMoves[commandNumber][1] = Integer.toString(pipFrom + move);
                            possibleMoves[commandNumber][2] = Integer.toString(move);
                        } else if (currentPlayer == EnumValues.diskColors.WHITE) {
                            GUI.displayString(getCommandString(commandNumber) + ": Move " + (pipFrom + 1) + ", "
                                    + (pipFrom + move + 1) + "   (" + Math.abs(move) + " spaces)");

                            System.out.println(getCommandString(commandNumber) + ": Move " + (pipFrom + 1) + ", "
                                    + (pipFrom + move + 1) + "   (" + Math.abs(move) + " spaces)");

                            // Storing the move in the possibleMoves array
                            possibleMoves[commandNumber][0] = Integer.toString(pipFrom);
                            possibleMoves[commandNumber][1] = Integer.toString(pipFrom + move);
                            possibleMoves[commandNumber][2] = Integer.toString(move);
                        } else {
                            System.out.println("INVALID currentPlayer input in printValidSingleMove ~362");
                            commandNumber--;
                        }
                        commandNumber++;
                    }
                }
            } else if (currentPlayer == EnumValues.diskColors.BLACK && (pipFrom + move) < 0 && blackBearingOff) {
                // Bear off for black
                GUI.displayString(getCommandString(commandNumber) + ": Move " + (1 + Pip.invertPipNumber(pipFrom))
                        + ", Black Bearing zone" + "   (" + Math.abs(move) + " spaces)");

                System.out.println(getCommandString(commandNumber) + ": Move " + (1 + Pip.invertPipNumber(pipFrom))
                        + ", Black Bearing zone" + "   (" + Math.abs(move) + " spaces)");

                // Storing the move in the possibleMoves array
                possibleMoves[commandNumber][0] = Integer.toString(pipFrom);
                possibleMoves[commandNumber][1] = "black";
                possibleMoves[commandNumber][2] = Integer.toString(move);

                commandNumber++;
            } else if (currentPlayer == EnumValues.diskColors.WHITE && (pipFrom + move) > 23 && whiteBearingOff) {
                // Bear off for white
                GUI.displayString(getCommandString(commandNumber) + ": Move " + (pipFrom + 1) + ", White Bearing zone"
                        + "   (" + Math.abs(move) + " spaces)");

                System.out.println(getCommandString(commandNumber) + ": Move " + (pipFrom + 1) + ", White Bearing zone"
                        + "   (" + Math.abs(move) + " spaces)");

                // Storing the move in the possibleMoves array
                possibleMoves[commandNumber][0] = Integer.toString(pipFrom);
                possibleMoves[commandNumber][1] = "white";
                possibleMoves[commandNumber][2] = Integer.toString(move);

                commandNumber++;
            } else {
                // System.out.println("Invalid Move ~299\n");
            }
        }

    }

    // Takes an integer input as to which command this is, and outputs a string of
    // up to 2 letters
    public static String getCommandString(int input) {
        String output = "";
        char temp = 'A';

        if (input <= 25) {
            temp += input;
            output += temp;
        } else if (input > 25) {
            temp += (input / 26) - 1;
            output += temp;

            temp = 'A';
            temp += input % 26;
            output += temp;
        } else {
            System.out.println("Number too high in Move.getCommandLetter ~396");
        }

        return output;
    }

    public static int convertStringCommand(String inputLocation) {
        System.out.println(inputLocation);
        String locationIn = inputLocation.trim();
        if (locationIn.equalsIgnoreCase("g") || locationIn.equalsIgnoreCase("black"))
            return -1;
        else if (locationIn.equalsIgnoreCase("w") || locationIn.equalsIgnoreCase("white"))
            return -2;
        else if (locationIn.equalsIgnoreCase("b") || locationIn.equalsIgnoreCase("bar"))
            return -3;
        else {
            try {
                int a = Integer.parseInt(locationIn);
                if (a >= 0 && a < 25)
                    return a;
                else {
                    System.out.println("Not a valid input");
                    return -4;
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
                e.printStackTrace();
                return -4;
            }
        }
    }

    private static void reevaluateBearingOff() {
        whiteBearingOff = true;
        blackBearingOff = true;

        // Checks to see if any disks remain outside the respective bearing off zones
        for (int i = 0; i < 24; i++) {
            if (i > 5 && Board.pipArray[i].getColor() == EnumValues.diskColors.BLACK) {
                blackBearingOff = false;
            }

            if (i < 18 && Board.pipArray[i].getColor() == EnumValues.diskColors.WHITE) {
                whiteBearingOff = false;
            }
        }

        // Check to see if the bar has any disks of a given color
        if (!Board.blackBar.diskStack.isEmpty())
            blackBearingOff = false;

        if (!Board.whiteBar.diskStack.isEmpty())
            whiteBearingOff = false;
    }

    public static void printPossibleMovesArray() {
        System.out.println("Possible moves array\n");
        for (int i = 0; i < 100; i++) {
            if (possibleMoves[i][0] != "" || possibleMoves[i][0] != "")
                System.out.println(getCommandString(i) + ": " + possibleMoves[i][0] + " " + possibleMoves[i][1] + " "
                        + possibleMoves[i][2]);
        }
    }

    public static void redrawPip(Stack<Disk> inputStack) {
        if (!inputStack.isEmpty()) {
            Stack<Disk> tempStack = new Stack<Disk>();
            if (inputStack.size() >= 6) {// Compress pip
                while (!inputStack.isEmpty())
                    tempStack.push(inputStack.pop());

                int xPos = tempStack.peek().getImage().getX();
                int baseYPos = tempStack.peek().getImage().getY();

                inputStack.push(tempStack.pop());

                int topYPos;
                if (baseYPos < tempStack.peek().getImage().getY()) {
                    topYPos = baseYPos + 250;
                } else {
                    topYPos = baseYPos - 250;
                }

                int increment = (topYPos - baseYPos) / (tempStack.size() + 1);
                baseYPos += increment;

                while (!tempStack.isEmpty()) {
                    inputStack.push(tempStack.pop());
                    inputStack.peek().getImage().setBounds(xPos, baseYPos, 100, 100);
                    inputStack.peek().getImage().repaint();
                    baseYPos += increment;
                }
            } else {// Draw pip standard method
                while (!inputStack.isEmpty())
                    tempStack.push(inputStack.pop());

                int xPos = tempStack.peek().getImage().getX();
                int baseYPos = tempStack.peek().getImage().getY();

                inputStack.push(tempStack.pop());

                int increment = 50;

                if (!tempStack.isEmpty() && baseYPos > tempStack.peek().getImage().getY()) {
                    increment *= -1;
                }

                baseYPos += increment;

                while (!tempStack.isEmpty()) {
                    inputStack.push(tempStack.pop());
                    inputStack.peek().getImage().setBounds(xPos, baseYPos, 100, 100);
                    inputStack.peek().getImage().repaint();
                    baseYPos += increment;
                }
            }
        } else {
            System.out.println("Attempting to redraw empty stack ~148");
        }
    }

    public static boolean checkDuplicate(int x, int y) {
        int i = 0;
        while (possibleMoves[i][0] != "") {
            if (!Character.isLetter(possibleMoves[i][0].toCharArray()[0])
                    && !Character.isLetter(possibleMoves[i][1].toCharArray()[0])) {
                if (Integer.parseInt(possibleMoves[i][0]) == x && Integer.parseInt(possibleMoves[i][1]) == y) {
                    System.out.println("DUPLICATE");
                    return true;
                }
            }
            i++;
        }
        return false;
    }
}