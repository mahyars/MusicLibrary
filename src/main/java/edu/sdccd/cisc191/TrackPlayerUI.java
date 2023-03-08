package edu.sdccd.cisc191;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class TrackPlayerUI extends HBox {
    private MusicTrack currentTrack;
    private boolean isPlaying = false;
    private Slider trackSlider;
    private Label timeLabel;

    public TrackPlayerUI() {
        // Create UI components
        Button playPauseButton = new Button("Play");
        Button stopButton = new Button("Stop");
        Button nextButton = new Button("Next");
        Button rewindButton = new Button("Rewind");
        trackSlider = new Slider();
        timeLabel = new Label("0:00");

        // Set the style of the buttons using the Styles class
        Styles.setButtonStyle(stopButton);
        Styles.setButtonStyle(nextButton);
        Styles.setButtonStyle(rewindButton);
        Styles.setButtonStyle(playPauseButton);

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

        // Set event handler for the stop button
        stopButton.setOnAction(event -> {
            if (currentTrack != null) {
                currentTrack.stop();
                playPauseButton.setText("Play");
                trackSlider.setValue(0);
                isPlaying = false;
            }
        });

        // Set event handler for the next button
        nextButton.setOnAction(event -> {
            if (currentTrack != null) {
                // Get the file path of the next track
                // (I need to replace this with my own logic for selecting the next track)
                String nextFilePath = "...";

                // Play the next track
                currentTrack.next(nextFilePath);
            }
        });

        // Set event handler for the rewind button
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

        // Create an HBox container to hold the buttons and track slider
        setAlignment(Pos.CENTER);
        setSpacing(15);
        getChildren().addAll(rewindButton, stopButton, playPauseButton, nextButton, trackSlider, timeLabel);
    }

    // Set the current track
    public void setCurrentTrack(MusicTrack track) {
        currentTrack = track;
        if (currentTrack != null) {
            timeLabel.setText("0:00/" + currentTrack.getTrackDuration());
        } else {
            timeLabel.setText("0:00");
        }
    }

    // Update the track position and time label
    public void updateTrackPosition() {
        if (currentTrack != null) {
            double position = currentTrack.getTrackPosition();
            trackSlider.setValue(position / currentTrack.getTrackDuration() * 100);
            timeLabel.setText(currentTrack.getTrackPosition() + "/" + currentTrack.getTrackDuration());
        }
    }
}