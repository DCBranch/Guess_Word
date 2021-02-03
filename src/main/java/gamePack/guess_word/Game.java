/**Guess Word is a hang-man-like game in which players can select a difficult 
 * and start a new game, guess letters until the word from the WordList file is 
 * completed or the player runs out of guess, save and load previous games, and 
 * all in one window and GUI basis.
 * 
 * @author Dawson C. Branch
 * @version 2.0.0
 * @since 1.0.0
 */
package gamePack.guess_word;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**Game classes are what hold info about the player's game in the moment; what 
 * the full mystery is, what the player has revealed of the mystery word, and 
 * the player's guesses.
 * 
 * @author Dawson C. Branch
 * @version 1.0.2
 * @since 1.0.0
 */
public class Game {
    //word that's supposed to be guessed
    private char [] mysteryWord;
    //What the player has revealed of the mystery word
    private char [] currentWord;
    //List of all guessed letters
    private List<Character> guesses = new ArrayList<Character>();
    
    private int numGuesses = 0;
    
    /**New game constructor
     * 
     * @param word - Randomly selected mystery word from the word list file
     * @param numGuesses - The number of guesses allotted based on the 
     * difficulty
     */
    public Game(String word, int numGuesses)
    {
        System.out.println(word);
        
        mysteryWord = word.toCharArray();
        System.out.println(new String(mysteryWord));
        
        currentWord = new char [word.length()];
        //Fills the currentWord array with placeholder underscores
        for (int count = 0; count < word.length(); count++)
        {
                currentWord[count] = '_';
        }
        
        this.numGuesses = numGuesses;
    }
    
    /**Load game constructor
     * 
     * @param word - Randomly selected mystery word from the word list file
     * @param curr - What of the mystery word has been revealed
     * @param guesses - The letters that were already guessed
     * @param numGuesses - The number of guesses allotted based on the 
     * difficulty
     */
    public Game(String word, String curr, String guesses, int numGuesses)
    {
        mysteryWord = word.toCharArray();
        currentWord = curr.toCharArray();
        this.guesses = guesses.chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.toList());
        this.numGuesses = numGuesses;
    }
    
    /**Saves this game's info into a text file
     * 
     */
    public void save()
    {
        //Uses the local date and time as the name of the filename
        LocalDateTime currTime = LocalDateTime.now();
        String saveFileName = "SAVE_" + currTime.toString();
        saveFileName = saveFileName.replace('.', '_');
        saveFileName = saveFileName.replace(':', '_');
        
        //Adds new save data to string meant for save file. Format:  "mysteryWord|currentWordCharArray|lettersAlreadyGuessed|numberOfGuessesLeft return"
        String saveFileStr = new String(mysteryWord) + "\n" + new String(currentWord) + "\n" + guesses.toString().replaceAll("[,\\s\\[\\]]", "") + "\n" + Integer.toString(numGuesses);
        
        //Tries to read the current save file and write in the save data string
        try(BufferedWriter brInput = new BufferedWriter(new FileWriter(saveFileName)))
        {
            brInput.append(saveFileStr);
            brInput.newLine();
        }
        catch(NoSuchElementException | IOException | IllegalStateException e)
        {
            System.out.println("CAN'T CREATE SAVE FILE");
            e.printStackTrace();
            
            System.exit(1);
        }
    }
    
    /**Checks to see if the guess is in the mystery word, decrements numGuesses, and replaces _s with
     * the character if it's in the mystery word. If there's no guesses left, it returns false and makes
     * no changes to the class.
     * 
     * @param guess - letter that's been 'guessed'
    */
    public boolean guess(char guess)
    {
        boolean found = false;
        
        if(numGuesses==0 || (new String(currentWord).contentEquals(new String(mysteryWord))))
            return false;
        
        //Loojs for the first unchanged value in the array and makes it the guess letter
        guesses.add(guess);
        
        //Replaces _s in the currentWord with the guess letter if it belongs in said spot
        for(int count = 0; count < mysteryWord.length; count++)
            if(Character.toUpperCase(guess) == Character.toUpperCase(mysteryWord[count]))
            {
                currentWord[count] = mysteryWord[count];
                found = true;
            }
        
        if(!found)
            numGuesses--;
        
        return true;
    }
    
    /**Returns a String of the game's mystery word*/
    public String getMystWord()
    {
        return new String(mysteryWord);
    }
    
    /**Returns an int of the number of guesses allotted for the game*/
    public int getNumGuesses()
    {
        return numGuesses;
    }
    
    /**Returns a String of the game's currently revealed letters of the mystery
    word*/
    public String getCurrWord()
    {
        return new String(currentWord);
    }
    
    /**Returns a String of the letters already guessed*/
    public String getGuesses()
    {
        return guesses.toString();
    }
}