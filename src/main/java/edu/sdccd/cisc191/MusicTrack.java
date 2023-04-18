package edu.sdccd.cisc191;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicTrack {
    // Instance variables for the title, artist, album, genre, file path, and media player...
    private final String title;
    private final String artist;
    private final String album;
    private final String genre;
    private MediaPlayer player;
    private double trackDuration;
    private Label timeLabel;
    private Slider trackSlider;


    // Constructor that takes the title, artist, album, genre, and file path of the track
    public MusicTrack(String title, String artist, String album, String genre, String resourceName) {
        System.out.println("Resource name: " + resourceName);

        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.player = null;

        // Create a new media player from the file path, if the file exists
        File resourceFile = new File(resourceName);
        if (resourceFile.exists()) {
            Media media = new Media(resourceFile.toURI().toString());
            this.player = new MediaPlayer(media);
            this.player.setOnReady(() -> {
                // get duration of the track in seconds
                this.trackDuration = player.getTotalDuration().toSeconds();
            });
            this.player.setOnError(() -> {
                Throwable error = player.getError();
                System.err.println("Error occurred in MediaPlayer: " + error.getMessage());
                error.printStackTrace();
            });
        } else {
            System.err.println("Resource not found: " + resourceName);
        }
    }

    public void setUIControls(Slider trackSlider, Label timeLabel) {
        this.trackSlider = trackSlider;
        this.timeLabel = timeLabel;
        updateCurrentTime();
    }

    public void bindSliderToPlayer(Slider trackSlider) {
        // Bind the slider's maximum value to the track duration
        trackSlider.maxProperty().bind(Bindings.createDoubleBinding(() ->
                        player.getTotalDuration().toSeconds(),
                player.totalDurationProperty()));

        // Bind the slider's value to the current time of the player
        trackSlider.valueProperty().bind(Bindings.createDoubleBinding(() ->
                        player.getCurrentTime().toSeconds(),
                player.currentTimeProperty()));
    }

    private void updateCurrentTime() {
        if (player != null) {
            player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                double currentTime = newValue.toSeconds();
                double totalTime = getTrackDuration();
                if (timeLabel != null) {
                    timeLabel.setText(String.format("%d:%02d / %d:%02d",
                            (int) currentTime / 60, (int) currentTime % 60,
                            (int) totalTime / 60, (int) totalTime % 60));
                }
                if (trackSlider != null) {
                    trackSlider.setValue(currentTime);
                }
            });
        }
    }


    // Method to play the track
    public void play() {
        if (player != null) {
            System.out.println("Playing track: " + getTitle());
            player.play();
        }
    }

    // Method to pause the track
    public void pause() {
        if (player != null) {
            System.out.println("Pausing track: " + getTitle());
            player.pause();
        }
    }

    // Method to stop the track
    public void stop() {
        if (player != null) {
            player.stop();
        }
    }

    // Getter methods for the title, artist, album, genre, and file path of the track
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    // Getter method for the duration of the track
    public double getTrackDuration() {
        return trackDuration;
    }

    // Method to set the track position
    public void setTrackPosition(double duration) {
        player.seek(Duration.millis(duration));
    }
}