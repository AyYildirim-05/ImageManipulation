package org.example.Methods;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindow {
    /**
     * Displays a modal alert box with the given title and message
     *
     * @param title   The title of the alert box
     * @param message The message to be displayed in the alert box
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        // Set the modality of the window to prevent interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        // Create a label and button for the alert box
        Label label = new Label();
        label.setId("alertLabel");
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setId("alertButton");
        closeButton.setOnAction(event -> window.close());

        // Create a layout to arrange the label and button
        VBox layout = new VBox(10);
        layout.setId("alertLayout");
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        // Set the scene with the layout
        Scene scene = new Scene(layout);

        // Set the scene and show the window
        window.setScene(scene);
        window.showAndWait();
    }

}
