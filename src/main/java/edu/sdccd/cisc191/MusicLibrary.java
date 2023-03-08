package edu.sdccd.cisc191;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicLibrary implements Serializable, Comparable<MusicLibrary> {
    // 2D array to store tracks by genre and album
    private MusicTrack[][] tracks;

    public MusicLibrary() {
        // Initialize the 2D array with 10 genres and 10 albums per genre
        tracks = new MusicTrack[10][10];
    }

    public void addTrack(MusicTrack track) {
        // Add the track to the 2D array
        String genre = track.getGenre();
        int genreIndex = getGenreIndex(genre);

        if (genreIndex == -1) {
            // The genre does not exist in the 2D array, so add a new row for it
            int newRow = tracks.length;
            MusicTrack[] newGenreArray = {new MusicTrack(genre, "", "", "", "")};
            tracks = Arrays.copyOf(tracks, newRow + 1);
            tracks[newRow] = newGenreArray;
            genreIndex = newRow;
        }
        // Add the track to the 2D array
        int newLength = tracks[genreIndex].length + 1;
        tracks[genreIndex] = Arrays.copyOf(tracks[genreIndex], newLength);
        tracks[genreIndex][newLength - 1] = track;
    }

    public void removeTrack(MusicTrack track) {
        // Remove the track from the 2D array
        String genre = track.getGenre();
        int genreIndex = getGenreIndex(genre);
        if (genreIndex != -1) {
            for (int i = 1; i < tracks[genreIndex].length; i++) {
                if (tracks[genreIndex][i].equals(track)) {
                    int lastColumn = tracks[genreIndex].length - 1;
                    MusicTrack[] newTrackArray = new MusicTrack[lastColumn];
                    for (int j = 1, k = 0; j < tracks[genreIndex].length; j++, k++) {
                        if (j == i) {
                            k--;
                        } else {
                            newTrackArray[k] = tracks[genreIndex][j];
                        }
                    }
                    tracks[genreIndex] = newTrackArray;
                    break;
                }
            }
        }
    }

    public List<MusicTrack> getTracks() {
        // Get all tracks from the 2D array
        List<MusicTrack> allTracks = new ArrayList<>();
        for (int i = 0; i < tracks.length; i++) {
            for (int j = 0; j < tracks[i].length; j++) {
                if (tracks[i][j] != null) {
                    allTracks.add(tracks[i][j]);
                }
            }
        }
        return allTracks;
    }

    public String[] getGenres() {
        // Create an array of all genres
        String[] genres = new String[tracks.length];
        for (int i = 0; i < genres.length; i++) {
            genres[i] = String.format("Genre %d", i + 1);
        }
        return genres;
    }

    public int getTrackCount() {
        // Get the total number of tracks in the library
        return getTracks().size();
    }

    public MusicTrack getTrack(int index) {
        // Get the track at the specified index from all genres and albums
        return getTracks().get(index);
    }

    public List<MusicTrack> getTracksByGenre(String genre) {
        // Get all tracks from a specific genre
        int genreIndex = getGenreIndex(genre);
        List<MusicTrack> genreTracks = new ArrayList<>();
        for (int i = 0; i < tracks[genreIndex].length; i++) {
            if (tracks[genreIndex][i] != null) {
                genreTracks.add(tracks[genreIndex][i]);
            }
        }
        return genreTracks;
    }

    public List<MusicTrack> getTracksByAlbum(String album) {
        // Get all tracks from a specific album
        List<MusicTrack> albumTracks = new ArrayList<>();
        for (int i = 0; i < tracks.length; i++) {
            for (int j = 0; j < tracks[i].length; j++) {
                if (tracks[i][j] != null && tracks[i][j].getAlbum().equals(album)) {
                    albumTracks.add(tracks[i][j]);
                }
            }
        }
        return albumTracks;
    }

    private int getGenreIndex(String genre) {
        // Get the index of the genre in the 2D array
        int genreIndex = -1;
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i][0] != null && tracks[i][0].getGenre().equals(genre)) {
                genreIndex = i;
                break;
            }
        }
        return genreIndex;
    }
    @Override
    public int compareTo(MusicLibrary other) {
        // Compare two MusicLibrary objects by their total number of tracks
        return Integer.compare(this.getTrackCount(), other.getTrackCount());
    }
}
