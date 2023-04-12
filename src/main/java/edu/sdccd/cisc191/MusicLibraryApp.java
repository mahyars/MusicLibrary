package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicLibraryApp extends Application {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {

        // Disable jaudiotagger logging messages
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);

        // create a new MusicLibrary object
        MusicLibrary library = new MusicLibrary();

        MetadataReader metadataReader = new MetadataReader();

        try {
            List<MusicTrack> trackList = metadataReader.readMetadataForAllTracks();
            // add the tracks to the library
            library.addTracks(trackList);
            System.out.println("trackList size: " + trackList.size() + "\n");

            // Call printLibraryInfo() to print the library information to the terminal
            library.printLibraryInfo();

        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        MusicLibraryUI ui = new MusicLibraryUI(library);
        Scene scene = new Scene(ui, WINDOW_WIDTH, WINDOW_HEIGHT);

        // get the list of tracks from the library
        List<MusicTrack> tracks = library.getTracks();
        // Sort tracks by title
        List<MusicTrack> sortedByTitle = MusicLibrary.sortTracksByAttribute(MusicLibrary.TrackAttribute.TITLE);

        // Sort tracks by artist
        List<MusicTrack> sortedByArtist = MusicLibrary.sortTracksByAttribute(MusicLibrary.TrackAttribute.ARTIST);

        // Sort tracks by album
        List<MusicTrack> sortedByAlbum = MusicLibrary.sortTracksByAttribute(MusicLibrary.TrackAttribute.ALBUM);

        if (!tracks.isEmpty()) {
            // Get the first track from the list
            MusicTrack firstTrack = tracks.get(0);

            // Call setUIControls with trackSlider and timeLabel from MusicLibraryUI
            firstTrack.setUIControls(ui.getTrackSlider(), ui.getTimeLabel());
        }

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