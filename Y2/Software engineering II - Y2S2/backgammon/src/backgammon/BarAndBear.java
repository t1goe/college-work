package backgammon;

import java.util.Stack;

public class BarAndBear {

	private int posX;
	private int topPosY;
	private int bottomPosY;

	private int numOfDisks;

	Stack<Disk> diskStack;

	public BarAndBear(int posX, int topPosY, int bottomPosY) {
		this.posX = posX;
		this.topPosY = topPosY;
		this.bottomPosY = bottomPosY;
		this.numOfDisks = 0;
		diskStack = new Stack<Disk>();
	}

	public boolean hasColor(EnumValues.diskColors testColor) {
		boolean answer = false;
		Stack<Disk> tempStack = new Stack<Disk>();
		while (!diskStack.isEmpty()) {
			if (diskStack.peek().getDiskColor() != testColor) {
				answer = true;
				break;
			}
			tempStack.push(diskStack.pop());
		}

		while (!tempStack.isEmpty()) {
			diskStack.push(tempStack.pop());
		}

		return answer;
	}

	public int size() {
		return this.numOfDisks;
	}

	public int stackSize() {
		return this.diskStack.size();
	}

	public int getPosX() {
		return posX;
	}

	public int getTopPosY() {
		return topPosY;
	}

	public int getBottomPosY() {
		return bottomPosY;
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
