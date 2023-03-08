package edu.sdccd.cisc191;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MusicDisplayUI extends VBox {
    private Label titleLabel;
    private HBox buttonBox;
    private HBox timeStamp;

    public MusicDisplayUI() {
        // Create UI components
        titleLabel = new Label("Title:");
        buttonBox = new HBox();
        timeStamp = new HBox();

        // Set up the time stamp label
        Label timeLabel = new Label("0:00");
        timeStamp.setAlignment(Pos.CENTER);
        timeStamp.getChildren().addAll(timeLabel);

        // Set up the layout for the button box
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);

        // Create a VBox container to hold the title, button box, and time stamp
        VBox contentBox = new VBox();
        contentBox.setSpacing(10);
        contentBox.getChildren().addAll(titleLabel, buttonBox, timeStamp);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Center the VBox container
        setAlignment(Pos.CENTER);

        // Add UI components to the VBox
        getChildren().addAll(contentBox);

        // Set the Hgrow and Vgrow constraints for the UI elements
        HBox.setHgrow(buttonBox, Priority.ALWAYS);
        VBox.setVgrow(buttonBox, Priority.ALWAYS);
    }

    // Set the title label text
    public void setTitleLabelText(String text) {
        titleLabel.setText(text);
    }

    // Add a button to the button box
    public void addButtonToButtonBox(Button button) {
        buttonBox.getChildren().add(button);
    }
}
