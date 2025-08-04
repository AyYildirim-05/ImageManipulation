package org.example.Methods;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

public class UserInterface {

    @FXML private Button importButton, exportButton, applyButton,saveButton;
    @FXML private Tab singularTab, multipleTab;
    @FXML private ImageView imageView;

    private String fileName;
    private Stage stage;
    private BufferedImage image;
    private FileMethods fm;
    @FXML
    private void initialize() {
        if (fm == null) {
            fm = new FileMethods();
        }

        importButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import Image File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());

            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                System.out.println("Selected file path: " + filePath);
                image = fm.loadImage(filePath);

                if (image != null) {
                    imageView.setImage(fm.toImage(image));
                } else {
                    System.out.println("⚠️ Failed to load image.");
                }

            } else {
                System.out.println("File selection cancelled.");
            }

        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

