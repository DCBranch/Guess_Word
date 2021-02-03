# Guess Word - word guessing game
A letter-by-letter word guessing game application made utilizing Java and OpenFX JavaFX libraries. The program showcases a JavaFX/FXML based GUI, file creation and reading, collection usage, and functional programing using lambdas and streams.

## Table of Contents
* [General Info](#general-info)
* [Technologies](#technologies)
* [Possible Updates](#possible-updates)

## General Info

### Game Description
Given a mystery word and only a handful of wrong guesses granted to them, the player has their luck, intelligence, and vocabulary tested as they try to guess what a word is one letter at a time. If the player guesses a letter that's in the mystery word, then all instances of that letter are revealed. If they make a wrong guess, then the number of misses left ticks down one. The length of the word and the number of misses are adjusted based on the difficulty selected on the start menu. At any point during the game, saving the game to come back to later is possible and the game is loadable on the start menu. If the number of misses left ticks to zero, then "YOU LOSE" appears, saving is disabled, and the word the player couldn't guess is revealed to them. However, if the player guesses all the letters of the mystery word, then "YOU WIN!"
### Design
When first launched, the player is shown a start menu giving them the option to create a new game with a selected difficulty, load a previous game, minimize the window, or exit. Exiting and minimizing is done via the close button in the right of the title bar. A new game is created via the "Create Game" button and that game's difficulty is based on what the player leaves the difficulty slider, easy, intermediate, or hard. A previous game save is selectable from a list view with each stating the date and time of creation and coming with their own previously established difficulty, mystery word progress, and misses left. After selecting a save, clicking "load" recreates the game using the save file's data.

Upon creating or recreating the game, the player is shown various options for interacting with the game or leaving it. Any previously revealed letters of the mystery word and underscores for unguessed letters are in large text in the forefront of the window. The number of missed guesses remaining and a list of any previously guessed letters with accompanying labels are below the mystery word. Below that, a textbox for guessing a letter, the respective enter button, and a save game button. There's also a quit button that reveals the word, reveals the return to start button, and hides the save button. The title bar's minimize and close buttons remain unchanged.
### Methodology
#### Main Class
The program's classes are based upon Java FX + FXML Standards. The Part_Registry the main class only loads the FXML document, initializes the primary stage for the game, and launches the application. Following with the minimalistic design approach, both the start menu and the in-game UI are placed upon the same stage and UI elements' functionality and visibility are toggled depending on the application's current menu and user requests in each menu. By default, all in-game / post-start UI menu are disabled, most parts of the start menu UI are enabled, and the save game list and load selected elements are enabled upon clicking "Load" on the start menu.
#### FXML Document
In order to enhance the design process and decrease the time spent making small numerically changes, the FXML document that determines the design of the UI is primarily made using scenebuilder and the FXML document calls upon the typed FXML document controller .java class file to provide the code used to give the UI functionality.

#### FXML Document Controller
##### Member Variables
* difficulty - This is an integer representation of the game's current difficulty setting. Starting from the "easy" difficulty position and value, 1 (lowest allowed value), the start menu's difficulty slider can be adjusted to the middle third of the slider bar for the "intermediate" difficulty, to the right third of the bar for "hard," and the left third of the slider bar the slider starts in is "easy." These adjustments are made in the code by calling upon the difficulty AdjustDifficulty method.
* wordList - The text file containing all possible mystery words separated line-by-line. Should only contain words that are length 10, 8, or 6.
* tempWord - The word scanned most recently when parsing out the list of words into their respective difficulty word lists
* easyWords - A list of words(strings) from the word list determined be easy by their length being 10.
* interWords - A list of words(strings) from the word list determined be intermediate by their length being 8.
* hardWords - A list of words(strings) from the word list determined be hard by their length being 6.
* currGame - This is the game object that's being created in CreateGame or recreated in LoadSelected.

##### Methods
AdjustDifficulty - Upon the slider adjustment mouse event, this function is called to look at the value of the slider, the position, and set the difficulty variable to the corresponding difficulty integer value. The part of the slider bar the slider is in is determined by comparing the slider value to the values of the bar at each fraction across itself.

CreateGame - After using a buffered reader to read the word list file and filter each word of the current difficult selected into the corresponding word list, the list of words is used to initial the new selectedWords list to avoid writing a new block of code for each difficulty or a new method. At the same time, a somewhat arbitrary number of missed guesses set based on difficulty; easy is 10, intermediate is 7, and hard is 4. A random index corresponding to the selected word list is then generated and that along with the number of missed guesses are used to construct a new game object and set currGame to it. The rest of the method is just toggling off start menu UI elements and toggling on in-game UI elements.

LoadGame - Is triggered by clicking the "Load" button. The "load" button is turned off, the list view is populated, and the list view and "Load Selected" button are turned on.

LoadSelected - Looks at the save file selected in the list view line by line for the saved game's members saved to each, parses into variables, and uses them to construct a "new" game for currGame. The start menu UI elements are then toggled off and in-game UI elements toggled on.

QuitGame & ReturnToStart - Quitting just displays the full mystery word and makes the window's only usable UI be the "Return" button which toggles the in-game menu off and start menu on with ReturnToStart.

SaveGame - Saving is handled by the object with the game's private members, the current game object itself.

#### Game
An object used to handle all the actual game-data, the mystery word, what parts of the word are revealed, number of misses left, and the previously guessed letters. Constructors are simply set the class' variables. The only other variable setting occurs in guess (char guess). The number of missed guesses can be decremented, _'s can be changed to the corresponding letters in the mystery word, and guessed characters are added each time. Each variable has a corresponding getter. Lastly, save uses buffered writer and file writer to create a simple, easy to read and write file with each of the game's variables on a line.

## Technologies
Used to create Guess Word:
* JDK 14.0.2
* OpenJFX 14.0.2.1
* SceneBuilder 11.0.1

## Possible Updates
Here is a list of bug fixes, changes, or new additions that may come:
* Create a score system in which right guesses net points, wins net completion bonus points, perfect wins (no missed guesses) net more bonus points, incorrect guesses cause deductions in score, losses cause more deductions, score gain multipliers depending on difficulty, score gets saved along with other data, words are no longer revealed after quitting, mystery word is revealed upon (causing a loss), and with graphical changes in GUI made to accommodate these changes
* Consolidate each difficulty's word list into one selectedWord list and remove its declaration in CreateGame
* CreateGame can be split into more methods to prevent difficulty-based code repetition.
* Create a disable start menu and enable in-game menu function to remove repeating code when creating and loading games