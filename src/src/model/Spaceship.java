package src.src.model;

public class Spaceship {
	
	//adjust the spaceship image size to these values
	//but feel free to change them if necessary (use these constants, don't define new ones)
	public static final int spaceshipWidth = 50;
	public static final int spaceshipHeight = 50;
	
	private int positionX, positionY;
	private boolean alive;
	private final GameObjectFacade gameBoard;
	
	/**
	 * Sets up a new Spaceship at the top of the GUI at the specified x-coordinate
	 * @param gameBoard the GameObjectFacade instance to communicate with
	 * @param posX the initial x-coordinate of the spaceship
	 */
	public Spaceship(GameObjectFacade gameBoard, int posX) {
		this.gameBoard = gameBoard;
		this.alive = true;
		this.positionX = posX;
		this.positionY = gameBoard.getGUIHeight() - (spaceshipHeight + 10);
	}
	
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
		positionX = (positionX + deltaX) % (gameBoard.getGUIWidth() - spaceshipWidth);
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
