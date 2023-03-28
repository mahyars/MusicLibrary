package edu.sdccd.cisc191;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class Styles {
    public static void setButtonStyle(Button button) {
        button.setFont(Font.font("Verdana", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #0072C6;");

        button.setOnMousePressed(event -> {
            button.setStyle("-fx-background-color: #00c6b3;");
        });

        button.setOnMouseReleased(event -> {
            button.setStyle("-fx-background-color: #0072C6;");
        });

        String symbol;
        switch (button.getText()) {
            case "Play/Pause":
                symbol = "\u25B6"; // Unicode character for "play" symbol
                break;
            case "Stop":
                symbol = "\u25A0"; // Unicode character for "stop" symbol
                break;
            case "Next":
                symbol = "\u23E9"; // Unicode character for "next" symbol
                break;
            case "Rewind":
                symbol = "\u23EA"; // Unicode character for "rewind" symbol
                break;
            default:
                symbol = "";
                break;
        }
        button.setText(symbol);

    }
}