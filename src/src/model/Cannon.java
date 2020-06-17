package src.model;

import java.util.Set;

public class Cannon {
	
	private int position;
	private final GameObjectFacade gameBoard;
	
	/**
	 * Sets up the player Cannon.
	 * @param gameBoard the GameObjectFacade instance to communicate with
	 */
	public Cannon(GameObjectFacade gameBoard) {
		this.gameBoard = gameBoard;
		this.position = 0;
	}
	
	public void steer(int delta) {
		position = (position + delta) % gameBoard.getGUIWidth();
	}
	
	public void fire() {
		Set<Spaceship> spaceships = gameBoard.getSpaceships();
		for(Spaceship s : spaceships) {
			//if the spaceship is in the firin line of the cannon, destroy it
			if(s.getX() <= position && s.getX() + Spaceship.spaceshipWidth >= position) {
				s.destroy();
			}
		}
		//remove the eventually destroyed spaceships from the List
		spaceships.removeIf(s -> !s.getAlive());
	}
}
