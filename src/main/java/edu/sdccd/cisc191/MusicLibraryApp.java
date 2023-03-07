package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MusicLibraryApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // create a new MusicLibrary object
        MusicLibrary library = new MusicLibrary();
        MusicLibraryUI ui = new MusicLibraryUI(library);
        Scene scene = new Scene(ui, 400, 500);

        // Set the Horizontal grow and Vertical grow constraints for the UI elements
        HBox.setHgrow(ui, Priority.ALWAYS);
        VBox.setVgrow(ui, Priority.ALWAYS);

        // Set the title of the Stage
        primaryStage.setTitle("Music Library");

        // Set the resizable property of the Stage to true
        primaryStage.setResizable(true);

        // Set the Scene of the Stage
        primaryStage.setScene(scene);

        // Show the Stage
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}