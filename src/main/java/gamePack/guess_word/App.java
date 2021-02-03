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

import javafx.application.Application;
//import javafx.application.Application.launch;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**Main gamePack class that launches the application via loading the 
 * FXMLDocument
 * 
 * @author Dawson C. Branch
 * @version 1.0.2
 * @since 1.0.0
 */
public class App extends Application {
    /**Start is the main entry point for JavaFX applications.
     * 
     * @param stage - Stage is the primary stage and can be used for setting the 
     * application's scene
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Guess Word");
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }
    
    /**Launches the stand-alone Guess Word game application. Returns upon exiting
     * the application
     * 
     * @param args - Array of inputed command line arguments for informing 
     * program how to run or of info used to run a certain way
     */
    public static void main(String[] args) {
        launch(args);
    }
}