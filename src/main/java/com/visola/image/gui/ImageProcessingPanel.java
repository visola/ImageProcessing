package com.visola.image.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.visola.image.processing.ImageObject;
import com.visola.image.processing.ImageUtils;
import com.visola.image.processing.Pixel;

public class ImageProcessingPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private final BufferedImage buffer;
  private final BufferedImage image;
  private final Map<Point, ImageObject> objectsByPosition = new ConcurrentHashMap<>();
  private int x, y;

  public ImageProcessingPanel(BufferedImage image) {
    this.image = image;
    System.out.printf("Image size: %d, %d%n", image.getWidth(), image.getHeight());
    this.buffer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

    Graphics2D g2 = buffer.createGraphics();
    g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), ImageProcessingPanel.this);
    g2.dispose();

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        new Thread(new FindObjects()).start();
      }
    });
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = buffer.createGraphics();
    try {
      g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), ImageProcessingPanel.this);
      Set<ImageObject> objects = new HashSet<>(objectsByPosition.values());
      for (ImageObject imageObject : objects) {
        for (Pixel p : imageObject.getPixels()) {
          g2.setColor(imageObject.getAverageColor());
          g2.fillRect(p.getPosition().x, p.getPosition().y, 1, 1);
        }
      }
      g2.setColor(Color.BLUE);
      g2.setStroke(new BasicStroke(5));
      g2.drawLine(0, y, buffer.getWidth(), y);
    } finally {
      g2.dispose();
    }
    g2 = (Graphics2D) g;
    try {
      g2.drawImage(buffer, 0, 0, getWidth(), getHeight(), this);
    } finally {
      g2.dispose();
    }
  }

  private class FindObjects implements Runnable {
    @Override
    public void run() {
      objectsByPosition.clear();
      for (y = 0; y < image.getHeight(); y++) {
        for (x = 0; x < image.getWidth(); x++) {
          Set<Pixel> pixelsBefore = ImageUtils.getPixelsAroundAndBefore(x, y, image);
          Pixel pixel = new Pixel(x, y, image.getRGB(x, y));
          ImageObject imageObject;
          if (pixelsBefore.isEmpty()) {
            imageObject = new ImageObject(image);
            imageObject.addPixel(pixel);
          } else {
            Optional<Pixel> closestColor = ImageUtils.findClosestColor(pixel, pixelsBefore, 5.0D);
            if (closestColor.isPresent()) {
              imageObject = objectsByPosition.get(closestColor.get().getPosition());
              imageObject.addPixel(pixel);
            } else {
              imageObject = new ImageObject(image);
              imageObject.addPixel(pixel);
            }
          }
          objectsByPosition.put(pixel.getPosition(), imageObject);
          SwingUtilities.invokeLater(()-> repaint());
        }
      }
    }
  }

}
