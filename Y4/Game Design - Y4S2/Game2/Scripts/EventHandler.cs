using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EventHandler : MonoBehaviour
{
    public static EventHandler current;

    void Start(){
        current = this;
    }

    public event Action onTerminalUse;
    public void TerminalUse(){
        if(onTerminalUse != null){
            onTerminalUse();
        }
    }

    public event Action<int> onCorrectAnswer;
    public void CorrectAnswer(int id){
        if(onCorrectAnswer != null){
            onCorrectAnswer(id);
        }
    }
}
