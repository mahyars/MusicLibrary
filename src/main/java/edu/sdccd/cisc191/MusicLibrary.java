package edu.sdccd.cisc191;

import java.util.ArrayList;
import java.util.List;

public class MusicLibrary {
    // the list of music tracks in the library
    private List<MusicTrack> tracks;

    // initialize the list of music tracks to an empty ArrayList
    public MusicLibrary() {
        tracks = new ArrayList<>();
    }

    // Add a music track to the library
    public void addTrack(MusicTrack track) {
        // initialize the list of music tracks to an empty ArrayList
        tracks.add(track);
    }

    // Remove a music track from the library
    public void removeTrack(MusicTrack track) {
        // remove the music track from the list of music tracks
        tracks.remove(track);
    }

    // Get the list of music tracks in the library
    public List<MusicTrack> getTracks() {
        // return the list of music tracks
        return tracks;
    }

    // Get the number of music tracks in the library
    public int getTrackCount() {
        // return the number of music tracks in the list
        return tracks.size();
    }

    // Get a music track from the library by index
    public MusicTrack getTrack(int index) {
        // return the music track at the specified index in the list
        return tracks.get(index);
    }
}