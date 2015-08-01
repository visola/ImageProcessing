package com.visola.image.processing;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ImageObject {

  private BufferedImage image = null;
  private Map<Point, Pixel> pixelsByPosition = new ConcurrentHashMap<>();
  private Rectangle size = null;
  private Color averageColor = null;

  public ImageObject(BufferedImage image) {
    this.image = image;
  }

  public void addPixel(Pixel pixel) {
    pixelsByPosition.put(pixel.getPosition(), pixel);
    averageColor = null;
    size = null;
  }

  public double getFilledPercentage() {
    int sizeArea = getSizeArea();
    return ( (double) getPixelCount() ) / ( (double) sizeArea );
  }

  public BufferedImage getImage() {
    return image;
  }

  public Point[] getPositions() {
    Set<Point> pointsSet = pixelsByPosition.keySet(); 
    return pointsSet.toArray(new Point[pointsSet.size()]);
  }

  public int getPixelCount() {
    return pixelsByPosition.size();
  }

  public Rectangle getSize() {
    if (size == null) {
      int minX = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;
      int maxX = 0;
      int maxY = 0;
      for (Point p : pixelsByPosition.keySet()) {
        if (p.x < minX)
          minX = p.x;
        if (p.y < minY)
          minY = p.y;
        if (p.x > maxX)
          maxX = p.x;
        if (p.y > maxY)
          maxY = p.y;
      }

      size = new Rectangle(minX, minY, maxX - minX, maxY - minY);
      if (size.width == 0) size.width = 1;
      if (size.height == 0) size.height = 1;
    }

    return size;
  }

  public Color getAverageColor() {
    if (averageColor == null) {
      int red = 0;
      int green = 0;
      int blue = 0;
      int count = pixelsByPosition.size();

      for (Pixel pixel : pixelsByPosition.values()) {
        red += pixel.getColor().getRed();
        green += pixel.getColor().getGreen();
        blue += pixel.getColor().getBlue();
      }

      red = red/count;
      if (red > 255) red = 255;
      green = green/count;
      if (green > 255) green = 255;
      blue = blue/count;
      if (blue > 255) blue = 255;
      averageColor = new Color(red, green, blue);
    }

    return averageColor;
  }

  public int getSizeArea() {
    Rectangle size = getSize();
    return size.width * size.height;
  }

  public BufferedImage getSubImage() {
    Rectangle r = getSize();
    return getImage().getSubimage(r.x, r.y, r.width, r.height);
  }

  public void removePoint(Point p) {
    pixelsByPosition.remove(p);
    size = null;
    averageColor = null;
  }

  public Iterable<Pixel> getPixels() {
    return pixelsByPosition.values();
  }

}