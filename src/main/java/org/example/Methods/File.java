package org.example.Methods;

import org.example.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class File {
    /**
     * Method that gets a file
     *
     * @param path path of the image
     * @return the buffered image
     */
    public BufferedImage loadImage(String path) {
        try {
            URL resource = Main.class.getResource(path);
            if (resource == null) {
                System.out.println("Image not found at: " + path);
                return null;
            }
            BufferedImage img = ImageIO.read(resource);
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
     * Method that displays image produced'
     *
     * @param img the image data entered
     */
    public void display(BufferedImage img) {
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
