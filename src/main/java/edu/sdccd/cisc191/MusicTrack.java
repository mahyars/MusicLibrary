package edu.sdccd.cisc191;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MusicTrack implements Serializable, Comparable<MusicTrack> {
    // Instance variables
    private final String title;
    private final String artist;
    private final String album;
    private final String genre;
    private final String filepath;
    private final int duration;
    private final int year;
    private static MediaPlayer mediaPlayer;
    private double trackDuration;

    // Constructor that takes the title, artist, album, genre, and file path of the track
    public MusicTrack(String title, String artist, String album, String genre, String filepath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = 0;
        this.year = 0;
        //this.player = null;
        this.filepath = filepath.replace("\\","/");

        // Create a new media player from the file path, if the file exists
        if (Files.exists(Paths.get(this.filepath))) {
            // Set the media player to play the track
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(new Media(this.filepath));
            // get duration of the track in seconds
            this.trackDuration = mediaPlayer.getTotalDuration().toSeconds();
        } else {
            throw new IllegalArgumentException("Invalid file path: " + this.filepath);
        }
    }

    // Method to play the track
    public void play() {
        mediaPlayer.play();
    }

    // Method to pause the track
    public void pause() {
        mediaPlayer.pause();
    }

    // Method to stop the track
    public void stop() {
        mediaPlayer.stop();
    }

    // Method to play the next track
    public void next(String nextFilePath) {
        // Set the media player to play the next track
        mediaPlayer.stop();
        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(new Media(nextFilePath));
        // get duration of the track in seconds
        this.trackDuration = mediaPlayer.getTotalDuration().toSeconds();
        mediaPlayer.play();
    }

    // Method to rewind the track
    public void rewind() {
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.play();
    }

    // Method to release the resources used by the media player
    public void release() {
        mediaPlayer.dispose();
        mediaPlayer = null;
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

    public double getTrackPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentTime().toSeconds();
        } else {
            return 0.0;
        }
    }

    // Method to set the track position
    public void setTrackPosition(double duration) {

        mediaPlayer.seek(Duration.millis(duration));
    }
    @Override
    public int compareTo(MusicTrack other) {
        // Compare two MusicTrack objects by their track duration
        return Double.compare(this.getTrackDuration(), other.getTrackDuration());
    }
}
