package src.model;

import java.util.Set;
import java.util.stream.Stream;
import java.time.LocalDateTime;

public interface GameObjectFacade {
	
	//methods to be invoked by the gameboard to access the game objects
	/**
	 * Moves all Spaceships by one "step" meaning especially that they come closer to the ground
	 */
	public void moveSpaceships();
	
	/**
	 * Spawns a random number of Spaceships on the top of the screen at random locations
	 */
	public void spawnRandomSpaceships();
	
	/**
	 * Moves the cannon by the specified delta value. If the cannon moves out of the field, 
	 * it comes back in on the other side.
	 * @param deltaX Positive values correspond to movements to the right,
	 * negative ones to movements to the left.
	 */
	public void steerCannon(int deltaX);
	
	/**
	 * Fires the cannon's laser. This method then checks, if a spaceship was hit, and if so, destorys that spaceship
	 * (it removes it from the gameboard's spaceship List).
	 * If multiple spaceships are hit in one line they are all destroyed.
	 */
	public void fireCannon();
	
	public int getSpaceshipWidth();
	public int getSpaceshipHeight();
	
	public void saveData(int score, LocalDateTime time);
	public Stream<Score> getData();
	
	//methods to be invoked by the game objects to acces the game board
	public int getGUIWidth();
	public int getGUIHeight();
	
	/**
	 * set the gameboard's GAME_OVER flag
	 */
	public void gameOver();
	
	//methods to be invoked by the gameobjects to access each other
	Set<Spaceship> getSpaceships();
}