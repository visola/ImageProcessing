package com.visola.image.processing;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageUtils {

  private static float [] SOBEL_KERNEL_X_VALUES = { -2, 0, 2, -3, 0, 3, -2, 0, 2 };
  private static Kernel SOBEL_KERNEL_X = new Kernel(3, 3, SOBEL_KERNEL_X_VALUES);
  public static ConvolveOp SOBEL_CONVOL_X = new ConvolveOp(SOBEL_KERNEL_X, ConvolveOp.EDGE_NO_OP, null);

  private static float [] SOBEL_KERNEL_Y_VALUES = { -1, -2, -1, 0, 0, 0, 1, 2, 1 };
  private static Kernel SOBEL_KERNEL_Y = new Kernel(3, 3, SOBEL_KERNEL_Y_VALUES);
  public static ConvolveOp SOBEL_CONVOL_Y = new ConvolveOp(SOBEL_KERNEL_Y, ConvolveOp.EDGE_NO_OP, null);

}
