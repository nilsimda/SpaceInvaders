package src.model;

import src.controller.GameBoard;

import java.util.List;

public class Cannon {
	
	private int position;
	private GameBoard gameBoard;
	
	/**
	 * Moves the cannon by the specified delta value. If the cannon moves out of the field, 
	 * it comes back in on the other side.
	 * @param delta Positive values correspond to movements to the right,
	 * negative ones to movements to the left.
	 */
	public void steer(int delta) {
		position = (position + delta) % GameBoard.GUI_WIDTH;
	}
	
	/**
	 * Fires the cannon's laser. This method then checks, if a spaceship was hit, and if so, destorys that spaceship
	 * (it removes it from the gameboard's spaceship List).
	 * If multiple spaceships are hit in one line they are all destroyed.
	 */
	public void fire() {
		List<Spaceship> spaceships = gameBoard.getSpaceships();
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
