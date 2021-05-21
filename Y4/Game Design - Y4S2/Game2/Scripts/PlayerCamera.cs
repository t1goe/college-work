using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerCamera : MonoBehaviour
{

    public GameObject player;
    public Vector3 cameraOffset = new Vector3(0, 1, 0);

    public float mouseY;
    public float camTilt = 0;

    public float verticalViewLimit = 85;

    Camera cam;

    private float menuModifier = 1;

    public FixedJoystick lookJoystick;

    public touchButtonScript useButton;
    

    // Start is called before the first frame update
    void Start()
    {
        cam = GetComponent<Camera>();
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


        //Update camera pos
        if (Application.platform == RuntimePlatform.Android){
            mouseY = lookJoystick.Vertical;
        }else{
            mouseY = Input.GetAxis("Mouse Y");
        }
        
        mouseY *= menuMovementModifier;

        transform.position = player.transform.position + cameraOffset;
        transform.rotation = player.transform.rotation;
        transform.Rotate(Vector3.left, camTilt);

        camTilt += mouseY;
        if(camTilt > verticalViewLimit){
            camTilt = verticalViewLimit;
        }else if(camTilt < -verticalViewLimit){
            camTilt = -verticalViewLimit;
        }

        

        //Check what camera is looking at
        Ray ray = cam.ViewportPointToRay(new Vector3(0.5F, 0.5F, 0));
        RaycastHit hit;
        if (Physics.Raycast(ray, out hit)){
            if(Input.GetKeyUp(KeyCode.E) || useButton.Pressed){
                useButton.Pressed = false;
                if(ch.activeCanvas == 1 || ch.activeCanvas == 3){
                    ch.activeCanvas = 0;
                }else if(hit.transform.name.StartsWith("terminal") &&(hit.distance < 5)){
                    ch.activeCanvas = 1;
                    ch.activeTerminal = hit.collider.gameObject.GetComponent<Interactable>();
                }else if(hit.transform.name.StartsWith("final terminal") && (hit.distance < 5)){
                    ch.activeCanvas = 3;
                    ch.activeTerminal = hit.collider.gameObject.GetComponent<Interactable>();
                }
            }
            
        }else{
            // print("I'm looking at nothing!");
        }
        // if (Input.GetKeyUp(KeyCode.E)) 
        // {
        //     OnTerminalUse();
        // }
    }

    // private void OnTerminalUse()
    // {
    //     EventHandler.Instance.TerminalUse();
    // }

    private void ToggleMovement(){
        if(menuModifier == 1){
            menuModifier = 0;
        }else{
            menuModifier = 1;
        }
    }

}
