package org.example;

import org.example.Methods.File;
import org.example.Methods.Plugins;
import java.awt.image.BufferedImage;

/**
 * Future tasks:
 *
 * 1. Make so that the images do not have to be grey scale
 * 2. Make an application so that we can publish it
 */

public class Main {
    public static BufferedImage img = null;

    public static void main(String[] args) {
        Plugins plugins = new Plugins();
        File file = new File();

        img = file.loadImage("/paris.png");

        if (img != null) {
            file.display(img);
            img = plugins.heavyBlur(img);
            file.display(img);
        }
    }

}
