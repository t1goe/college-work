using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class FinalInteractable : MonoBehaviour
{
    public float[] answer;

    // public InputField[] input;

    private int quizSize;
    
    // private int failCount;

    public FinalInteractable(){}

    // Start is called before the first frame update
    void Start()
    {
        // failCount = 0;
        quizSize = answer.Length;
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public int checkAnswers(float[] ans){
        
        // foreach(float a in ans){
        int correctCount = 0;
        for(int i = 0; i< ans.Length; i++){
            if(ans[i] == answer[i]){
                // CanvasHandler ch = GameObject.Find("Interaction canvas").GetComponent<CanvasHandler>();
                // ch.activeCanvas = 0;
                correctCount++;
            }
        }
        CanvasHandler ch = GameObject.Find("Interaction canvas").GetComponent<CanvasHandler>();
        ch.activeCanvas = 4;
        return correctCount;
    }
}
