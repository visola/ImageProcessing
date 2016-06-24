package com.visola.image.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.visola.image.processing.ColorUtils;
import com.visola.image.processing.ImageUtils;

public class ImageProcessingPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private final BufferedImage buffer;
  private final BufferedImage sobel;
  private final BufferedImage image;
  private int x, y;

  public ImageProcessingPanel(BufferedImage image) {
    this.image = image;
    System.out.printf("Image size: %d, %d%n", image.getWidth(), image.getHeight());
    this.buffer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    this.sobel = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

    Graphics2D g2 = buffer.createGraphics();
    g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), ImageProcessingPanel.this);
    g2.dispose();

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        new Thread(new SobelOperation()).start();
      }
    });
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = buffer.createGraphics();
    try {
      g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), ImageProcessingPanel.this);
      if (y > 0) {
    	  g2.drawImage(sobel.getSubimage(0, 0, sobel.getWidth(), y), 0, 0, image.getWidth(), y, ImageProcessingPanel.this);
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

  private class SobelOperation implements Runnable {
    @Override
    public void run() {
      BufferedImage processedImageX = ImageUtils.SOBEL_CONVOL_X.filter(image, null);
      BufferedImage processedImageY = ImageUtils.SOBEL_CONVOL_Y.filter(image, null);
      for (y = 0; y < image.getHeight(); y++) {
        for (x = 0; x < image.getWidth(); x++) {
          Color cX = new Color(processedImageX.getRGB(x, y));
          Color cY = new Color(processedImageY.getRGB(x, y));

          sobel.setRGB(x, y, ColorUtils.toGreyScale(ColorUtils.hypot(cX, cY)).getRGB());
          SwingUtilities.invokeLater(()-> repaint());
        }
      }
    }
  }

}
