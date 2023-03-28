package edu.sdccd.cisc191;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.PrintWriter;
import java.util.*;

public class MusicLibrary {

    // Add a private field to store the tracks by genre
    private Map<String, List<MusicTrack>> tracksByGenre;
    private ObservableList<MusicTrack> allTracks;

    public MusicLibrary() {
        //tracks = FXCollections.observableArrayList();
        tracksByGenre = new HashMap<>();
        allTracks = FXCollections.observableArrayList();
    }

    // Add a method to add a listener to the tracks list
    public void addListener(ListChangeListener<MusicTrack> listener) {
        allTracks.addListener(listener);
    }

    public void addTrack(MusicTrack track) {
        // Add track to the corresponding genre array in the map
        String genre = track.getGenre();
        List<MusicTrack> tracks = tracksByGenre.getOrDefault(genre, new ArrayList<>());
        tracks.add(track);
        tracksByGenre.put(genre, tracks);
        System.out.println("Track added: " + track.getTitle() + " (" + genre + ")");

        // Add track to the observable list
        allTracks.add(track);
    }

    public void addTracks(List<MusicTrack> newTracks) {
        for (MusicTrack track : newTracks) {
            addTrack(track);
        }
    }

    public void removeTrack(MusicTrack track) {
        // Remove track from the corresponding genre array in the map
        String genre = track.getGenre();
        List<MusicTrack> tracks = tracksByGenre.get(genre);
        if (tracks != null) {
            tracks.remove(track);
            tracksByGenre.put(genre, tracks);
            System.out.println("Track removed: " + track.getTitle() + " (" + genre + ")");
        }
        // Remove track from the observable list
        allTracks.remove(track);
    }

    public List<MusicTrack> getTracks() {
        // Get all tracks from all genres
        return allTracks;
    }

    public String[] getGenres() {
        // Get the set of genres from the map
        Set<String> genreSet = tracksByGenre.keySet();

        // Convert the set to an array and return it
        String[] genres = new String[genreSet.size()];
        genreSet.toArray(genres);
        return genres;
    }

    public int getTrackCount() {
        // Get the total number of tracks in the library
        return getTracks().size();
    }

    public MusicTrack getTrack(int index) {
        // Get the track at the specified index from all genres
        return getTracks().get(index);
    }

    public List<MusicTrack> getTracksByGenre(String genre) {
        // Get all tracks from a specific genre
        List<MusicTrack> tracks = tracksByGenre.getOrDefault(genre, new ArrayList<>());

        System.out.println("Tracks by genre '" + genre + "': " + tracks.size());
        return tracks;
    }

    // Method to print the library information to a terminal using I/O streams

    /**In this method, I used a PrintWriter to write the information to the terminal using I/O streams.
     * System.out is an instance of PrintStream, which is an output stream, so we can use it with
     * the PrintWriter. The true argument in the PrintWriter constructor enables auto-flushing,
     * which means the output will be automatically flushed to the terminal after each println() or
     * printf() call.*/
    public void printLibraryInfo() {
        PrintWriter out = new PrintWriter(System.out, true);

        out.println("Music Library:");
        out.println("--------------");
        for (String genre : getGenres()) {
            List<MusicTrack> tracks = getTracksByGenre(genre);
            out.println("Genre: " + genre);
            for (MusicTrack track : tracks) {
                out.printf("  - Title: %s, Artist: %s, Album: %s\n",
                        track.getTitle(), track.getArtist(), track.getAlbum());
            }
        }
    }
}