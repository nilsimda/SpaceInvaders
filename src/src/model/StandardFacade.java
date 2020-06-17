package src.model;

import src.controller.GameBoard;

import java.util.Set;
import java.util.HashSet;

public class StandardFacade implements GameObjectFacade {
	
	private final GameBoard gameBoard;
	private Cannon cannon;
	private Set<Spaceship> spaceships;
	
	/**
	 * This constructor sets up all the game objects (cannon and spaceships) that are necessary to play the game. 
	 * It Spawns the cannon on the left side of the game board and adds some initial spaceships 
	 * by calling spawnRandomSpaceships().
	 * @param gameBoard the game board to be communicated with
	 */
	public StandardFacade(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		this.cannon = new Cannon(this);
		this.spaceships = new HashSet<>();
		//add some initial spaceships
		spawnRandomSpaceships();
	}
	
	
	/**
	 * This method simply moved all spaceships one pixel closer to the ground
	 */
	@Override
	public void moveSpaceships() {
		//simple moving algorithm that just moves all the spaceships one pixel closer to the ground
		for(Spaceship s : spaceships) {
			s.move(0, -1);
		}
	}
	
	/**
	 * Spawns a random number (between 1 and 5, inclusive) of Spaceships on the top of the screen at random locations
	 */
	@Override
	public void spawnRandomSpaceships() {
		int numberOfSpaceships = 1 + (int)(Math.random() * 4);
		int coordinate = 0;
		for(int i=0; i<numberOfSpaceships; i++) {
			coordinate = coordinate +						//spawn the spaceship left of the previous ones
					(int)(Math.random() *					//in a random location
					(GameBoard.GUI_WIDTH - Spaceship.spaceshipWidth - coordinate) /
					(numberOfSpaceships - i));				//so that there is enoug space for the other ones
			spaceships.add(new Spaceship(this, coordinate));
		}
	}
	
	@Override
	public void steerCannon(int deltaX) {
		//delegate the method call to the Cannon object
		cannon.steer(deltaX);
	}
	
	@Override
	public void fireCannon() {
		//delegate the method call to the Cannon object
		cannon.fire();
	}

	@Override
	public int getGUIWidth() {
		return GameBoard.GUI_WIDTH;
	}
	
	@Override
	public int getGUIHeight() {
		return GameBoard.GUI_HEIGHT;
	}
	
	@Override
	public int getSpaceshipWidth() {
		return Spaceship.spaceshipWidth;
	}
	
	@Override
	public int getSpaceshipHeight() {
		return Spaceship.spaceshipHeight;
	}
	
	@Override
	public void gameOver() {
		//delegate to the GameBoard object
		gameBoard.gameOver();
	}
	
	@Override
	public Set<Spaceship> getSpaceships() {
		return spaceships;
	}
}
