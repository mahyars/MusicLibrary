package edu.sdccd.cisc191;
/*
  CISC191 Architect Assignment 3
  @author Mahyar saadati
 */

import javafx.application.Platform;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/** Module 9: Collections and Generics
 * The MetadataReader class is responsible for reading metadata from audio files (specifically MP3 files).
 * It uses the jaudiotagger library to extract metadata such as title, artist, album, and genre from the
 * audio files. The class also demonstrates the use of Collections and Generics in Java.
 */

public class MetadataReader {
    /*
     * Reads metadata for a single audio track given its file path.
     * The jaudiotagger library is used to extract the metadata, and a MusicTrack object is created
     * with the extracted information.
     *
     * @param audioFilePath The file path of the audio track to read metadata from.
     * @return A MusicTrack object containing the metadata, or null if there was an error reading the file.
     */
    public static MusicTrack readMetadataForTrack(String audioFilePath) {
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
    /** Module 13: Concurrency
     * Reads metadata for all audio tracks found in the resources folder in parallel.
     * This method demonstrates the use of Collections, Generics, and Concurrency in Java by creating a List of MusicTrack
     * objects. It iterates through all the MP3 files in the resources folder and uses the
     * readMetadataForTrack method to extract metadata for each track in a new thread.
     * It utilizes JavaFX's Platform.runLater() to safely make changes to the JavaFX application thread.
     * The method also uses Java's ExecutorService to manage the threads.
     *
     * @return A List<MusicTrack> containing the metadata for all audio tracks in the resources folder.
     * @throws URISyntaxException If there is an issue converting the resource URL to a URI.
     */

    public List<MusicTrack> readMetadataForAllTracks() throws URISyntaxException {
        // Create a list to store the MusicTrack objects
        List<MusicTrack> tracks = Collections.synchronizedList(new ArrayList<>());

        // Get the URL of the resources folder and convert the URL to a file object
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

        System.out.println("Resources folder: " + resourcesFolder.getPath());

        /* This lambda expression takes two arguments (dir, name) and returns a boolean result.
         * It's a compact way of implementing the FilenameFilter interface's accept() method.*/

        // To filter the list of files in the resources folder to only include .mp3 files.
        File[] mp3Files = resourcesFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        // Create an ExecutorService with a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Read metadata for each track and add it to the list
        if (mp3Files != null) {
            List<Future<?>> futures = new ArrayList<>();
            for (File audioFile : mp3Files) {

                /* To create a new thread for each audio file in the folder,
                 * read its metadata, and add it to the list of tracks.
                 * This lambda expression doesn't take any arguments and doesn't return a result.
                 * It's a compact way of implementing the Runnable interface's run() method.*/
                Future<?> future = executor.submit(() -> {
                    System.out.println("Reading file: " + audioFile.getPath());
                    MusicTrack track = readMetadataForTrack(audioFile.getPath());
                    Platform.runLater(() -> {
                        track.createMediaPlayer();
                    });
                    tracks.add(track);
                });

                futures.add(future);  // Add the future to the list
            }

            // Wait for all tasks to complete
            futures.forEach((Future<?> future) -> {
                try {
                    future.get();
                } catch (Exception e) {
                    System.out.println("Error processing file");
                    e.printStackTrace();
                }
            });
        }


        // Shutdown the executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Return the list of MusicTrack objects
        return tracks;
    }
}