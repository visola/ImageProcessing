package com.visola.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

import com.visola.image.gui.MainFrame;

public class Main {

  public static void main(String[] args) throws Exception {
    byte [] imageBytes = FileUtils.readFileToByteArray(new File(args[0]));
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

    MainFrame frame = new MainFrame(image);
    frame.setSize(600, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
