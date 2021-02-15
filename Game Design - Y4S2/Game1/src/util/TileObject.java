package util;

public class TileObject extends GameObject{

    // empty, block, spike
    private State state = State.EMPTY;

    public TileObject() {}

    public TileObject(State state) {
        this.state = state;
    }

    public State getState(){
        return  this.state;
    }

    public void setState(State state){
        this.state = state;
    }
}
