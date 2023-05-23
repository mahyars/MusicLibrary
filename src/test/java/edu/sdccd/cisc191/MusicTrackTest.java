package edu.sdccd.cisc191;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MusicTrackTest {

    private MusicTrack musicTrack;

    @BeforeEach
    void setUp() {
        // You can use a real file path to an mp3 file here for testing purposes.
        musicTrack = new MusicTrack("title", "artist", "album", "genre", "/path/to/track.mp3");
    }

    @Test
    void getTitle() {
        assertEquals("title", musicTrack.getTitle());
    }

    @Test
    void getArtist() {
        assertEquals("artist", musicTrack.getArtist());
    }

    @Test
    void getAlbum() {
        assertEquals("album", musicTrack.getAlbum());
    }

    @Test
    void getGenre() {
        assertEquals("genre", musicTrack.getGenre());
    }

    @Test
    void getPlayer() {
        // We haven't called createMediaPlayer() method, so player should be null
        assertNull(musicTrack.getPlayer());
    }

    @Test
    void getTrackDuration() {
        // We haven't called createMediaPlayer() method, so trackDuration should be 0.0
        assertEquals(0.0, musicTrack.getTrackDuration());
    }
}
