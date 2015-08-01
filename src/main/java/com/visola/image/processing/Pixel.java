package com.visola.image.processing;

import java.awt.Color;
import java.awt.Point;

public class Pixel {

  private Point position;
  private Color color;

  public Pixel(Point position, Color color) {
    this.position = position;
    this.color = color;
  }

  public Pixel(int x, int y, int rgb) {
    this.position = new Point(x, y);
    this.color = new Color(rgb);
  }

  public Point getPosition() {
    return position;
  }

  public void setPosition(Point position) {
    this.position = position;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

}
