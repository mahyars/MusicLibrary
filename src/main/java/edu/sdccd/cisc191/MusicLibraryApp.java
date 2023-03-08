package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MusicLibraryApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a new MusicLibrary object
        MusicLibrary library = new MusicLibrary();

        // Create UI components
        MusicManagerUI managerUI = new MusicManagerUI(library);
        MusicDisplayUI displayUI = new MusicDisplayUI();
        TrackPlayerUI playerUI = new TrackPlayerUI();

        // Set up the main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10, 10, 10, 10));

        // Set the Horizontal grow and Vertical grow constraints for the UI elements
        VBox.setVgrow(managerUI, Priority.ALWAYS);
        VBox.setVgrow(displayUI, Priority.ALWAYS);
        HBox.setHgrow(playerUI, Priority.ALWAYS);

        // Add the UI components to the main layout
        mainLayout.setLeft(managerUI);
        mainLayout.setCenter(displayUI);
        mainLayout.setBottom(playerUI);

        // Create the Scene
        Scene scene = new Scene(mainLayout, 800, 600);


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