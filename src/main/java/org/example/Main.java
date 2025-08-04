package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Methods.UserInterface;

import java.io.File;
import java.io.IOException;

/**
 * Run the project through the maven tool window
 */
public class Main extends Application {

    private UserInterface controller;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainPage.fxml"));
        loader.setController(new UserInterface());
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}

