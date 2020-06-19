package model;

import java.util.Set;

public class Cannon {

    private int position;
    private final GameObjectFacade gameBoard;

    /**
     * Sets up the player Cannon.
     *
     * @param gameBoard the GameObjectFacade instance to communicate with
     */
    public Cannon(GameObjectFacade gameBoard) {
        this.gameBoard = gameBoard;
        this.position = 0;
    }

    public void steer(int delta) {
        position = (position + delta) % gameBoard.getGUIWidth();
    }


    /**
     * Method that is called when the player fires the cannon. Currently "bullets" travel instantly, destroying one
     * ship in its path, but this can be changed in the future.
     *
     * @return Scorevalue of the destroyed ship. Later we theoretically can have multiple tiers of ship where each
     * has a different value her, depending on how difficult it is to shoot them down
     */
    public int fire() {
        int scoreToAdd = 0;
        Set<Spaceship> spaceships = gameBoard.getSpaceships();
        for (Spaceship s : spaceships) {
            //if the spaceship is in the firing line of the cannon, destroy it
            if (s.getX() <= position && s.getX() + Spaceship.spaceshipWidth >= position) {
                scoreToAdd = s.scorePerShip;
                s.destroy();
                break; // So you can only destroy one spaceship at a time
            }
        }
        //remove the eventually destroyed spaceships from the List
        spaceships.removeIf(s -> !s.getAlive());
        return scoreToAdd;
    }
}
