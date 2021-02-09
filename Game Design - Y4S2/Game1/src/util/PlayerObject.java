package util;

public class PlayerObject extends GameObject{

    // Added
    private Vector3f velocity = new Vector3f(0, 0, 0);
    private float maxSpeed = 0;
    private float friction = 0; //Value < 0 to apply to speed to slow it down
    private float acceleration = 0;
    private float jumpAcc = 0;

    public PlayerObject() {}

    public PlayerObject(String textureLocation, int width, int height, Point3f centre, float maxSpeed, float friction, float acceleration, float jump) {
        hasTextured = true;
        this.textureLocation = textureLocation;
        this.width = width;
        this.height = height;
        this.centre = centre;
        this.maxSpeed = maxSpeed;
        this.friction = friction;
        this.acceleration = acceleration;
        this.jumpAcc = jump;
    }

    public Vector3f getVelocity(){
        return this.velocity;
    }

    public void setVelocity(Vector3f velocity){
        this.velocity = velocity;
    }

    public float getMaxSpeed(){
      return this.maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed){
      this.maxSpeed = maxSpeed;
    }

    public float getFriction(){
      return this.friction;
    }

    public void setFriction(float friction){
      this.friction = friction;
    }

    public float getJumpAcc(){
      return this.jumpAcc;
    }

    public void setJumpAcc(float jump){
      this.jumpAcc = jump;
    }

    // Moves the object by the current velocity
    public Point3f applyCurrentVelocity(){
        Point3f newCentre = this.centre.PlusVector(this.velocity);
        this.centre = newCentre;
        return newCentre;
    }

    //Adds velocity vector to current velocity
    public Vector3f addVelocity(Vector3f velocity){
        Vector3f newVelocity = this.velocity.PlusVector(velocity);
        this.velocity = newVelocity;
        return newVelocity;
    }

    // Manually accelerates using acceleration value
    public void accelerate(int direction){
        switch (direction){
            case 0: //UP or -Y
                if(this.getVelocity().getY() - this.acceleration > -this.maxSpeed){
                    this.addVelocity(new Vector3f(0, -this.acceleration, 0));
                }else{
                    this.setVelocity(new Vector3f(this.getVelocity().getX(), -this.maxSpeed, this.getCentre().getZ()));
                }

                break;
            case 1: //RIGHT or +X
                if(this.getVelocity().getX() + this.acceleration < this.maxSpeed){
                    this.addVelocity(new Vector3f(this.acceleration, 0, 0));
                }else{
                    this.setVelocity(new Vector3f(this.maxSpeed, this.getVelocity().getY(), this.getCentre().getZ()));
                }

                break;
            case 2: //DOWN or +Y
                if(this.getVelocity().getY() + this.acceleration < this.maxSpeed) {
                    this.addVelocity(new Vector3f(0, this.acceleration, 0));
                }else{
                    this.setVelocity(new Vector3f(this.getVelocity().getX(), this.maxSpeed, this.getCentre().getZ()));
                }

                break;
            case 3: //LEFT or -X
                if(this.getVelocity().getX() - this.acceleration > -this.maxSpeed){
                    this.addVelocity(new Vector3f(-this.acceleration, 0, 0));
                }else{
                    this.setVelocity(new Vector3f(-this.maxSpeed, this.getVelocity().getY(), this.getCentre().getZ()));
                }

                break;
            default:
                System.out.println("Invalid direction, choose values 0-3");
        }
    }

    public void jump(){
      this.addVelocity(new Vector3f(0, -jumpAcc, 0));
    }

    // Manually accelerates using acceleration value
    public void decelerate(){
        this.verticallyDecelerate();
        this.horizontalDecelerate();
    }

    public void horizontalDecelerate(){
        //Friction
        Vector3f currentVelocity = this.velocity;
        float dx = this.velocity.getX();

        //Friction value, lower value slows faster
        dx *= this.friction;

        //Value makes sure you come to a stop at *some* point by rounding down
        float threshold = (float) 0.1;
        if(Math.abs(dx) < threshold)
            dx = 0;

        // Apply friction
        this.setVelocity(new Vector3f(dx, this.velocity.getY(), this.getVelocity().getZ()));
    }

    // Manually accelerates using acceleration value
    public void verticallyDecelerate(){

        //Friction
        Vector3f currentVelocity = this.velocity;
        float dy = this.velocity.getY();

        //Friction value, lower value slows faster
        dy *= this.friction;

        //Value makes sure you come to a stop at *some* point by rounding down
        float threshold = (float) 0.1;

        if(Math.abs(dy) < threshold)
            dy = 0;

        // Apply friction
        this.setVelocity(new Vector3f(this.velocity.getX(), dy, this.getVelocity().getZ()));
    }

    public String toString(){
        String output = "\n";
        output += "position: " + this.getCentre().getX() + ", " + this.getCentre().getY() + "\n";
        output += "velocity: " + this.getVelocity().getX() + ", " + this.getVelocity().getY() + "\n";

        return output;
    }

}