package mallamsg_CSE271Project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/* Samuel Mallamaci
 * CSE271 Project 2 : NQUEENS
 * Uses concepts from the N-Queens example in class.
 */
public class NQueensRunner {
	
	static int spaceWidth = 80;
	static int spaceHeight = 80;
	static QueensFrame frame;
	static final int FRAME_WIDTH = 815;
	static final int FRAME_HEIGHT = 1000;
	static Space boardArray[][] = new Space[8][8];
	static BoardComponent gameBoardComponent;
	//end static declarations
	
	@SuppressWarnings("serial")
	public static class QueensFrame extends JFrame{
		public QueensFrame(){
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setTitle("mallamsg - Queens");
			constructComponents();
			
		}//end QueensFrame constructor
		
		void constructComponents(){
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					/*
					 * Fills the board from top-to-bottom then left-to-right
					 */
					boardArray[i][j] = new Space(i * spaceWidth, j * spaceHeight);
				}
			}
			buildBoard();
			CheckButton check = new CheckButton("Check Solution");
			CheckButtonListener checkListener = new CheckButtonListener();
			check.addActionListener(checkListener);
			add(check, BorderLayout.SOUTH);
		}//end constructComponents
		
		void buildBoard(){
			/* 
			 * Handles creating the 64 grey and white
			 * spaces on the board.
			 */		
			gameBoardComponent = new BoardComponent();
			//add the mouseListener
			GameBoardMouseListener mListener = new GameBoardMouseListener();
			gameBoardComponent.addMouseListener(mListener);
			add(gameBoardComponent);	
		}//end buildBoard	
	}//end QueensFrame class
	
	@SuppressWarnings("serial")
	public static class CheckButton extends JButton{
		/**
		 * A button that checks if the current solution is correct.
		 */
		public CheckButton(String text){
			super(text);
			setPreferredSize(new Dimension(800, 160));
		}
	}//end CheckButton class
	
	private static class CheckButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					if(boardArray[i][j].getQueen()){
						checkCapture(i, j);
					}
				}
			}
			gameBoardComponent.repaint();
		}	
	}//end checkButtonListener
	
	@SuppressWarnings("serial")
	public static class BoardComponent extends JComponent{
		/**
		 * Component which draws the squares. 
		 * If the square should have a queen in it, 
		 * an imageicon is placed on top of the square in the 
		 * board component;
		 */
		boolean fillWhite = true;
		
		public void paintComponent(Graphics g){
			for(int i = 0; i < 800; i+=100){
				for(int j = 0; j < 800; j+=100){
					if(fillWhite){
						g.setColor(Color.WHITE);
					}
					else{
						g.setColor(Color.GRAY);
					}
					if(boardArray[i/100][j/100].getQueen())
					{
						g.setColor(Color.BLUE);
						if(boardArray[i/100][j/100].getCanBeCaptured()){
							g.setColor(Color.RED);
						}
					}
					g.fillRect(i, j, 100, 100);
					if(j!=700){
						fillWhite = !fillWhite;
					}
				}
			}
			fillWhite = true;	
		}
	}//end Board Component

	public static class GameBoardMouseListener implements MouseListener{
		int xPos;
		int yPos;
		@Override
		public void mouseClicked(MouseEvent e) {
			/**
			 * This listens for the mouse click
			 * gets the clicked location relative to the gameBoard
			 * translates the click location to an object in the boardArray
			 * makes the Space object's hasQueen value true or false
			 * and then repaints the gameBoardComponent
			 */
			
			xPos = e.getX()/100;
			yPos = e.getY()/100;
			
			//negate the hasQueen value
			boardArray[xPos][yPos].setQueen(!boardArray[xPos][yPos].getQueen());
			//we set the canBeCapturedValue to false for now, so that it can be properly computed by the gameBoardComponent
			//the checkButton will determine if it can be captured or not, and then address painting the gameBoardComponent properly.
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					boardArray[i][j].setCanBeCaptured(false);
				}
			}

			
			gameBoardComponent.repaint();
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//not used			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// not used			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// not used
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// not used
		}
	}//end GameBoardMouseListener
	
	public static void checkCapture(int j, int i){
		/**
		 * calls methods to check if the space is capturable by any placed queens
		 */
		boolean capturable = false;
		capturable = (checkRow(i, j)||checkColumn(i, j)||checkDiagonal(i, j));
		boardArray[i][j].setCanBeCaptured(capturable);

	}//end checkCapture method
	
	public static boolean checkRow(int i, int j){
		/**
		 * returns true if the row contains a queen that can capture the current queen
		 */
		for(int k = 0; k<8; k++){
			if(k == i){
				// so the queen does not capture itself
				k++;
			}
			if(k == 8){
				// if the whole row has been checked and nothing has been returned, return false
				return false;
			}
			if(boardArray[k][j].getQueen()){
				return true;
			}
		}
		// if nothing has been returned by the end, then there is no capture possible in the row.
		return false;
	}//end checkRow method
	
	public static boolean checkColumn(int i, int j){
		/**
		 * returns true if the column contains a queen that can capture the current queen
		 */
		for(int k = 0; k<8; k++){
			if(k == j){
				k++;
			}
			if(k == 8){
				return false;
			}
			if(boardArray[i][k].getQueen()){
				return true;
			}
		}
		return false;
	}//end checkColumn method
	
	public static boolean checkDiagonal(int i, int j){
		for(int k = i, n = j; k<8 && n<8; k++, n++){
			if(!(k==i && n==j)&&(boardArray[k][n].getQueen())){
				return true;
			}
		}
		for(int k = i, n = j; k<8 && n>=0; k++, n--){
			if(!(k==i && n==j)&&(boardArray[k][n].getQueen())){
				return true;
			}
		}
		for(int k = i, n = j; k>=0 && n<8; k--, n++){
			if(!(k==i && n==j)&&(boardArray[k][n].getQueen())){
				return true;
			}
		}
		for(int k = i, n = j; k>=0 && n>=0; k--, n--){
			if(!(k==i && n==j)&&(boardArray[k][n].getQueen())){
				return true;
			}
		}
		return false;
	}// end checkDiagonal

	public static void main(String[] args) {
		frame = new QueensFrame();
		frame.setVisible(true);
	}//end main
}