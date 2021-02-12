/**Guess Word is a hang-man-like game in which players 
 * can select a difficult and start a new game, guess 
 * letters until the word from the WordList file is 
 * completed or the player runs out of guess, save and 
 * load previous games, and all in one window and GUI basis.
 * 
 * @author Dawson C. Branch
 * @version 2.0.0
 * @since 1.0.0
 */
package gamePack.guess_word;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import java.util.Scanner;
import java.nio.file.Paths;

/**FXMLDocumentController provides the general functionality to the GUI
 * 
 * @author Dawson C. Branch
 * @version 1.0.2
 * @since 1.0.0
 */
public class FXMLDocumentController
{
    //GUI controller variables
    //Numeric representation of difficulty (1 being the lowest number and the easiest difficulty
    int difficulty = 1; 
    
    //Text file containing all the game's possible mystery words    
    File wordList = new File ("WordList.txt");
     
    /*String used to hold the word currently being looked at when scanning
    through the word list*/
    String tempWord = "";
    /*A Game class object that contains the data for the game
     * currently being played*/
    Game currGame; 
    /*An ArrayList of easy words, words with 10 characters, from the mystery word list*/
    List<String> easyWords = new ArrayList<String>();
    /*An ArrayList of intermediate words, words with 8 characters, from the mystery word list*/
    List<String> interWords = new ArrayList<String>();
    /*An ArrayList of hard words, words with 6 characters, from the mystery word list*/
    List<String> hardWords = new ArrayList<String>();
    

    //Start Screen
    @FXML
    private Pane pne_StartMenu;

    @FXML
    private Slider sld_Difficulty;

    @FXML
    private Label lbl_Difficulty;


    @FXML
    private Button btn_Load;
    
    
    //Save selection UI
    //Contains save selection UI part of start menu
    @FXML
    private HBox hbx_LoadGame;
    
    @FXML
    private ListView<String> ltv_Saves;

    @FXML
    private Button btn_LoadSelected;
    
    
    //Game Screen
    @FXML
    private Pane pne_Game;

    @FXML
    private Label lbl_MysteryWord;
    
    /*Number of wrong answers acceptable before the game is lost. For clarity, 
    change label from "Guesses" to 'Lifes' and, optionally, remove the # display
    from the label, and a row of cartoon hearts representing the number of 
    'Lifes' left.*/
    @FXML
    private Label lbl_Guesses;

    @FXML
    private Label lbl_GuessedLetters;

    @FXML
    private Button btn_Guess;

    @FXML
    private TextField txt_GuessInput;

    @FXML
    private Button btn_Save;

    
    @FXML
    private Label lbl_InvInputText;
    
    @FXML
    private Label lbl_EndResult;

    @FXML
    private Button btn_Return;
    @FXML
    private Button btn_CreateGame;
    @FXML
    private Button btn_Quit;
    
    
    /**Adjusts difficulty upon the mouse moving the slider in the respective
     * portion, fraction, of the slider's range
     * 
     * @param event - When the slider is adjusted
     */
    @FXML
    void adjustDifficulty(MouseEvent event)
    {
        if(sld_Difficulty.getValue() < (sld_Difficulty.getMax() / 3))
        {
            difficulty = 1;
            lbl_Difficulty.setText("Easy");
        }
        else if(sld_Difficulty.getValue() > (2 * (sld_Difficulty.getMax() / 3)))
        {
            difficulty = 3;
            lbl_Difficulty.setText("Hard");
        }
        else
        {
            {
            difficulty = 2;
            lbl_Difficulty.setText("Intermediate");
        }
        }
    }

