package mallamsg_CSE271Project2;
public class Space{
	/**
	 * A very simple class that contains an x and y coordinate
	 * for a space on the chess board, and a boolean value
	 * for if the space contains a queen.
	 */
	private int spaceX;
	private int spaceY;
	private boolean hasQueen = false;
	private boolean canBeCaptured = false;
	
	public boolean getCanBeCaptured() {
		return canBeCaptured;
	}

	public void setCanBeCaptured(boolean canBeCaptured) {
		this.canBeCaptured = canBeCaptured;
	}

	public Space(int x, int y){
		this.spaceX = x;
		this.spaceY = y;
	}

	public int getSpaceX() {
		return spaceX;
	}

	public int getSpaceY() {
		return spaceY;
	}
	
	public void setQueen(boolean b){
		hasQueen = b;
	}
	
	public boolean getQueen(){
		return hasQueen;
	}
	
}//end Space class
