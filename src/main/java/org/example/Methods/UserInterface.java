package org.example.Methods;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

public class UserInterface {

    @FXML
    private RadioButton toGrayScale1, toGrayScale2, blurGrayScale, blurColor, heavyBlurGrayScale, heavyBlurColor, edgeDetection;
    @FXML
    private RadioButton pixelateGrayScale, pixelateColor, resize, brighten;

    @FXML private Button importButton, exportButton, applyButton;
    @FXML private Tab singularTab, multipleTab;
    @FXML
    private TabPane tabPane;
    @FXML private ImageView imageView;
    @FXML
    private TextField numberField;

    private String fileName;
    private Stage stage;
    private BufferedImage image;
    private BufferedImage applied;
    private FileMethods fm;
    private Plugins plugin;

    private ToggleGroup group1, group2;
    private Image display;

    @FXML
    private void initialize() {
        if (fm == null && plugin == null) {
            fm = new FileMethods();
            plugin = new Plugins();
        }
        if (group1 == null && group2 == null) {
            group1 = new ToggleGroup();
            group2 = new ToggleGroup();

            toGrayScale1.setToggleGroup(group1);
            toGrayScale2.setToggleGroup(group1);
            blurGrayScale.setToggleGroup(group1);
            blurColor.setToggleGroup(group1);
            heavyBlurGrayScale.setToggleGroup(group1);
            heavyBlurColor.setToggleGroup(group1);
            edgeDetection.setToggleGroup(group1);

            pixelateGrayScale.setToggleGroup(group2);
            pixelateColor.setToggleGroup(group2);
            resize.setToggleGroup(group2);
            brighten.setToggleGroup(group2);
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
                    display = fm.toImage(image);
                    imageView.setFitWidth(display.getWidth());
                    imageView.setFitHeight(display.getHeight());
                    imageView.setImage(display);


                } else {
                    System.out.println("⚠️ Failed to load image.");
                }

            } else {
                System.out.println("File selection cancelled.");
            }
        });

        applyButton.setOnAction(event -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

            switch (selectedTab.getId()) {
                case "singularTab":
                    RadioButton selectedRadioButton1 = (RadioButton) group1.getSelectedToggle();

                    switch (selectedRadioButton1.getId()) {
                        case "toGrayScale1":
                            applied = plugin.toGrayScale(image);
                            break;
                        case "toGrayScale2":
                            applied = plugin.toGrayScale2(image);
                            break;
                        case "blurGrayScale":
                            applied = plugin.blur(image);
                            break;
                        case "blurColor":
                            applied = plugin.blurColor(image);
                            break;
                        case "heavyBlurGrayScale":
                            applied = plugin.heavyBlur(image);
                            break;
                        case "heavyBlurColor":
                            applied = plugin.heavyBlurColor(image);
                            break;
                        case "edgeDetection":
                            applied = plugin.detectEdges(image);
                            break;
                        default:
                            System.out.println("error");
                            return;
                    }

                    display = fm.toImage(applied);
                    imageView.setFitWidth(display.getWidth());
                    imageView.setFitHeight(display.getHeight());
                    imageView.setImage(display);

//                    image = applied;

                    break;
                case "multipleTab":
                    getText();
                    RadioButton selectedRadioButton2 = (RadioButton) group2.getSelectedToggle();
                    switch (selectedRadioButton2.getId()) {
                        case "pixelateGrayScale":
                            applied = plugin.pixelateGreyScale(image, Integer.parseInt(numberField.getText()));
                            break;
                        case "pixelateColor":
                            applied = plugin.pixelateColor(image, Integer.parseInt(numberField.getText()));
                            break;
                        case "resize":
                            applied = plugin.resize(image, Integer.parseInt(numberField.getText()));
                            break;
                        case "brighten":
                            applied = plugin.brighten(image, Integer.parseInt(numberField.getText()));
                            break;
                        default:
                            System.out.println("error");
                            return;
                    }

                    display = fm.toImage(applied);
                    imageView.setFitWidth(display.getWidth());
                    imageView.setFitHeight(display.getHeight());
                    imageView.setImage(display);

//                    image = applied;

                    break;
                default:
                    System.out.println("Unknown tab.");
                    break;
            }
        });

        exportButton.setOnAction(event -> {

        });

    }


    private void getText() {
        numberField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                numberField.setText(oldVal);
            }
        });
    }

}
