using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Interactable : MonoBehaviour
{
    public string displayText = "";
    public float answer = 0f;

    // public InputField input;

    public bool final = false;
    public GameObject[] doors;

    public string failureSound;
    
    private int failCount;

    public float botWait;
    public GameObject trackStart;

    public Interactable(){}

    // Start is called before the first frame update
    void Start()
    {
        failCount = 0;
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    

    public float checkAnswer(float ans){
        CanvasHandler ch = GameObject.Find("Interaction canvas").GetComponent<CanvasHandler>();
        if(ans == answer){
            
            ch.activeCanvas = 0;

            if(!final){
                FindObjectOfType<AudioManager>().Play("succ");
            }else{
                FindObjectOfType<AudioManager>().Play("final_intro");
            }
            
            foreach(GameObject door in doors){
                door.GetComponent<DoorBehavior>().openDoor();
                // bot.GetComponent<BotBehavior>().setGameState(1);
                GameObject.Find("bot").GetComponent<BotBehavior>().animate(botWait, trackStart);
            }
            
        }else{
            if(failCount == 0){
                FindObjectOfType<AudioManager>().Play("fail");
                failCount++;
            }else{
                FindObjectOfType<AudioManager>().Play("fail");
                FindObjectOfType<AudioManager>().delayPlay(3, failureSound);
            }
            ch.mainMistakes++;
        }
        
        return 0;
    }
}
