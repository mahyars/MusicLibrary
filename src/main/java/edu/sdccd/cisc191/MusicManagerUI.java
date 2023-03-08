package edu.sdccd.cisc191;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MusicLibraryManagerUI extends VBox {
    private MusicLibrary library;
    private ListView<MusicTrack> trackListView;
    private Button addTrackButton;
    private Button removeTrackButton;
    private TextField titleField;

    public MusicLibraryManagerUI(MusicLibrary library) {
        // Add reference to MusicLibrary instance
        this.library = library;

        // Create UI components
        trackListView = new ListView<MusicTrack>();
        addTrackButton = new Button("Add Track");
        removeTrackButton = new Button("Remove Track");
        titleField = new TextField("test");

        // Create an ObservableList from the ArrayList returned by library.getTracks()
        ObservableList<MusicTrack> observableTracks = FXCollections.observableArrayList(library.getTracks());

        // Set the ListView items to the new ObservableList
        trackListView.setItems(observableTracks);

        // Set the style of the buttons using the Styles class
        Styles.setButtonStyle(addTrackButton);
        Styles.setButtonStyle(removeTrackButton);

        // Set event handler for the add track button
        addTrackButton.setOnAction(event -> {
            // Create a new MusicTrack object from the text fields and add it to the library
            MusicTrack newTrack = new MusicTrack(titleField.getText(), "Unknown Artist", "Unknown Album",
                    "Unknown Genre", "/path/to/file.mp3");
            library.addTrack(newTrack);
            observableTracks.add(newTrack);
        });

        // Set event handler for the remove track button
        removeTrackButton.setOnAction(event -> {
            // Get the selected item from the list view and remove it from the library
            MusicTrack selectedTrack = trackListView.getSelectionModel().getSelectedItem();
            if (selectedTrack != null) {
                library.removeTrack(selectedTrack);
                observableTracks.remove(selectedTrack);
            }
        });

        // Create a VBox container to hold the title, text field, and button box
        setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(titleField, addTrackButton, removeTrackButton, trackListView);

        // Set the Hgrow and Vgrow constraints for the text field and buttons
        VBox.setVgrow(titleField, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(addTrackButton, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(removeTrackButton, javafx.scene.layout.Priority.ALWAYS);
    }
}
