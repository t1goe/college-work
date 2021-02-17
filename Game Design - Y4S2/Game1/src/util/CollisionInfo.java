package util;

public class CollisionInfo {

    private State state;
    private Direction direction;
    private float ratio;

    public CollisionInfo() {}

    public CollisionInfo(State state, Direction direction, float ratio) {
        this.state = state;
        this.direction = direction;
        this.ratio = ratio;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

}
