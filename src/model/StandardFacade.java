package model;

import controller.GameBoard;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class StandardFacade implements GameObjectFacade {
	
	private final GameBoard gameBoard;
	private Cannon cannon;
	private Set<Spaceship> spaceships;
	private DataManager dataManager;
	
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
		this.dataManager = new DataManager();
		//add some initial spaceships
		spawnRandomSpaceships();
	}
	
	
	/**
	 * This method simply moved all spaceships one pixel closer to the ground
	 */
	@Override
	public void moveSpaceships() {
		//simple moving algorithm that just moves all the spaceships one pixel closer to the ground
		synchronized (spaceships) {
			for (Spaceship s : spaceships) {
				s.move(0, 15);
			}
		}
	}
	
	/**
	 * Spawns a random number (between 1 and 5, inclusive) of Spaceships on the top of the screen at random locations
	 */
	@Override
	public void spawnRandomSpaceships() {
		int numberOfSpaceships = 8; //1 + (int)(Math.random() * 10);
		/*int coordinate = 0;
		for(int i=0; i<numberOfSpaceships; i++) {
			coordinate = coordinate +						//spawn the spaceship left of the previous ones
					(int)(Math.random() *					//in a random location
					(GameBoard.GUI_WIDTH - Spaceship.spaceshipWidth - coordinate) /
					(numberOfSpaceships - i));				//so that there is enough space for the other ones
			spaceships.add(new Spaceship(this, coordinate));
		}*/
		for(int i = 0; i < numberOfSpaceships; i++){
			spaceships.add(new Spaceship(this, i*50));
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
		int val = cannon.fire();
		//forward score to gameBoard
		gameBoard.addToScore(val);

		if(spaceships.isEmpty()) //can be removed later in case the game should not be winnable but highscore based
			gameOver();
	}

	@Override
	public double getGUIWidth() {
		return GameBoard.GUI_WIDTH;
	}
	
	@Override
	public double getGUIHeight() {
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
	
	@Override
	public void saveData(int score, LocalDateTime time, String name) {
		//delegate to the data manager
		dataManager.saveData(score, time, name);
	}
	
	@Override
	public Stream<Score> getData() {
		//delegate to the data manager
		return dataManager.getData();
	}

	public Cannon getCannon(){
		return cannon;
	}
}
