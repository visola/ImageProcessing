package com.visola.image.processing;

import java.awt.Color;

public class ColorUtils {

  public static Color hypot(Color p1, Color p2) {
    int hypoB = (int) Math.round(Math.hypot(p1.getBlue(), p2.getBlue()));
    int hypoG = (int) Math.round(Math.hypot(p1.getGreen(), p2.getGreen()));
    int hypoR = (int) Math.round(Math.hypot(p1.getRed(), p2.getRed()));

    if (hypoB > 255) hypoB = 255;
    if (hypoG > 255) hypoG = 255;
    if (hypoR > 255) hypoR = 255;

    return new Color(hypoR, hypoG, hypoB);
  }

  public static Color toGreyScale(Color in) {
    int r = in.getRed();
    int g = in.getGreen();
    int b = in.getBlue();

    int max = Math.max(Math.max(r, g), b);
    return new Color(max, max, max);
  }

}
