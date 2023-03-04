package edu.sdccd.cisc191;

import java.util.*;

public class MusicLibrary {
    // Create a 2D array to store tracks by genre
    private Map <String, List<MusicTrack>> tracksByGenre;

    public MusicLibrary() {
        tracksByGenre = new HashMap<>();
    }

    public void addTrack(MusicTrack track) {
        // Add track to the corresponding genre array in the map
        String genre = track.getGenre();
        List<MusicTrack> tracks = tracksByGenre.getOrDefault(genre, new ArrayList<>());
        tracks.add(track);
        tracksByGenre.put(genre, tracks);
    }

    public void removeTrack(MusicTrack track) {
        // Remove track from the corresponding genre array in the map
        String genre = track.getGenre();
        List<MusicTrack> tracks = tracksByGenre.get(genre);
        if (tracks != null) {
            tracks.remove(track);
            tracksByGenre.put(genre, tracks);
        }
    }

    public List<MusicTrack> getTracks() {
        // Get all tracks from all genres
        List<MusicTrack> allTracks = new ArrayList<>();
        for (List<MusicTrack> tracks : tracksByGenre.values()) {
            allTracks.addAll(tracks);
        }
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
        return tracksByGenre.getOrDefault(genre, new ArrayList<>());
    }
}
