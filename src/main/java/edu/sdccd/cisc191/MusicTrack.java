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
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String filepath;
    private int duration;
    private int year;
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
        this.duration = 0;
        this.year = 0;
        this.player = null;
        this.filepath = resourceName;

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

    private String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%d:%02d", minutes, seconds);
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

            // Add a listener to update the labels as the track progresses
            player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                Duration currentTime = newValue;
                Duration totalTime = player.getTotalDuration();
                String timeString = formatDuration(currentTime) + " / " + formatDuration(totalTime);
                if (timeLabel != null) {
                    timeLabel.setText(timeString);
                }
            });
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

    // Method to play the next track
    public void next(String nextFilePath) {
        if (player != null) {
            // Stop the current player
            player.stop();

            // Create a new player for the next track and start playing it
            Media media = new Media(new File(nextFilePath).toURI().toString());
            player = new MediaPlayer(media);
            player.setOnReady(() -> {
                // get duration of the track in seconds
                this.trackDuration = player.getTotalDuration().toSeconds();
                player.play();
            });
            player.setOnError(() -> {
                System.err.println("Failed to create MediaPlayer");
            });
        }
    }

    // Method to rewind the track
    public void rewind() {
        if (player != null) {
            player.seek(player.getStartTime());
            player.play();
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

    public String getFilepath() {
        return filepath;
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