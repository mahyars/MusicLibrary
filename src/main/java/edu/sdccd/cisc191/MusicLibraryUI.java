package edu.sdccd.cisc191;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.List;

public class MusicLibraryUI extends VBox {
    private MusicTrack currentTrack;
    private final TextField titleField = new TextField();
    private final Slider trackSlider;
    private final ListView<MusicTrack> trackListView;
    private int currentTrackIndex;
    private ChangeListener<Duration> timeListener;
    private final Label durationLabel;
    private Label timeLabel;

    public MusicLibraryUI(MusicLibrary library) {

        // Create UI components
        Label titleLabel = new Label("Title:");
        titleField.setEditable(false);
        Button playPauseButton = new Button("Play/Pause");
        Button stopButton = new Button("Stop");
        Button nextButton = new Button("Next");
        Button rewindButton = new Button("Rewind");
        trackSlider = new Slider();
        Label timeLabel = new Label("0:00");
        Button addTrackButton = new Button("Add Track");
        Button removeTrackButton = new Button("Remove Track");
        trackListView = new ListView<>();
        durationLabel = new Label("0:00");

        trackListView.setCellFactory(param -> new ListCell<MusicTrack>() {
            @Override
            protected void updateItem(MusicTrack item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        });

        // Add the listener
        trackListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCurrentTrack(newValue);
        });

        // Set the style of the play button using the Styles class
        Styles.setButtonStyle(stopButton);
        Styles.setButtonStyle(nextButton);
        Styles.setButtonStyle(rewindButton);
        Styles.setButtonStyle(playPauseButton);
        Styles.setButtonStyle(addTrackButton);
        Styles.setButtonStyle(removeTrackButton);

        // Initialize observableTracks with tracks from the library and set it as the items of the trackListView
        ObservableList<MusicTrack> observableTracks = FXCollections.observableArrayList(library.getTracks());
        trackListView.setItems(observableTracks); // Set items for trackListView

        // Add the trackListView to the UI
        getChildren().add(trackListView);

