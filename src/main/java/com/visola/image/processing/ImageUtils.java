package com.visola.image.processing;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ImageUtils {

  public static Set<Pixel> getPixelsAround(int x, int y, BufferedImage image) {
    return getPixelsAround(x, y, image, p -> false);
  }

  public static Set<Pixel> getPixelsAround(int x, int y, BufferedImage image, Function<Point, Boolean> shouldStop) {
    Set<Pixel> pixels = new HashSet<>();
    LOOPS: for (int h = y - 1; h <= y + 1; h++) {
      for (int w = x - 1; w <= x + 1; w++) {
        if (shouldStop.apply(new Point(w, h))) {
          break LOOPS;
        }

        if (w == x && h == y) continue; // Don't count the point

        // Avoid points outside the image
        if (h < 0 || h >= image.getHeight()) continue;
        if (w < 0 || w >= image.getWidth()) continue;

        pixels.add(new Pixel(new Point(w, h), new Color(image.getRGB(w, h))));
      }
    }
    return pixels;
  }

  public static Set<Pixel> getPixelsAroundAndBefore(int x, int y, BufferedImage image) {
    return getPixelsAround(x, y, image, p -> p.x > x && p.y == y);
  }

  public static Optional<Pixel> findClosestColor(Pixel pixel, Set<Pixel> pixels, double threashold) {
    double distance = Double.MAX_VALUE;
    Pixel closest = null;
    for (Pixel p : pixels) {
      double newDistance = ColorUtils.distance(pixel.getColor(), p.getColor());
      if (distance > newDistance) {
        closest = p;
        distance = newDistance;
      }
    }
    if (distance <= threashold) {
      return Optional.of(closest);
    } else {
      return Optional.empty();
    }
  }

}
