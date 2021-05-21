using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BotBehavior : MonoBehaviour
{
    public GameObject lookFocus;
    public GameObject moveFocus = null;
    public GameObject tem = null;

    public float speed = 1;

    public float gameState;

    // public AudioSource audioSource;

    // Start is called before the first frame update
    void Start()
    {
        gameState = -1;
    }

    // Update is called once per frame
    void Update()
    {
        // if(Input.GetKeyUp(KeyCode.R)){
        //     // audioSource.Play();
        //     FindObjectOfType<AudioManager>().Play("intro");
        //     FindObjectOfType<AudioManager>().delayPlay(2, "test");
        // }

        // if(Input.GetKeyUp(KeyCode.F)){
        //     moveFocus = GameObject.Find("flag1");
        // }

        if(moveFocus != null){
            transform.LookAt(moveFocus.transform);
            transform.Rotate(Vector3.left, 5f);   

           // Move our position a step closer to the target.
            float step =  speed * Time.deltaTime; // calculate distance to move
            transform.position = Vector3.MoveTowards(transform.position, moveFocus.transform.position, step);

            // Check if the position of the cube and sphere are approximately equal.
            if (Vector3.Distance(transform.position, moveFocus.transform.position) < 0.001f)
            {
                // if(moveFocus.GetComponent<FlagScript>().sound != null){

                // }
                // GameObject tempNextFlag = moveFocus.GetComponent<FlagScript>().nextFlag;
                // if(tempNextFlag == null){
                //     setGameState(moveFocus.GetComponent<FlagScript>().nextGameState);
                // }
                // moveFocus = tempNextFlag;
                
                
                string x = moveFocus.GetComponent<FlagScript>().sound;
                moveFocus = moveFocus.GetComponent<FlagScript>().nextFlag;
                FindObjectOfType<AudioManager>().Play(x);
            } 
        }else{
            transform.LookAt(lookFocus.transform);
            transform.Rotate(Vector3.left, 5f);   
        }
    }

    public void MoveTowards(GameObject target){
        moveFocus = target;
    }

    public void animate(float wait, GameObject startFlag){
        StartCoroutine(animateHelper(wait, startFlag));
    }

    private IEnumerator animateHelper(float wait, GameObject startFlag){
        yield return new WaitForSeconds(wait);
        moveFocus = startFlag;      
    }
}