        // Add listener to update the currentTrack when a track is selected in the trackListView
        library.addListener((ListChangeListener.Change<? extends MusicTrack> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    observableTracks.addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    observableTracks.removeAll(change.getRemoved());
                }
            }
        });

        // Set event handler for the add track button
        addTrackButton.setOnAction(event -> {
            // Create a new MusicTrack object from the text fields and add it to the library
            MusicTrack newTrack = new MusicTrack(titleField.getText(), "Unknown Artist", "Unknown Album",
                    "Unknown Genre", "/path/to/file.mp3");
            library.addTrack(newTrack);
            observableTracks.add(newTrack);
        });

        // Set event handler for the remove track button
        removeTrackButton.setOnAction(event -> {
            // Get the selected item from the list view and remove it from the library
            MusicTrack selectedTrack = trackListView.getSelectionModel().getSelectedItem();
            if (selectedTrack != null) {
                library.removeTrack(selectedTrack);
                observableTracks.remove(selectedTrack);
            }
        });

        /*Create a choice box for the genres and set an event handler to update
        the trackListView with the tracks for the selected genre*/
        String[] genres = library.getGenres();
        ChoiceBox<String> genreChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(genres));
        genreChoiceBox.setOnAction(event -> {
            // Get the selected genre
            String selectedGenre = genreChoiceBox.getValue();
            // Get the list of tracks for the selected genre
            List<MusicTrack> tracks = library.getTracksByGenre(selectedGenre);
            // Update the trackListView with the tracks for the selected genre
            trackListView.setItems(FXCollections.observableArrayList(tracks));
        });

        // Set event handler for the play/pause button
        playPauseButton.setOnAction(event -> {
            if (currentTrack != null) {
                if (currentTrack.getPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                    currentTrack.pause();
                    playPauseButton.setText("Play");
                } else {
                    playTrack();
                    playPauseButton.setText("Pause");
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
                currentTrack.stop();
                currentTrack.getPlayer().currentTimeProperty().removeListener(timeListener);
                currentTrackIndex++;
                if (currentTrackIndex >= library.getTrackCount()) {
                    currentTrackIndex = 0;
                }
                setCurrentTrack(library.getTrack(currentTrackIndex));
                playTrack();
            }
        });

        rewindButton.setOnAction(event -> {
            if (currentTrack != null) {
                currentTrack.stop();
                currentTrack.getPlayer().currentTimeProperty().removeListener(timeListener);
                currentTrackIndex--;
                if (currentTrackIndex < 0) {
                    currentTrackIndex = library.getTrackCount() - 1;
                }
                setCurrentTrack(library.getTrack(currentTrackIndex));
                playTrack();
            }
        });

        // Set up the track slider
        trackSlider.setPrefWidth(200);
        trackSlider.setMin(0);
        trackSlider.setMax(100);
        trackSlider.setValue(0);
        trackSlider.setOnMousePressed(event -> {
            if (currentTrack != null) {
                currentTrack.getPlayer().currentTimeProperty().removeListener(timeListener);
            }
        });

        trackSlider.setOnMouseReleased(event -> {
            if (currentTrack != null) {
                double position = trackSlider.getValue() / 100 * currentTrack.getTrackDuration();
                currentTrack.setTrackPosition(position);
                currentTrack.getPlayer().currentTimeProperty().addListener(timeListener);
            }
        });

        HBox.setHgrow(trackSlider, Priority.ALWAYS);

        // Create an HBox container to hold the buttons and track slider
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.getChildren().addAll(rewindButton, stopButton, playPauseButton, nextButton);

        //Create and HBox container to hole the time stamp
        HBox timeStamp = new HBox();
        timeStamp.setAlignment(Pos.CENTER);
        //timeStamp.getChildren().addAll(timeLabel);
        timeStamp.getChildren().addAll(timeLabel, new Label("/"), durationLabel);

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

    public Slider getTrackSlider() {
        return trackSlider;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    private void updateSliderPosition() {
        if (currentTrack != null) {
            timeListener = (observable, oldValue, newValue) -> {
                trackSlider.setValue(newValue.toSeconds() / currentTrack.getTrackDuration() * 100);
                // Update the timeLabel with the current position of the track
                int currentPosition = (int) newValue.toSeconds();
                int currentPositionMinutes = currentPosition / 60;
                int currentPositionSeconds = currentPosition % 60;
                timeLabel.setText(String.format("%d:%02d", currentPositionMinutes, currentPositionSeconds));
            };
            currentTrack.getPlayer().currentTimeProperty().addListener(timeListener);
        }
    }


    // Set the current track
    public void setCurrentTrack(MusicTrack track) {
        // If there's a current track playing, stop it
        if (currentTrack != null) {
            try {
                currentTrack.stop();
                currentTrack.getPlayer().currentTimeProperty().removeListener(timeListener);
            } catch (Exception e) {
                System.err.println("Error stopping the current track: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Set the new current track
        currentTrack = track;

        // Check if the track is not null, to handle possible errors
        if (currentTrack != null) {
            try {
                // Set the UI controls (slider and label) for the new track
                currentTrack.setUIControls(trackSlider, timeLabel);
                // Bind the slider to the new track's media player
                currentTrack.bindSliderToPlayer(trackSlider);

                // Set the timeListener
                timeListener = (observable, oldValue, newValue) -> {
                    trackSlider.setValue(newValue.toSeconds() / currentTrack.getTrackDuration() * 100);

                    // Update the durationLabel with the total duration of the track
                    int totalDuration = (int) currentTrack.getTrackDuration();
                    int durationMinutes = totalDuration / 60;
                    int durationSeconds = totalDuration % 60;
                    durationLabel.setText(String.format("%d:%02d", durationMinutes, durationSeconds));
                };

                // Set the new current track
                currentTrack = track;

                // Update titleLabel's text
                if (currentTrack != null) {
                    titleField.setText(currentTrack.getTitle());
                } else {
                    titleField.setText("");
                }

                // Add the timeListener to the new track's media player
                currentTrack.getPlayer().currentTimeProperty().addListener(timeListener);

            } catch (Exception e) {
                System.err.println("Error setting UI controls or binding slider: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: MusicTrack is null in setCurrentTrack");
        }
    }
    private void playTrack() {
        if (currentTrack != null) {
            currentTrack.bindSliderToPlayer(trackSlider);
            currentTrack.play();
        }
    }
}