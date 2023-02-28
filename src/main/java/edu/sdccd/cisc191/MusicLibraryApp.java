package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MusicLibraryApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // create a new MusicLibrary object
        MusicLibrary library = new MusicLibrary();
        MusicLibraryUI ui = new MusicLibraryUI(library);
        Scene scene = new Scene(ui, 400, 500);

        // create some sample tracks
        MusicTrack track1 = new MusicTrack("Title 1",
                "Artist 1",
                "Album 1",
                "Genre 1",
                "C:\\Users\\admin\\Music\\NoLeafClover.mp3");
        MusicTrack track2 = new MusicTrack("Title 2",
                "Artist 2",
                "Album 2",
                "Genre 2",
                "C:\\Users\\admin\\Music\\SadButTrue.mp3");
        MusicTrack track3 = new MusicTrack("Title 3",
                "Artist 3",
                "Album 3",
                "Genre 3",
                "C:\\Users\\admin\\Music\\TheUnnamedFeeling.mp3");

        // add the tracks to the library
        library.addTrack(track1);
        library.addTrack(track2);
        library.addTrack(track3);

        // remove a track from the library
        library.removeTrack(track2);

        // get the list of tracks from the library
        List<MusicTrack> tracks = library.getTracks();

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