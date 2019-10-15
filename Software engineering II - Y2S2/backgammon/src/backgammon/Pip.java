package backgammon;

import java.util.Stack;

public class Pip {

	private int numDisks;
	private int baseXPos;
	private int baseYPos;
	private EnumValues.quadrant quadrant;
	Stack<Disk> diskStack;

	public Pip(int baseXPos, int baseYPos, EnumValues.quadrant quadrant) {
		this.baseXPos = baseXPos;
		this.baseYPos = baseYPos;
		this.quadrant = quadrant;
		diskStack = new Stack<Disk>();
	}

	public EnumValues.diskColors getColor() {
		if (this.stackSize() == 0)
			return EnumValues.diskColors.NONE;
		else
			return diskStack.peek().getDiskColor();
	}

	public void setNumDisks(int numDisks) {
		this.numDisks = numDisks;
	}

	public int stackSize() {
		return this.diskStack.size();
	}

	public int getNumDisks() {
		return this.numDisks;
	}

	public int getBaseYPos() {
		return this.baseYPos;
	}

	public int getBaseXPos() {
		return this.baseXPos;
	}

	public EnumValues.quadrant getQuadrant() {
		return quadrant;
	}

	public static int invertPipNumber(int a) {
		if (a < 0)// barAndBear
			return a;
		else if (a >= 1 || a <= 24)
			return 25 - a;
		else
			System.out.println("Error: Invalid input into Pip.invertPipNumber ~171");

		return 0;
	}

	// ONLY FOR USE WITH BAR
	public boolean haveColor(EnumValues.diskColors inputColor) {
		boolean out = false;

		Stack<Disk> tempStack = new Stack<Disk>();
		while (!out && !this.diskStack.isEmpty()) {
			if (this.diskStack.peek().getDiskColor() == inputColor)
				out = true;

			tempStack.push(this.diskStack.pop());
		}

		while (!tempStack.isEmpty())
			this.diskStack.push(tempStack.pop());

		return out;
	}

}
