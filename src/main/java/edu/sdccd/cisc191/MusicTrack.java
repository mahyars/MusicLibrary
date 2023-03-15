package edu.sdccd.cisc191;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;

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
    public MusicTrack(String title, String artist, String album, String genre, String resourceName) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = 0;
        this.year = 0;
        this.player = null;
        this.filepath = resourceName;

        // Create a new media player from the file path, if the file exists
        URL resourceUrl = getClass().getResource(resourceName);
        if (resourceUrl != null) {
            Media media = new Media(resourceUrl.toString());
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
            throw new IllegalArgumentException("Invalid file path: " + resourceName);
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

    // Getter method for the duration of the track
    public double getTrackDuration() {
        return trackDuration;
    }

    // Method to set the track position
    public void setTrackPosition(double duration) {
        player.seek(Duration.millis(duration));
    }
}

