import java.awt.event.*;

public class MouseInput implements MouseListener, MouseMotionListener {
    private static int mouseX = 0;
    private static int mouseY = 0;

    private static boolean leftMouseDown = false;
    private static boolean rightMouseDown = false;
    private static boolean middleMouseDown = false;


    private static final MouseInput instance = new MouseInput();

    MouseInput() {
    }

    public static MouseInput getInstance() {
        return instance;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    } //When mouse enters the frame

    public void mouseExited(MouseEvent e) {
    } //When mouse exits the frame

    public void mousePressed(MouseEvent e) {
        switch (e.getModifiers()) {
            case MouseEvent.BUTTON1_MASK: //Left mouse
                leftMouseDown = true;
                break;
            case MouseEvent.BUTTON2_MASK: //Middle mouse
                middleMouseDown = true;
                break;
            case MouseEvent.BUTTON3_MASK: //Right mouse
                rightMouseDown = true;
                break;
        }
    }

    public void mouseReleased(MouseEvent e) {
        switch (e.getModifiers()) {
            case MouseEvent.BUTTON1_MASK: //Left mouse
                leftMouseDown = false;
                break;
            case MouseEvent.BUTTON2_MASK: //Middle mouse
                middleMouseDown = false;
                break;
            case MouseEvent.BUTTON3_MASK: //Right mouse
                rightMouseDown = false;
                break;
        }
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static void setMouseX(int mouseX) {
        MouseInput.mouseX = mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static void setMouseY(int mouseY) {
        MouseInput.mouseY = mouseY;
    }

    public static boolean isLeftMouseDown() {
        return leftMouseDown;
    }

    public static void setLeftMouseDown(boolean leftMouseDown) {
        MouseInput.leftMouseDown = leftMouseDown;
    }

    public static boolean isRightMouseDown() {

        return rightMouseDown;
    }

    public static void setRightMouseDown(boolean rightMouseDown) {
        MouseInput.rightMouseDown = rightMouseDown;
    }

    public static boolean isMiddleMouseDown() {
        return middleMouseDown;
    }

    public static void setMiddleMouseDown(boolean middleMouseDown) {
        MouseInput.middleMouseDown = middleMouseDown;
    }

    //    public static void main(String[] args) {
//        new MouseInput();
//    }
}
