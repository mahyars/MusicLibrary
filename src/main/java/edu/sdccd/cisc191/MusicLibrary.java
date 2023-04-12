package edu.sdccd.cisc191;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.PrintWriter;
import java.util.*;

/** Module 9 - Collections and Generics:
 * The MusicLibrary class represents a music library with a collection of music tracks.
 * It demonstrates the use of Collections and Generics in Java, using data structures such as
 * Map<String, List<MusicTrack>> and ObservableList<MusicTrack>.
 */
public class MusicLibrary {

    /**A map that stores tracks by genre, where the key is the genre and the value is a list of MusicTrack objects
     * This demonstrates the use of Generics and Collections (HashMap and ArrayList)
     * */
    private Map<String, List<MusicTrack>> tracksByGenre;

    /**An observable list that stores all the tracks in the music library
     * This demonstrates the use of Generics and Collections (ObservableList)
     * */
    private static ObservableList<MusicTrack> allTracks;

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

        //System.out.println("Tracks by genre '" + genre + "': " + tracks.size());
        return tracks;
    }


    /** Chapter 7: I/O Streams:
     * In this method, I used a PrintWriter to write the information to the terminal using I/O streams.
     * System.out is an instance of PrintStream, which is an output stream, so we can use it with
     * the PrintWriter. The true argument in the PrintWriter constructor enables auto-flushing,
     * which means the output will be automatically flushed to the terminal after each println() or
     * printf() call.*/
    public void printLibraryInfo() {
        PrintWriter out = new PrintWriter(System.out, true);

        out.println("Music Library:");
        out.println("--------------");
        String format = "%-20s %-30s %-30s %-30s%n";
        out.printf(format, "Genre", "Title", "Artist", "Album");
        out.println(String.join("", Collections.nCopies(115, "-")));

        for (String genre : getGenres()) {
            List<MusicTrack> tracks = getTracksByGenre(genre);
            out.println(genre);
            String indent = "  ";
            for (MusicTrack track : tracks) {
                out.printf(indent + format, "", track.getTitle(), track.getArtist(), track.getAlbum());
            }
        }
        out.println(String.join("", Collections.nCopies(115, "-")));
    }

    /** Chapter 11: Sorting:
     * The TrackAttribute enum represents various attributes of a music track
     * that can be used for sorting, such as TITLE, ARTIST, and ALBUM.
     */
    public enum TrackAttribute {
        TITLE, ARTIST, ALBUM
    }

    /**
     * The sortTracksByAttribute method is a utility method that sorts a list of
     * MusicTrack objects based on the specified TrackAttribute.
     *
     * @param attribute The TrackAttribute to sort by (e.g., TITLE, ARTIST, ALBUM).
     * @return A new list of MusicTrack objects sorted by the specified attribute.
     *
     * Usage:
     * 1. Call the sortTracksByAttribute method with a specific TrackAttribute:
     *    List<MusicTrack> sortedTracks = sortTracksByAttribute(TrackAttribute.TITLE);
     *
     * 2. Use the sorted list of tracks as needed, for example, update the UI
     *    with the sorted tracks:
     *    yourTrackListView.getItems().setAll(sortedTracks);
     */
    public static List<MusicTrack> sortTracksByAttribute(TrackAttribute attribute) {
        List<MusicTrack> sortedTracks = new ArrayList<>(allTracks);
        Comparator<MusicTrack> comparator;

        switch (attribute) {
            case TITLE:
                comparator = Comparator.comparing(MusicTrack::getTitle);
                break;
            case ARTIST:
                comparator = Comparator.comparing(MusicTrack::getArtist);
                break;
            case ALBUM:
                comparator = Comparator.comparing(MusicTrack::getAlbum);
                break;
            default:
                throw new IllegalArgumentException("Invalid track attribute for sorting.");
        }

        sortedTracks.sort(comparator);
        return sortedTracks;
    }
}