    @FXML
    void createGame(ActionEvent event)
    {
        Random randomGen = new Random();
        int random = 0;
        System.out.println(random);
        int numGuesses = 0;
        
        
        /*If the word list for each difficulty level is not populated, then the 
        code block will try to populate it*/
        if(easyWords.isEmpty() || interWords.isEmpty() || hardWords.isEmpty())
        {
            try(BufferedReader brInput = new BufferedReader(new FileReader(wordList)))
            {
                while((tempWord = brInput.readLine()) != null)
                {
                    if(tempWord.length() == 10)
                    {
                        easyWords.add(tempWord);
                    }
                    if(tempWord.length() == 8)
                    {
                        interWords.add(tempWord);
                    }
                    if(tempWord.length() == 6)
                    {
                        hardWords.add(tempWord);
                    }
                }
            }
            catch(NoSuchElementException | IOException | IllegalStateException e)
            {
                System.out.println("NO FILE FOUND");
                e.printStackTrace();

                System.exit(1);
            }
        }
        /*Array List for the selected difficulty's words to go into.
        Note: Inefficient. See comment after switch.*/
        List<String> selectedWords = new ArrayList<String>(); 
        switch(difficulty)
        {
            case 1:
                selectedWords.addAll(easyWords);
                numGuesses = 10;
                break;
            case 2:
                selectedWords.addAll(interWords);
                numGuesses = 7;
                break;
            case 3:
                selectedWords.addAll(hardWords);
                numGuesses = 4;
                break;
        }
        
        System.out.println(selectedWords.size());
        random = randomGen.nextInt(selectedWords.size());
        System.out.println(random);
        currGame = new Game(selectedWords.get(random), numGuesses);
        
        //First half of UI pane switch
        pne_StartMenu.setVisible(false);
        pne_Game.setDisable(false);
        
        //Set UI elements with the values from their corresponding variables
        lbl_MysteryWord.setText(currGame.getCurrWord());
        lbl_Guesses.setText(Integer.toString(currGame.getNumGuesses()));
        lbl_GuessedLetters.setText(currGame.getGuesses());
        
        //Enable Save button
        btn_Save.setVisible(true);
        btn_Save.setDisable(false);
        
        //Remove Return button
        btn_Return.setDisable(true);
        btn_Return.setVisible(false);
        
        //Removing load game UI elements
        btn_Load.setVisible(true);
        hbx_LoadGame.setVisible(false);
        ltv_Saves.setVisible(false);
        ltv_Saves.getItems().clear();
        ltv_Saves.setDisable(true);
        hbx_LoadGame.setDisable(true);
        
        //Enabling Guess textbox and button
        txt_GuessInput.setDisable(false);
        btn_Guess.setDisable(false);
        
        //Second half of UI pane switch
        pne_StartMenu.setDisable(true);
        pne_Game.setVisible(true);
    }

    /**loadGame pulls the list of saved game files
     * 
     * @param event Load Game button is pressed
     */
    @FXML
    void loadGame(ActionEvent event)
    {
        hbx_LoadGame.setDisable(false);
        btn_Load.setVisible(false);
        ltv_Saves.setDisable(false);
        
        File[] files = new File(".").listFiles();
        /*Searches through save files in project folder and adds each save found
        to the listview object for saves*/
        for (File file : files)
        {
            if (file.isFile() && file.getName().contains("SAVE_"))
            {
                ltv_Saves.getItems()
                        .add(file.getName());
            }
        }
        hbx_LoadGame.setVisible(true);

        ltv_Saves.setVisible(true);
    }
    
    /**Use the save file highlighted in the listview for saves to recreate the 
    Game class
    * 
    * @param event Load button in hbx for saved games is clicked
    * Possible bug: Load button may be selectable even when there are no save 
    * files.
    * Possible fixes: Add a cancel/return button that displays with the rest of 
    * that portion of the UI and, when there's no saves detected, display a
    * message saying that
    * return button to saves hbx,
    */
    @FXML
    void loadSelected(ActionEvent event)
    {
        File[] files = new File(".").listFiles();
        List<String> fileList = new ArrayList<String>();
        
        String mystWord = "";
        String currWord = "";
        String guesses = "";
        int numGuesses = 0;
        
        for (File file : files)
        {
            if (file.isFile() && file.getName().contains("SAVE_"))
            {
                fileList.add(file.getName());
            }
        }
        
        try (Scanner input = new Scanner(Paths.get(fileList.get(ltv_Saves.getSelectionModel().getSelectedIndex()))))
        {
            while (input.hasNext())
            {
                mystWord = input.nextLine();
                currWord = input.nextLine();
                guesses = input.nextLine();
                numGuesses = input.nextInt();
                System.out.println(numGuesses);
            }
            
            currGame = new Game(mystWord, currWord, guesses, numGuesses);
            
            //First half of UI pane switch
            pne_StartMenu.setVisible(false);
            pne_Game.setDisable(false);

            //Set UI elements with the values from their corresponding variables
            lbl_MysteryWord.setText(currGame.getCurrWord());
            lbl_Guesses.setText(Integer.toString(currGame.getNumGuesses()));
            lbl_GuessedLetters.setText(currGame.getGuesses());

            //Enable Save button
            btn_Save.setVisible(true);
            btn_Save.setDisable(false);

            //Remove Return button
            btn_Return.setDisable(true);
            btn_Return.setVisible(false);

            //Removing load game UI elements
            btn_Load.setVisible(true);
            hbx_LoadGame.setVisible(false);
            ltv_Saves.setVisible(false);
            ltv_Saves.getItems().clear();
            ltv_Saves.setDisable(true);
            hbx_LoadGame.setDisable(true);
            
            //Enabling Guess textbox and button
            txt_GuessInput.setDisable(false);
            btn_Guess.setDisable(false);

            //Second half of UI pane switch
            pne_StartMenu.setDisable(true);
            pne_Game.setVisible(true);
        }
        catch(NoSuchElementException | IllegalStateException | IOException e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
            
            System.exit(1);
        }
        
        //currGame = new Game();
        ltv_Saves.setDisable(false);
        ltv_Saves.setVisible(true);
    }
    
