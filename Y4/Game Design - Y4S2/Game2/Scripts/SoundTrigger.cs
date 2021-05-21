using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoundTrigger : MonoBehaviour
{
    public bool active = true;
    public string sound = "test";

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.transform.name == "player_hitbox")
        {
            if(active){
                FindObjectOfType<AudioManager>().Play(sound);
                active = false;
            }
            
        }
    }

    void OnTriggerExit(Collider other)
    {
        
        if (other.CompareTag("MainCamera"))
        {

        
        }

    }
}
