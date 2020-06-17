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
	
	@Override
	public void moveSpaceships() {
		// TODO add a moving algortihm
	}
	
	@Override
	public void spawnRandomSpaceships() {
		// TODO add a spawning algorithm
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
	public void gameOver() {
		//delegate to the GameBoard object
		gameBoard.gameOver();
	}
	
	@Override
	public Set<Spaceship> getSpaceships() {
		return spaceships;
	}
}
