using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DoorBehavior : MonoBehaviour
{
    public Vector3 OpenPosition;
    public Vector3 ClosedPosition;
    public bool open = false;
    public float Duration = 2f;//Time for door to open

    public Vector3 StartPos;
    public Vector3 DestPos;
    public float Timer = 100f;

    // Start is called before the first frame update
    void Start()
    {
        ClosedPosition = transform.position;
        OpenPosition = transform.position + new Vector3(0, 3, 0);

        // if(!open){
        //     closeDoor();
        // }else{
        //     openDoor();
        // }
    }

    // Update is called once per frame
    void Update()
    {
        // if(Input.GetKeyUp(KeyCode.Q)){
        //     if(open){
        //         closeDoor();
        //     }else{
        //         openDoor();
        //     }
        // }

        if(Timer < Duration){
            transform.position = Vector3.Lerp(StartPos, DestPos, Timer / Duration);
        }

        // if(Timer > Duration){
        //     transform.position = DestPos;
        // }
        Timer += Time.deltaTime;
    }

    public void openDoor(){
        if(Timer > Duration){
            Timer = 0f;
            StartPos = ClosedPosition;
            DestPos = OpenPosition;
            open = true;
        }
    }

    public void closeDoor(){
        if(Timer > Duration){
            Timer = 0f;
            StartPos = OpenPosition;
            DestPos = ClosedPosition;
            open = false;
        }
    }
}
