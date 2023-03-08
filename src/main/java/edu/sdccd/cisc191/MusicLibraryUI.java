/*
package edu.sdccd.cisc191;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
public class MusicLibraryUI extends VBox {
    private MusicTrack currentTrack;
    private TextField titleField;
    private boolean isPlaying = false;
    private Slider trackSlider;
    private Label timeLabel;
    private MusicLibrary library;
    private ListView<MusicTrack> trackListView;
    private Button addTrackButton;
    private Button removeTrackButton;


    public MusicLibraryUI(MusicLibrary library) {

        // Add reference to MusicLibrary instance
        this.library = library;

        // Create UI components
        Label titleLabel = new Label("Title:");
        titleField = new TextField("test");
        Button playPauseButton = new Button("Play/Pause");
        Button stopButton = new Button("Stop");
        Button nextButton = new Button("Next");
        Button rewindButton = new Button("Rewind");
        trackSlider = new Slider();
        timeLabel = new Label("0:00");
        addTrackButton = new Button("Add Track");
        removeTrackButton = new Button("Remove Track");
        trackListView = new ListView<MusicTrack>();

        // Create an ObservableList from the ArrayList returned by library.getTracks()
        ObservableList<MusicTrack> observableTracks = FXCollections.observableArrayList(library.getTracks());

        // Set the ListView items to the new ObservableList
        trackListView.setItems(observableTracks);

        // Set the style of the play button using the Styles class
        Styles.setButtonStyle(stopButton);
        Styles.setButtonStyle(nextButton);
        Styles.setButtonStyle(rewindButton);
        Styles.setButtonStyle(playPauseButton);
        Styles.setButtonStyle(addTrackButton);
        Styles.setButtonStyle(removeTrackButton);

        // Set event handler for the add track button
        addTrackButton.setOnAction(event -> {
            // Create a new MusicTrack object from the text fields and add it to the library
            MusicTrack newTrack = new MusicTrack(titleField.getText(), "Unknown Artist", "Unknown Album",
                    "Unknown Genre", "/path/to/file.mp3");
            library.addTrack(newTrack);
        });

        // Set event handler for the remove track button
        removeTrackButton.setOnAction(event -> {
            // Get the selected item from the list view and remove it from the library
            MusicTrack selectedTrack = trackListView.getSelectionModel().getSelectedItem();
            if (selectedTrack != null) {
                library.removeTrack(selectedTrack);
            }
        });


        // Set event handler for the play/pause button
        playPauseButton.setOnAction(event -> {
            if (currentTrack != null) {
                if (isPlaying) {
                    currentTrack.pause();
                    playPauseButton.setText("Play");
                    isPlaying = false;
                } else {
                    currentTrack.play();
                    playPauseButton.setText("Pause");
                    isPlaying = true;
                }
            }
        });

        stopButton.setOnAction(event -> {
            if (currentTrack != null) {
                currentTrack.stop();
                playPauseButton.setText("Play");
                trackSlider.setValue(0);
            }
        });

        nextButton.setOnAction(event -> {
            if (currentTrack != null) {
                // Get the file path of the next track
                // (I need to replace this with my own logic for selecting the next track)
                String nextFilePath = "...";

                // Play the next track
                currentTrack.next(nextFilePath);
            }
        });

        rewindButton.setOnAction(event -> {
            if (currentTrack != null) {
                currentTrack.rewind();
            }
        });

        // Set up the track slider
        trackSlider.setPrefWidth(200);
        trackSlider.setMin(0);
        trackSlider.setMax(100);
        trackSlider.setValue(0);
        trackSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentTrack != null) {
                currentTrack.setTrackPosition(newValue.doubleValue() / 100 * currentTrack.getTrackDuration());
            }
        });

        HBox.setHgrow(trackSlider, Priority.ALWAYS);

        // Create an HBox container to hold the buttons and track slider
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.getChildren().addAll(rewindButton, stopButton, playPauseButton, nextButton);

        //Create and VBox container to hole the time stamp
        HBox timeStamp = new HBox();
        timeStamp.setAlignment(Pos.CENTER);
        timeStamp.getChildren().addAll(timeLabel);

        // Create a VBox container to hold the title, text field, and button box
        VBox contentBox = new VBox();
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(titleLabel, titleField, trackSlider, timeStamp, buttonBox);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Center the VBox container
        setAlignment(Pos.CENTER);

        // Add UI components to the VBox
        getChildren().addAll(contentBox);

        // Set the Hgrow and Vgrow constraints for the UI elements
        HBox.setHgrow(titleField, Priority.ALWAYS);
        VBox.setVgrow(titleField, Priority.ALWAYS);
        HBox.setHgrow(playPauseButton, Priority.ALWAYS);
        VBox.setVgrow(playPauseButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);
        VBox.setVgrow(stopButton, Priority.ALWAYS);
        HBox.setHgrow(nextButton, Priority.ALWAYS);
        VBox.setVgrow(nextButton, Priority.ALWAYS);
        HBox.setHgrow(rewindButton, Priority.ALWAYS);
        VBox.setVgrow(rewindButton, Priority.ALWAYS);
    }

    // Set the current track
    public void setCurrentTrack(MusicTrack track) {
        currentTrack = track;
        if (currentTrack != null) {
            titleField.setText(currentTrack.getTitle());
        } else {
            titleField.setText("");
        }
    }
}

*/
