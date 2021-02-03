module guess_word {
	requires java.base;
	requires javafx.base;
	requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens gamePack.guess_word to javafx.fxml;
    exports gamePack.guess_word;
}