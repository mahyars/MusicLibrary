package edu.sdccd.cisc191;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MetadataReader {
    // Method to read metadata for a single track
    public MusicTrack readMetadataForTrack(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                System.out.println("File not found: " + audioFilePath);
                return null;
            }
            AudioFile audio = AudioFileIO.read(audioFile);
            Tag tag = audio.getTag();

            // Get the title, artist, album, and genre from the tag
            String title = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            String genre = tag.getFirst(FieldKey.GENRE);

            // Create a new MusicTrack object and return it
            return new MusicTrack(title, artist, album, genre, audioFilePath);
        } catch (Exception e) {
            System.out.println("Error reading file: " + audioFilePath);
            e.printStackTrace();
        }
        return null;
    }
    // Method to read metadata for all tracks in the resources folder
    public List<MusicTrack> readMetadataForAllTracks() throws URISyntaxException {
        // Create a list to store the MusicTrack objects
        List<MusicTrack> tracks = new ArrayList<>();

        // Get the URL of the resources folder and URL to a file object
        URL resourceUrl = getClass().getClassLoader().getResource("");
        if (resourceUrl == null) {
            System.err.println("Resource folder not found");
            return tracks;
        }
        File resourcesFolder;
        try {
            resourcesFolder = new File(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            System.err.println("Invalid resource folder URI");
            return tracks;
        }

        //File resourcesFolder = new File(resourceUrl.getFile());

        System.out.println("Resources folder: " + resourcesFolder.getPath());

        // Get all the files in the resources folder that end with .mp3
        File[] mp3Files = resourcesFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        // Read metadata for each track and add it to the list
        if (mp3Files != null) {
            for (File audioFile : mp3Files) {
                System.out.println("Reading file: " + audioFile.getPath());
                MusicTrack track = readMetadataForTrack(audioFile.getPath());
                tracks.add(track);
            }
        }

        // Return the list of MusicTrack objects
        return tracks;
    }
}