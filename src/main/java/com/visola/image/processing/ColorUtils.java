package com.visola.image.processing;

import java.awt.Color;

public class ColorUtils {

  public static double distance(Color p1, Color p2) {
    int diffB = p1.getBlue() - p2.getBlue();
    int diffG = p1.getGreen() - p2.getGreen();
    int diffR = p1.getRed() - p2.getRed();

    return Math.sqrt(Math.pow(diffB, 2) + Math.pow(diffG, 2) + Math.pow(diffR, 2));
  }

}
