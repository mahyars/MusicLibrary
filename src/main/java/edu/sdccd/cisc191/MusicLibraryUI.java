package edu.sdccd.cisc191;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private final TableView<MusicTrack> trackTableView;
    private int currentTrackIndex;
    private ChangeListener<Duration> timeListener;
    private final Label durationLabel;
    private Label timeLabel;

    public MusicLibraryUI(MusicLibrary library) {

        // Initialize components
        trackSlider = new Slider();
        trackTableView = new TableView<>();
        durationLabel = new Label("0:00");
        timeLabel = new Label("0:00");

        // Create and configure UI components
        Label titleLabel = new Label("Title:");
        titleField.setEditable(false);
        Button playPauseButton = new Button("Play/Pause");
        Button stopButton = new Button("Stop");
        Button nextButton = new Button("Next");
        Button rewindButton = new Button("Rewind");
        Button addTrackButton = new Button("Add Track");
        Button removeTrackButton = new Button("Remove Track");
        ChoiceBox<String> genreChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(library.getGenres()));

        // Set styles
        Styles.setButtonStyle(stopButton);
        Styles.setButtonStyle(nextButton);
        Styles.setButtonStyle(rewindButton);
        Styles.setButtonStyle(playPauseButton);
        Styles.setButtonStyle(addTrackButton);
        Styles.setButtonStyle(removeTrackButton);

        // Initialize observableTracks and set it as the items of the trackListView
        ObservableList<MusicTrack> observableTracks = FXCollections.observableArrayList(library.getTracks());
        trackTableView.setItems(observableTracks);

        TableColumn<MusicTrack, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<MusicTrack, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<MusicTrack, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        trackTableView.getColumns().addAll(titleColumn, albumColumn, artistColumn);

        // Add the trackListView to the UI
        getChildren().add(trackTableView);

        // Listeners and event handlers
        trackTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCurrentTrack(newValue);
        });

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
            MusicTrack newTrack = new MusicTrack(titleField.getText(), "Unknown Artist", "Unknown Album",
                    "Unknown Genre", "/path/to/file.mp3");
            library.addTrack(newTrack);
            observableTracks.add(newTrack);
        });

        // Set event handler for the remove track button
        removeTrackButton.setOnAction(event -> {
            MusicTrack selectedTrack = trackTableView.getSelectionModel().getSelectedItem();
            if (selectedTrack != null) {
                library.removeTrack(selectedTrack);
                observableTracks.remove(selectedTrack);
            }
        });

        /*Create a choice box for the genres and set an event handler to update
        the trackListView with the tracks for the selected genre*/
        genreChoiceBox.setOnAction(event -> {
            String selectedGenre = genreChoiceBox.getValue();
            List<MusicTrack> tracks = library.getTracksByGenre(selectedGenre);
            trackTableView.setItems(FXCollections.observableArrayList(tracks));
        });

        // Set event handler for the play/pause button
        playPauseButton.setOnAction(event -> {
            if (currentTrack != null) {
                if (currentTrack.getPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                    currentTrack.pause();
                    playPauseButton.setText("\u25B6");
                } else {
                    playTrack();
                    playPauseButton.setText("\u23F8");
                }
            }
        });

        // Set event handler for the stop button
        stopButton.setOnAction(event -> {
            if (currentTrack != null) {
                currentTrack.stop();
                playPauseButton.setText("\u25B6");
                trackSlider.setValue(0);
            }
        });

        // Set event handler for the next button
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

        // Set event handler for the rewind button
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

        // Set up the time listener
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

        //Create an HBox container to hold the time stamp
        HBox timeStamp = new HBox();
        timeStamp.setAlignment(Pos.CENTER);
        timeStamp.setSpacing(5);
        timeStamp.getChildren().clear();
        timeStamp.getChildren().add(timeLabel);

        // Create a VBox container to hold the title, text field, and button box
        VBox contentBox = new VBox();
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(titleLabel, titleField, trackSlider, timeStamp, buttonBox);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Center the VBox container
        setAlignment(Pos.CENTER);

        /** GUI for the sorting options.
         * The ComboBox control allows the user to select an option from a predefined list of items.
         * The ComboBox is used for selecting a sorting attribute for the music tracks.
         */

        ComboBox<MusicLibrary.TrackAttribute> sortComboBox = new ComboBox<>();
        ObservableList<MusicLibrary.TrackAttribute> sortOptions = FXCollections.observableArrayList(
                MusicLibrary.TrackAttribute.TITLE, MusicLibrary.TrackAttribute.ARTIST, MusicLibrary.TrackAttribute.ALBUM
        );
        sortComboBox.setItems(sortOptions);
        sortComboBox.getSelectionModel().selectFirst(); // Select the first option by default

        // Create a Button for triggering the sorting
        Button sortButton = new Button("Sort");

        // Set the event handler for the sort button
        sortButton.setOnAction(event -> {
            MusicLibrary.TrackAttribute selectedAttribute = sortComboBox.getSelectionModel().getSelectedItem();
            List<MusicTrack> sortedTracks = library.sortTracksByAttribute(selectedAttribute);

            // Update the UI (e.g., ListView or TableView) with the sorted tracks
            trackTableView.getItems().setAll(sortedTracks);
        });

        // Create an HBox container to hold the sort ComboBox and Button
        HBox sortBox = new HBox();
        sortBox.setAlignment(Pos.CENTER);
        sortBox.setSpacing(10);
        sortBox.getChildren().addAll(sortComboBox, sortButton);

        // Add the HBox container to the contentBox
        contentBox.getChildren().add(sortBox);

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