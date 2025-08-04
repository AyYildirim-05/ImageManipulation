package org.example.Methods;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.example.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.embed.swing.SwingFXUtils;


public class FileMethods {
    /**
     * Method that gets a file
     *
     * @param path path of the image
     * @return the buffered image
     */
    public BufferedImage loadImage(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("Image not found at: " + path);
                return null;
            }
            BufferedImage img = ImageIO.read(file);
            if (img == null) {
                System.out.println("⚠️ Could not decode image.");
            }
            return img;
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }


    /**
     * Converts a BufferedImage to a JavaFX Image.
     *
     * @param bufferedImage the BufferedImage to convert
     * @return the converted JavaFX Image
     */
    public Image toImage(BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Method that displays image produced
     *
     * @param img the image data entered
     */
    public void display(BufferedImage img) { //todo should be replaced
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }





}
