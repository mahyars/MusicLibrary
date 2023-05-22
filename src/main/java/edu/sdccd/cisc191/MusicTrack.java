package edu.sdccd.cisc191;
/*
  CISC191 Architect Assignment 3
  @author Mahyar saadati
 */
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
    private final String resourceName;
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
        this.resourceName = resourceName;
    }

        // Create a new media player from the file path, if the file exists
        public void createMediaPlayer() {
            // Create a new media player from the file path, if the file exists
            File resourceFile = new File(this.resourceName);
            if (resourceFile.exists()) {
                Media media = new Media(resourceFile.toURI().toString());
                this.player = new MediaPlayer(media);

                /* This lambda expression doesn't take any arguments and doesn't return a result. It's a compact way of implementing
                 * the Runnable interface's run() method. The code inside the lambda expression gets executed when the MediaPlayer object
                 * is ready, i.e., it has enough media data to start playing.*/
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

    /** Module 14: Lambda Expressions
     * A lambda expression is used in the updateCurrentTime() method to add a listener
     * to the player's currentTimeProperty.
     * The listener updates the timeLabel and trackSlider to reflect the current time of the track.
     * This lambda expression takes three arguments (observable, oldValue, newValue) and doesn't return a result.
     * It's a compact way of implementing the ChangeListener interface's changed() method.
     */

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