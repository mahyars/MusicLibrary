package edu.sdccd.cisc191;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

public class MusicTrack {
    // Instance variables for the title, artist, album, genre, file path, and media player
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String filepath;
    private int duration;
    private int year;
    private MediaPlayer player;
    private double trackDuration;

    // Constructor that takes the title, artist, album, genre, and file path of the track
    public MusicTrack(String title, String artist, String album, String genre, String filepath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = 0;
        this.year = 0;
        this.player = null;
        this.filepath = filepath.replace("\\","/");

        // Create a new media player from the file path, if the file exists
        if (new File(this.filepath).exists()) {
            this.player = new MediaPlayer(new Media(this.filepath));
            // get duration of the track in seconds
            this.trackDuration = player.getTotalDuration().toSeconds();
        } else {
            throw new IllegalArgumentException("Invalid file path: " + this.filepath);
        }
    }

    // Method to play the track
    public void play() {
        if (player != null) {
            player.play();
        }
    }

    // Method to pause the track
    public void pause() {
        if (player != null) {
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
            player = new MediaPlayer(new Media(nextFilePath));
            // get duration of the track in seconds
            this.trackDuration = player.getTotalDuration().toSeconds();
            player.play();
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
