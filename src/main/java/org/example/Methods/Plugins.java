package org.example.Methods;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Plugins {

    /**
     * Manual conversion to grayscale of an image
     *
     * @param img the image data entered
     * @return the greyscale image
     */
    public BufferedImage toGrayScale(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                int newRGB = (gray << 16) | (gray << 8) | gray;

                grayImage.setRGB(x, y, newRGB);
            }
        }
        return grayImage;
    }

    /**
     * Java automatically converts it to grayscale because grayImage was created with TYPE_BYTE_GRAY.
     *
     * @param img the image data entered
     * @return the greyscale image
     */
    public BufferedImage toGrayScale2(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = grayImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return grayImage;
    }

    /**
     * Method that applies a n*n pixelation to a grayscale image
     *
     * @param img the image data given to the method
     * @param n the pixel number of the selected square
     * @return the image with applied effect
     */
    public BufferedImage pixelateGreyScale(BufferedImage img, int n) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage pixImg = new BufferedImage(
                width, height, BufferedImage.TYPE_BYTE_GRAY);
        int pix = 0; // total grayscale value
        int p = 0; // final pixel RGB
        for (int y = 0; y < height - n; y += n) {
            for (int x = 0; x < width - n; x += n) { // these two select a box

                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) { // calculate the average value in each box
                        pix += (img.getRGB(x + a, y + b) & 0xFF);
                    }
                }
                pix = (int) (pix / n / n); //  Average the Block’s Grayscale Value
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) { // painting the chosen block to the same color
                        p = (255 << 24) | (pix << 16) | (pix << 8) | pix;
                        pixImg.setRGB(x + a, y + b, p);
                    }
                }
                pix = 0;
            }
        }
        return pixImg;
    }

    /**
     * Method that applies a n*n pixelation to a color image
     *
     * @param img the image data given to the method
     * @param n the pixel number of the selected square
     * @return the image with applied effect
     */
    public BufferedImage pixelateColor(BufferedImage img, int n) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage pixImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height - n; y += n) {
            for (int x = 0; x < width - n; x += n) {
                int rSum = 0, gSum = 0, bSum = 0, aSum = 0;

                // Sum up R, G, B, A values in the block
                for (int dy = 0; dy < n; dy++) {
                    for (int dx = 0; dx < n; dx++) {
                        int rgb = img.getRGB(x + dx, y + dy);
                        aSum += (rgb >> 24) & 0xFF; // extract alpha
                        rSum += (rgb >> 16) & 0xFF; // extract red
                        gSum += (rgb >> 8) & 0xFF; // extract green
                        bSum += rgb & 0xFF; // extract blue
                    }
                }

                int numPixels = n * n;
                int aAvg = aSum / numPixels; // average of alpha
                int rAvg = rSum / numPixels; // average of red
                int gAvg = gSum / numPixels; // average of green
                int bAvg = bSum / numPixels; // average of blue

                // Rebuild ARGB pixel
                int avgColor = (aAvg << 24) | (rAvg << 16) | (gAvg << 8) | bAvg;

                // Paint the block with the average color
                for (int dy = 0; dy < n; dy++) {
                    for (int dx = 0; dx < n; dx++) {
                        pixImg.setRGB(x + dx, y + dy, avgColor);
                    }
                }
            }
        }

        return pixImg;
    }

    /**
     * Method that scales a greyscale image
     *
     * @param img the image data given to the method
     * @param newHeight the pixel height of the given image
     * @return the image with applied effect
     */
    public BufferedImage resize(BufferedImage img, int newHeight) {
        double scaleFactor = (double) newHeight / img.getHeight();
        int newWidth = (int) (scaleFactor * img.getWidth());

        BufferedImage scaledImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        AffineTransform at = new AffineTransform();
        at.scale(scaleFactor, scaleFactor);

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(img, scaledImg);
    }

    /**
     * Method that applies 3x3 Gaussian blur to a grayscale image
     *
     * @param img the image data given to the method
     * @return the image with applied effect
     */
    public BufferedImage blur(BufferedImage img) {
        BufferedImage blurImg = new BufferedImage(
                img.getWidth() - 2, img.getHeight() - 2, BufferedImage.TYPE_BYTE_GRAY);
        int pix = 0;
        for (int y = 0; y < blurImg.getHeight(); y++) {
            for (int x = 0; x < blurImg.getWidth(); x++) {
                pix = (int) (4 * (img.getRGB(x + 1, y + 1) & 0xFF)
                        + 2 * (img.getRGB(x + 1, y) & 0xFF)
                        + 2 * (img.getRGB(x + 1, y + 2) & 0xFF)
                        + 2 * (img.getRGB(x, y + 1) & 0xFF)
                        + 2 * (img.getRGB(x + 2, y + 1) & 0xFF)
                        + (img.getRGB(x, y) & 0xFF)
                        + (img.getRGB(x, y + 2) & 0xFF)
                        + (img.getRGB(x + 2, y) & 0xFF)
                        + (img.getRGB(x + 2, y + 2) & 0xFF)) / 16;
                int p = (255 << 24) | (pix << 16) | (pix << 8) | pix;
                blurImg.setRGB(x, y, p);
            }
        }
        return blurImg;
    }

    /**
     * Method that applies 3x3 Gaussian blur to a colored image
     *
     * @param img the image data given to the method
     * @return the image with applied effect
     */
    public BufferedImage blurColor(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Shrink output size because of 3x3 kernel border
        BufferedImage blurImg = new BufferedImage(width - 2, height - 2, BufferedImage.TYPE_INT_ARGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int r = 0, g = 0, b = 0, a = 0;

                // Weighted blur kernel on each channel
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = img.getRGB(x + kx, y + ky);
                        int weight;

                        // Define weights
                        if (kx == 0 && ky == 0) weight = 4;
                        else if (kx == 0 || ky == 0) weight = 2;
                        else weight = 1;

                        a += ((pixel >> 24) & 0xFF) * weight;
                        r += ((pixel >> 16) & 0xFF) * weight;
                        g += ((pixel >> 8) & 0xFF) * weight;
                        b += (pixel & 0xFF) * weight;
                    }
                }

                // Normalize (sum of weights = 16)
                a /= 16;
                r /= 16;
                g /= 16;
                b /= 16;

                int blurred = (a << 24) | (r << 16) | (g << 8) | b;
                blurImg.setRGB(x - 1, y - 1, blurred); // offset by 1 since we started at x=1
            }
        }

        return blurImg;
    }


    /**
     * Method that applies a 5x5 Gaussian blur to a grayscale image
     *
     * @param img the image data given to the method
     * @return the image with applied effect
     */
    public BufferedImage heavyBlur(BufferedImage img) {
        BufferedImage blurImg = new BufferedImage(
                img.getWidth() - 4, img.getHeight() - 4, BufferedImage.TYPE_BYTE_GRAY);
        int pix = 0;
        for (int y = 0; y < blurImg.getHeight(); y++) {
            for (int x = 0; x < blurImg.getWidth(); x++) {
                pix = (int) (
                        10 * (img.getRGB(x + 3, y + 3) & 0xFF)
                                + 6 * (img.getRGB(x + 2, y + 1) & 0xFF)
                                + 6 * (img.getRGB(x + 1, y + 2) & 0xFF)
                                + 6 * (img.getRGB(x + 2, y + 3) & 0xFF)
                                + 6 * (img.getRGB(x + 3, y + 2) & 0xFF)
                                + 4 * (img.getRGB(x + 1, y + 1) & 0xFF)
                                + 4 * (img.getRGB(x + 1, y + 3) & 0xFF)
                                + 4 * (img.getRGB(x + 3, y + 1) & 0xFF)
                                + 4 * (img.getRGB(x + 3, y + 3) & 0xFF)
                                + 2 * (img.getRGB(x, y + 1) & 0xFF)
                                + 2 * (img.getRGB(x, y + 2) & 0xFF)
                                + 2 * (img.getRGB(x, y + 3) & 0xFF)
                                + 2 * (img.getRGB(x + 4, y + 1) & 0xFF)
                                + 2 * (img.getRGB(x + 4, y + 2) & 0xFF)
                                + 2 * (img.getRGB(x + 4, y + 3) & 0xFF)
                                + 2 * (img.getRGB(x + 1, y) & 0xFF)
                                + 2 * (img.getRGB(x + 2, y) & 0xFF)
                                + 2 * (img.getRGB(x + 3, y) & 0xFF)
                                + 2 * (img.getRGB(x + 1, y + 4) & 0xFF)
                                + 2 * (img.getRGB(x + 2, y + 4) & 0xFF)
                                + 2 * (img.getRGB(x + 3, y + 4) & 0xFF)
                                + (img.getRGB(x, y) & 0xFF)
                                + (img.getRGB(x, y + 2) & 0xFF)
                                + (img.getRGB(x + 2, y) & 0xFF)
                                + (img.getRGB(x + 2, y + 2) & 0xFF)) / 74;
                int p = (255 << 24) | (pix << 16) | (pix << 8) | pix;
                blurImg.setRGB(x, y, p);
            }
        }
        return blurImg;
    }

    /**
     * Method that applies a 5x5 Gaussian blur to a colored image
     *
     * @param img the image data given to the method
     * @return the image with applied effect
     */
    public BufferedImage heavyBlurColor(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage blurImg = new BufferedImage(width - 4, height - 4, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height - 4; y++) {
            for (int x = 0; x < width - 4; x++) {
                int a = 0, r = 0, g = 0, b = 0;

                // List of [dx, dy, weight] from your formula
                int[][] weights = {
                        {3, 3, 10}, {2, 1, 6}, {1, 2, 6}, {2, 3, 6}, {3, 2, 6},
                        {1, 1, 4}, {1, 3, 4}, {3, 1, 4}, {3, 3, 4},
                        {0, 1, 2}, {0, 2, 2}, {0, 3, 2},
                        {4, 1, 2}, {4, 2, 2}, {4, 3, 2},
                        {1, 0, 2}, {2, 0, 2}, {3, 0, 2},
                        {1, 4, 2}, {2, 4, 2}, {3, 4, 2},
                        {0, 0, 1}, {0, 2, 1}, {2, 0, 1}, {2, 2, 1}
                };

                for (int[] w : weights) {
                    int dx = w[0], dy = w[1], wt = w[2];
                    int rgb = img.getRGB(x + dx, y + dy);
                    a += ((rgb >> 24) & 0xFF) * wt;
                    r += ((rgb >> 16) & 0xFF) * wt;
                    g += ((rgb >> 8) & 0xFF) * wt;
                    b += (rgb & 0xFF) * wt;
                }

                int divisor = 74;
                a /= divisor;
                r /= divisor;
                g /= divisor;
                b /= divisor;

                int finalPixel = (a << 24) | (r << 16) | (g << 8) | b;
                blurImg.setRGB(x, y, finalPixel);
            }
        }

        return blurImg;
    }

    /**
     * Sobel Edge detection algorithm implemented manually for a grayscale image
     *
     * @param img the image data given to the method
     * @return the image containing all the lines in the image
     */
    public BufferedImage detectEdges(BufferedImage img) {
        img = blur(img);
        int h = img.getHeight(), w = img.getWidth(), threshold = 30, p = 0;
        BufferedImage edgeImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        int[][] vert = new int[w][h];
        int[][] horiz = new int[w][h];
        int[][] edgeWeight = new int[w][h];
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                vert[x][y] = (int) (img.getRGB(x + 1, y - 1) & 0xFF + 2 * (img.getRGB(x + 1, y) & 0xFF) + img.getRGB(x + 1, y + 1) & 0xFF
                        - img.getRGB(x - 1, y - 1) & 0xFF - 2 * (img.getRGB(x - 1, y) & 0xFF) - img.getRGB(x - 1, y + 1) & 0xFF);
                horiz[x][y] = (int) (img.getRGB(x - 1, y + 1) & 0xFF + 2 * (img.getRGB(x, y + 1) & 0xFF) + img.getRGB(x + 1, y + 1) & 0xFF
                        - img.getRGB(x - 1, y - 1) & 0xFF - 2 * (img.getRGB(x, y - 1) & 0xFF) - img.getRGB(x + 1, y - 1) & 0xFF);
                edgeWeight[x][y] = (int) (Math.sqrt(vert[x][y] * vert[x][y] + horiz[x][y] * horiz[x][y]));
                if (edgeWeight[x][y] > threshold)
                    p = (255 << 24) | (255 << 16) | (255 << 8) | 255;
                else
                    p = (255 << 24) | (0 << 16) | (0 << 8) | 0;
                edgeImg.setRGB(x, y, p);
            }
        }
        return edgeImg;
    }

    /**
     * Method that brightens the color of an image by a percentage
     *
     * @param img the image data given to the method
     * @param percentage the percentage of the increased brightness
     * @return the image with applied effect
     */
    public BufferedImage brighten(BufferedImage img, int percentage) {
        int r = 0, g = 0, b = 0, rgb = 0, p = 0;
        int amount = (int) (percentage * 255 / 100); // rgb scale is 0-255, so 255 is 100%
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < img.getHeight(); y += 1) {
            for (int x = 0; x < img.getWidth(); x += 1) {
                rgb = img.getRGB(x, y);
                r = ((rgb >> 16) & 0xFF) + amount;
                g = ((rgb >> 8) & 0xFF) + amount;
                b = (rgb & 0xFF) + amount;
                if (r > 255) r = 255;
                if (g > 255) g = 255;
                if (b > 255) b = 255;
                p = (255 << 24) | (r << 16) | (g << 8) | b;
                newImage.setRGB(x, y, p);
            }
        }
        return newImage;
    }

}