    /**Forfeit the game, prevent the player from interacting with the game
     * components and save function, reveal the mystery word, and reveal the
     * return to start menu button.
     * 
     @param event Quit button is clicked*/
    @FXML
    void quitGame(ActionEvent event)
    {
        lbl_MysteryWord.setText(currGame.getMystWord());
        
        btn_Save.setVisible(false);
        btn_Save.setDisable(true);
        
        btn_Return.setDisable(false);
        btn_Return.setVisible(true);
        
        txt_GuessInput.setDisable(true);
        btn_Guess.setDisable(true);
    }
    
    /**Return to the start menu
     * 
     * @param event The return button is clicked
     */
    @FXML
    void returnToStart (ActionEvent event)
    {
        pne_Game.setVisible(false);
        
        lbl_InvInputText.setVisible(false);
        
        lbl_EndResult.setVisible(false);
        txt_GuessInput.setText("");
        
        pne_StartMenu.setDisable(false);
        pne_Game.setDisable(true);
        pne_StartMenu.setVisible(true);
    }
    
    /**Looks at the guess letter text box. If the letter is in the mystery word,
     * then EnterGuess fills any instances of the letter in the mystery word 
     * label, adds it to the label of guessed letters, and declares the game won
     * If there are no more letters to guess, decrease the number of guesses of 'Lifes' remaining,
     * and declare the game lost if there are now 0 left. Displays an error
     * message if the input is invalid. 
     * 
     * @param event Guess button is clicked
     * For clarity, change name to EnteredGuess.
     */
    @FXML
    void enterGuess(ActionEvent event)
    {
        if ((txt_GuessInput.getText().length() != 1)
                || !(Character.isAlphabetic(txt_GuessInput.getText().charAt(0)))
                || lbl_GuessedLetters.getText().toUpperCase().contains(txt_GuessInput.getText().toUpperCase()))
        {
            lbl_InvInputText.setVisible(true);
            return;
        }
        lbl_InvInputText.setVisible(false);
        
        if(!(currGame.guess(txt_GuessInput.getText().charAt(0))))
        {
            return;
        }
        lbl_MysteryWord.setText(currGame.getCurrWord());
        lbl_GuessedLetters.setText(currGame.getGuesses());
        lbl_Guesses.setText(Integer.toString(currGame.getNumGuesses()));
        
        if(currGame.getNumGuesses() == 0)
        {
            if(currGame.getCurrWord().contains("_"))
            {
                lbl_EndResult.setText("YOU LOSE.");
                lbl_EndResult.setVisible(true);
            }
            else
            {
                lbl_EndResult.setText("YOU WIN!");
                lbl_EndResult.setVisible(true);
            }
        }
        else
        {
            if(!currGame.getCurrWord().contains("_"))
            {
                lbl_EndResult.setText("YOU WIN!");
                lbl_EndResult.setVisible(true);
            }
        }
        
        txt_GuessInput.setText("");
    }

    /**Uses the current Game class's save method to create a save file.
     * 
     * @param event Save button is clicked
     */
    @FXML
    void saveGame(ActionEvent event)
    {
        currGame.save();
    }
}