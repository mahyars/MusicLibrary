package edu.sdccd.cisc191;

import javafx.collections.ListChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MusicLibraryTest {
    private MusicLibrary musicLibrary;
    private MusicTrack testTrack1;
    private MusicTrack testTrack2;

    @BeforeEach
    public void setUp() {
        musicLibrary = new MusicLibrary();
        testTrack1 = new MusicTrack("Test Title 1", "Test Artist 1", "Test Album 1", "Test Genre 1", "test1.mp3");
        testTrack2 = new MusicTrack("Test Title 2", "Test Artist 2", "Test Album 2", "Test Genre 1", "test2.mp3");
    }

    @Test
    public void testAddTrack() {
        musicLibrary.addTrack(testTrack1);
        assertEquals(1, musicLibrary.getTrackCount());
        assertEquals(testTrack1, musicLibrary.getTrack(0));
    }

    @Test
    public void testRemoveTrack() {
        musicLibrary.addTrack(testTrack1);
        musicLibrary.addTrack(testTrack2);
        musicLibrary.removeTrack(testTrack1);
        assertEquals(1, musicLibrary.getTrackCount());
        assertEquals(testTrack2, musicLibrary.getTrack(0));
    }

    @Test
    public void testGetTracksByGenre() {
        musicLibrary.addTrack(testTrack1);
        musicLibrary.addTrack(testTrack2);
        List<MusicTrack> tracks = musicLibrary.getTracksByGenre("Test Genre 1");
        assertEquals(2, tracks.size());
        assertTrue(tracks.contains(testTrack1));
        assertTrue(tracks.contains(testTrack2));
    }

    @Test
    public void testSortTracksByAttribute() {
        musicLibrary.addTrack(testTrack1);
        musicLibrary.addTrack(testTrack2);
        List<MusicTrack> sortedTracks = MusicLibrary.sortTracksByAttribute(MusicLibrary.TrackAttribute.TITLE);
        assertEquals(2, sortedTracks.size());
        assertEquals(testTrack1, sortedTracks.get(0));
        assertEquals(testTrack2, sortedTracks.get(1));
    }

    @Test
    public void testAddListener() {
        ListChangeListener<MusicTrack> listener = change -> {
            assertTrue(change.next());
            assertEquals(1, change.getAddedSize());
            assertEquals(testTrack1, change.getAddedSubList().get(0));
        };
        musicLibrary.addListener(listener);
        musicLibrary.addTrack(testTrack1);
    }
}