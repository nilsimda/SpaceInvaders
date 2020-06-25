package model;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class Cannon {

    public static double cannonHeight = 30;
    public static double cannonWidth = 40;
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
        position = (int) Math.max(Math.min(position + delta, gameBoard.getGUIWidth() -cannonWidth), 0);
    }


    /**
     * Method that is called when the player fires the cannon. Currently "bullets" travel instantly, destroying the
     * first ship in its path, but this can be changed in the future.
     *
     * @return Scorevalue of the destroyed ship. Later we theoretically can have multiple tiers of ship where each
     * has a different value her, depending on how difficult it is to shoot them down
     */
    public int fire() {
        synchronized (gameBoard.getSpaceships()) {
            Set<Spaceship> spaceships = gameBoard.getSpaceships();
            // Only the lowest Spaceship will be removed
            Optional<Spaceship> destroyedShip = spaceships
                    .stream()
                    .filter(s -> s.getX() <= position + cannonWidth / 2 &&
                            s.getX() + Spaceship.spaceshipWidth >= position + cannonWidth / 2)
                    .min(Comparator.comparingDouble(Spaceship::getY));
            if (destroyedShip.isPresent()) {
                gameBoard.getSpaceships().remove(destroyedShip.get());
                destroyedShip.get().destroy();
                return destroyedShip.get().scorePerShip;
            }
            return 0;
        }
    }

    public int getPosition() {
        return position;
    }
}
