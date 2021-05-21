using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CanvasHandler : MonoBehaviour
{

    public Canvas crosshair;
    public Canvas terminalInteraction;
    public Canvas mainMenu;
    public Canvas finalQuiz;
    public Canvas end;

    public Interactable activeTerminal = new Interactable();
    public FinalInteractable finalTerminal;

    public Text displayText;
    public InputField inputField;
    public InputField[] quizFields;

    public Button submitButton;
    public Button startButton;
    public Button quizSubmit;

    public Text endMainText;
    public Text endQuizText;

    public GameObject mobileControls;

    // 0 = Crosshair
    // 1 = Termainal
    // 2 = Menu
    // 3 = finalQuiz
    // 4 = end
    public int activeCanvas;

    public int mainMistakes;

    public GameObject startFlag;

    // Start is called before the first frame update
    void Start()
    {
        // EventHandler.Instance.onTerminalUse += OnTerminalDisplay;
        // Text tempTermText = terminalInteraction.AddComponent<Text>();
        // tempTermText = terminalText;
        displayText.text = "default text";

        Button btn = submitButton.GetComponent<Button>();
		btn.onClick.AddListener(SubmitAnswer);

        Button btn1 = startButton.GetComponent<Button>();
		btn1.onClick.AddListener(StartGame);

        Button btn2 = quizSubmit.GetComponent<Button>();
		btn2.onClick.AddListener(SubmitQuiz);

        activeCanvas = 2;

        mainMistakes = 0;
    }

    // Update is called once per frame
    void Update()
    {
        crosshair.enabled = false;  
        terminalInteraction.enabled = false; 
        mainMenu.enabled=false;
        finalQuiz.enabled = false;
        end.enabled=false;

        // displayText.text = activeTerminal.displayText;

        switch(activeCanvas){
            case 0:
                crosshair.enabled = true;
                inputField.text = "";

                int quizLength = quizFields.Length;
                for(int i = 0; i < quizLength; i++){
                    quizFields[i].text = "";
                }
                
                break;
            case 1:
                displayText.text = activeTerminal.displayText;
                terminalInteraction.enabled = true;
                break;
            case 2:
                mainMenu.enabled = true;
                break;
            case 3:
                finalQuiz.enabled = true;
                break;
            case 4:
                end.enabled = true;
                endMainText.text = "Main game\n" + mainMistakes.ToString() + " mistakes made";
                break;

        }
        // if (Input.GetKeyUp(KeyCode.Q)) 
        // {
        //     terminalInteraction.enabled = !terminalInteraction.enabled;
        // }
    }

    private void OnTerminalDisplay(){
        terminalInteraction.enabled = !terminalInteraction.enabled;
    }

    public bool isMovementAllowed(){
        if(activeCanvas == 0){
            return true;
        }else{
            return false;
        }
        // switch(activeCanvas){
        //     case 0:
        //         return true;
        //         break;
        //     case 1:
        //         return false;
        //         break;
        //     case 2:
        //         return false;
        //         break;
        //     case 3:
        //         return false;
        //         break;
        //     case 4:
        //         return false;
        //         break;
        //     default:
        //         return false;
        //         break;
        // }
    }

    private void SubmitQuiz(){
        
        int quizLength = quizFields.Length;
        float[] answers = new float[quizLength];

        for(int i = 0; i < quizLength; i++){
            if(quizFields[i].text == ""){
                FindObjectOfType<AudioManager>().Play("quiz_fail");  
                return;      
            }
            answers[i] = float.Parse(quizFields[i].text);
            
        }
        //FindObjectOfType<AudioManager>().Play("fail");
        int quizScore = finalTerminal.checkAnswers(answers);
        endQuizText.text = "Quiz\n" + quizScore.ToString() + "/3 correct answers";
    }

    private void SubmitAnswer(){
        activeTerminal.checkAnswer(float.Parse(inputField.text));
    }

    private void StartGame(){
        
        activeCanvas = 0;
        // GameObject.Find("bot").GetComponent<BotBehavior>().setGameState(0);
        GameObject.Find("bot").GetComponent<BotBehavior>().animate(0, startFlag);
    }
}
