import util.Direction;
import util.LevelMap;
import util.PlayerObject;
import util.Vector3f;

public class Controller {

    private MouseInput mouse = MouseInput.getInstance();
    private KeyboardInput keyboard = KeyboardInput.getInstance();

    //Dead zone which indicates how far away the mouse must be from the player to be indicated as "that direction"
    private final int deadZone = 60;

    private static final Controller instance = new Controller();

    public Controller() {
    }

    public static Controller getInstance() {
        return instance;
    }

    public boolean isMoveRightPressed(PlayerObject p, LevelMap l) {
        return KeyboardInput.getInstance().isKeyDPressed() ||
                (mousePositionRelativeToPlayerX(p, l) == Direction.RIGHT && MouseInput.isLeftMouseDown());
    }

    public boolean isMoveLeftPressed(PlayerObject p, LevelMap l) {
        return KeyboardInput.getInstance().isKeyAPressed() ||
                (mousePositionRelativeToPlayerX(p, l) == Direction.LEFT && MouseInput.isLeftMouseDown());
    }

    public boolean isJumpPressed() {
        return MouseInput.isRightMouseDown()
                || KeyboardInput.getInstance().isKeySpacePressed();
    }

    //Returns the direction the player is trying to dash in
    public Direction isDashPressed(PlayerObject p, LevelMap l) {

        //Keyboard input for dash (takes priority)
        if (KeyboardInput.getInstance().isKeyQPressed()) {

            Direction xDirection = Direction.NONE;
            Direction yDirection = Direction.NONE;

            //Check horizontal input
            if (KeyboardInput.getInstance().isKeyAPressed() && !KeyboardInput.getInstance().isKeyDPressed()) {//Left
                xDirection = Direction.LEFT;

            } else if (!KeyboardInput.getInstance().isKeyAPressed() && KeyboardInput.getInstance().isKeyDPressed()) {//Right
                xDirection = Direction.RIGHT;

            }

            //Check vertical input
            if (KeyboardInput.getInstance().isKeyWPressed() && !KeyboardInput.getInstance().isKeySPressed()) {//Up
                yDirection = Direction.UP;

            } else if (!KeyboardInput.getInstance().isKeyWPressed() && KeyboardInput.getInstance().isKeySPressed()) {//Down
                yDirection = Direction.DOWN;

            }


            switch (yDirection) {
                case UP:
                    switch (xDirection) {
                        case LEFT:
                            return Direction.UPLEFT;
                        case RIGHT:
                            return Direction.UPRIGHT;
                        case NONE:
                            return Direction.UP;
                    }
                    break;
                case DOWN:
                    switch (xDirection) {
                        case LEFT:
                            return Direction.DOWNLEFT;
                        case RIGHT:
                            return Direction.DOWNRIGHT;
                        case NONE:
                            return Direction.DOWN;
                    }
                    break;
                case NONE:
                    switch (xDirection) {
                        case LEFT:
                            return Direction.LEFT;
                        case RIGHT:
                            return Direction.RIGHT;
                        case NONE:
                            return Direction.NONE;

                    }
                    break;
            }

        }

        //Mouse input for dash
        if (MouseInput.isMiddleMouseDown()) {

            Direction xDirection = Direction.NONE;
            Direction yDirection = Direction.NONE;

            //Check horizontal input
            switch(mousePositionRelativeToPlayerX(p, l)){
                case LEFT:
                    xDirection = Direction.LEFT;
                    break;
                case RIGHT:
                    xDirection = Direction.RIGHT;
                    break;
            }


            //Check vertical input
            switch(mousePositionRelativeToPlayerY(p, l)){
                case UP:
                    yDirection = Direction.UP;
                    break;
                case DOWN:
                    yDirection = Direction.DOWN;
                    break;
            }


            switch (yDirection) {
                case UP:
                    switch (xDirection) {
                        case LEFT:
                            return Direction.UPLEFT;
                        case RIGHT:
                            return Direction.UPRIGHT;
                        case NONE:
                            return Direction.UP;
                    }
                    break;
                case DOWN:
                    switch (xDirection) {
                        case LEFT:
                            return Direction.DOWNLEFT;
                        case RIGHT:
                            return Direction.DOWNRIGHT;
                        case NONE:
                            return Direction.DOWN;
                    }
                    break;
                case NONE:
                    switch (xDirection) {
                        case LEFT:
                            return Direction.LEFT;
                        case RIGHT:
                            return Direction.RIGHT;
                        case NONE:
                            return Direction.NONE;

                    }
                    break;
            }

        }

        return Direction.NONE;
    }

    private Direction mousePositionRelativeToPlayerX(PlayerObject p, LevelMap l) {
        int width = p.getWidth();

        int playerAlign;
        if (!p.isFacingRight()) {
            playerAlign = width;
        } else {
            playerAlign = 0;
        }

        //Get player position on screen
        int playerXPos = (int) p.getCentre().getX() + playerAlign + l.getOffsetX();

        if (playerXPos > MouseInput.getMouseX() + deadZone) {
            return Direction.LEFT;
        } else if (playerXPos < MouseInput.getMouseX() - deadZone) {
            return Direction.RIGHT;
        }
        return Direction.NONE;
    }

    private Direction mousePositionRelativeToPlayerY(PlayerObject p, LevelMap l) {
        //Get player position on screen
        int playerYPos = (int) p.getCentre().getY() + l.getOffsetY();

        if (playerYPos > MouseInput.getMouseY() + deadZone) {
            return Direction.UP;
        } else if (playerYPos < MouseInput.getMouseY() - deadZone) {
            return Direction.DOWN;
        }
        return Direction.NONE;
    }

    //used to ensure that the jump button isn't pressed multiple times
    public void setJumpPressed(Boolean b) {
        MouseInput.setRightMouseDown(b);
        KeyboardInput.getInstance().setKeySpacePressed(b);
    }
}
