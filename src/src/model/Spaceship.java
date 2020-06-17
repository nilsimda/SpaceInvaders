package src.model;

import src.controller.GameBoard;

public class Spaceship {
	
	//change to a suitable value for the GUI scale if necessary
	public static final int spaceshipWidth = 50;
	
	private int positionX, positionY;
	private boolean alive;
	private GameBoard gameBoard;
	
	/**
	 * Sets the alive attribute of the spaceship to false, 
	 * which makes it being removed from the gameboard's spaceship list soon.
	 */
	public void destroy() {
		this.alive = false;
	}
	
	/**
	 * Moves the spaceship by the given delta values. If it moves out on one side it comes back in on the other one. 
	 * This method also checks if a spaceship has hit the ground and if so, sets the GameBoard.GAME_OVER flag.
	 * @param deltaX the value to be moved on the x-axis. Positive values mean movements to right and vice versa.
	 * @param deltaY the value to be moved on the y-axis. Positive values mean movements downwards and vice versa,
	 */
	public void move(int deltaX, int deltaY) {
		positionX = (positionX + deltaX) % GameBoard.GUI_WIDTH;
		positionY = (positionY + deltaY);
		if(this.positionY <= 0) {
			//the spaceship has hit the ground
			this.gameBoard.gameOver();
		}
	}
	
	//getter methods
	public int getX() {
		return positionX;
	}
	public int getY() {
		return positionY;
	}
	public boolean getAlive() {
		return alive;
	}
}
