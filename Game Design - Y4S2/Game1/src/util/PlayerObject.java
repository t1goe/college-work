package util;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */
public class PlayerObject extends GameObject{

    // Added
    private Vector3f velocity = new Vector3f(0, 0, 0);
    private float maxSpeed = 0;
    private float friction = 0; //Value < 0 to apply to speed to slow it down
    private float acceleration = 0;

    public PlayerObject() {

    }

    public PlayerObject(String textureLocation, int width, int height, Point3f centre, float maxSpeed, float friction, float acceleration) {
        hasTextured = true;
        this.textureLocation = textureLocation;
        this.width = width;
        this.height = height;
        this.centre = centre;
        this.maxSpeed = maxSpeed;
        this.friction = friction;
        this.acceleration = acceleration;
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

    // Manually accelerates using acceleration value
    public void decelerate(){

        //Friction
        Vector3f currentVelocity = this.velocity;
        float dx = this.velocity.getX();
        float dy = this.velocity.getY();

        //Friction value, lower value slows faster
        dx *= this.friction;
        dy *= this.friction;

        //Value makes sure you come to a stop at *some* point by rounding down
        float threshold = (float) 0.1;
        if(dx < threshold)
            dx = 0;

        if(dy < threshold)
            dy = 0;

        // Apply friction
        this.setVelocity(new Vector3f(dx, dy, this.getVelocity().getZ()));
    }

}

/*
 *  Game Object 
 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::c:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::clc::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::lol:;::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::;;cool:;::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::codk0Oxolc:::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::coxk0XNWMMWWWNK0kxdolc::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::loxO0XWMMMMMMMWWWMMMMMMWWNK0Oxdlc::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::cldkOKNWMMMMMMMMMMMMMWWMMMMMMMMMMMMMWNX0Okdolc:::::::::::::::::::::::::::::::::
::::::::::::::::codk0KNWMMMMMMMMMMMMMMMMMMWMMMMMMMMMMMMMMMMMMMMWWXKOkxo:::::::::::::::::::::::::::::
::::::::::::;cdOXNWMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMMMMMNKOdc::::::::::::::::::::::::::::
:::::::::::::cxKXXNWWMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMWX0kdolc:::::::::::::::::::::::::::::
::::::::::::::d0000KKXNNWMMMMMMMMMMMMMMMMWWMMMMMMMMMMMMMMMMWNKOxolllllc:::::::::::::::::::::::::::::
::::::::::::::oO00000000KXXNWWMMMMMMMMMMMMWMMMMMMMMMMMMMWX0kdollllllllc:::::::::::::::::::::::::::::
::::::::::::::lk00000O000000KKXNWWMMMMMMMWWWMMMMMMMMWNKOxollllllllllll::::::::::::::::::::::::::::::
::::::::::::::cx0000000000O000000KXXNWMMMWWWMMMMWXK0kdlllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::dO00000000000000000000KKXNNNWWNKOxolllllllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::lO000000000000000OOOO0000000Kkdlllllllllllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::ck00000000000000000OOOOOOOOkxollllllllllllllllllllllll:::::::::::::::::::::::::::::::
:::::::::::::;;cx00000000000000000000OOOOOOxocllllllllllllllllllllllc:;;;;;;;;;;::::::::::::::::::::
;;;;;;;;;;;;;;;:oO00000000000000000OOOO0000kdllllcclllllllllllllllllc:;;;;;;;;;;;;;;;;;;::::::::::::
;;;;;;;;;;;;;;;:lO00000000000000OOO00000000Oolllllllllllllllllllllllc:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;ck0000000000OOO000000000000kolllllllllllllllllllllll:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;:dOO0000OOO0000000000000000kolllllllllllllllllllllllc:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;:lxkOOOO0000000000000000000kolllllllllllllllllllllooool::;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;::;;::cokOkkO00000000000000000000kolllllllllllllllllllllccccllcc::::;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;:;;:;::ccllodxkO00000000000000000000kolllllllllllllllllllcc:;;;;:::::;;:;;;;;;;;;;;;;;;;;;;;;
;;;;;;;::::::::::;;:ldkO0000000000000000000kolllllllllllllllllc::;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;::;;:::;;;;;:;::ldkO0000000000000000kollllllllllllllcc::;;;;:;;:;;;;;:;;;:;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;:;;;;:cldkO0000000000000kollllllllllllc:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;::ldkO0000000000kolllllllllcc::;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;::;:;;::ldkO0000000kolllllllc::::;;;;:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;::ldkO0000kolllcc:::;;;;;;;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;;;;:ldkO0kolcc::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;;::lodl:::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;::;:::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;::;;;;;;;;;;;:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
*/