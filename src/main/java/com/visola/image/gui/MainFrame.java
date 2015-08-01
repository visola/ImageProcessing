package com.visola.image.gui;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  public MainFrame(BufferedImage image) {
    super("Image Processing");
    add(new ImageProcessingPanel(image));
    pack();
  }

}
