using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MobileControlDisable : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        // if (Application.platform == RuntimePlatform.Android){
        //     gameObject.SetActive(true);
        // }else{
        //     gameObject.SetActive(false);
        // } 
    }

    // Update is called once per frame
    void Update()
    {
        
        print(Application.platform);
        if (Application.platform == RuntimePlatform.Android){
            gameObject.SetActive(true);
        }else{
            gameObject.SetActive(false);
        }

        
            
    }
}
