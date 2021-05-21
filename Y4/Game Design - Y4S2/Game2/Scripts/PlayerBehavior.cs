using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerBehavior : MonoBehaviour
{

    public float speed = 5.0f;
    public float horizontalInput;
    public float verticalInput;

    public float jumpHeight = 10f;

    public float mouseX;
    // public float mouseY;
    
    private float menuModifier = 1;

    public FixedJoystick moveJoystick;
    public FixedJoystick lookJoystick;
    public touchButtonScript jumpButton;
    

    // Start is called before the first frame update
    void Start()
    {
        // EventHandler.Instance.onTerminalUse += ToggleMovement;
    }

    // Update is called once per frame
    void Update()
    {
        CanvasHandler ch = GameObject.Find("Interaction canvas").GetComponent<CanvasHandler>();
        
        float menuMovementModifier = 0f;
        if(ch.isMovementAllowed()){
            menuMovementModifier = 1;
        }else{
            menuMovementModifier = 0;
        }

        
        if (Application.platform == RuntimePlatform.Android){
            horizontalInput = moveJoystick.Horizontal;
            verticalInput = moveJoystick.Vertical;
        }else{
            horizontalInput = Input.GetAxis("Horizontal");
            verticalInput = Input.GetAxis("Vertical");
        }

        horizontalInput *= menuMovementModifier;
        verticalInput *= menuMovementModifier;

        if (Application.platform == RuntimePlatform.Android){
            mouseX = lookJoystick.Horizontal;
        }else{
            mouseX = Input.GetAxis("Mouse X");
        }
        
        
        mouseX *= menuMovementModifier;
        // mouseY = Input.GetAxis("Mouse Y");

        if(Physics.Raycast(transform.position, -Vector3.up, GetComponent<Collider>().bounds.extents.y + 0.1f)){
            if(Input.GetKeyDown(KeyCode.Space) || jumpButton.Pressed){
                GetComponent<Rigidbody>().velocity = new Vector3(0, jumpHeight, 0);
            }
        }


        transform.Translate(Vector3.forward * Time.deltaTime * speed * verticalInput);
        transform.Translate(Vector3.right * Time.deltaTime * speed * horizontalInput);

        transform.Rotate(Vector3.up, mouseX);
        // transform.Rotate(Vector3.left, mouseY);
        // print(mouseX);
        // transform.Rotate(0, mouseX, 0, Space.World);
    }

    private void ToggleMovement(){
        if(menuModifier == 1){
            menuModifier = 0;
        }else{
            menuModifier = 1;
        }
    }
}
