package com.twistedequations.phictiurlann.image

import java.awt.image.BufferedImage
import java.util.HashSet

fun BufferedImage.isGrayscale() : Boolean {
  //check number of channels
  val channels = raster.numDataElements;
  if(channels == 1) {
    return true;
  }

  val w = width
  val h = height

  var r = 0
  var g = 0
  var b = 0

  for (y in 0 .. h - 1) {
    for (x in 0 .. w - 1) {
      val rgb = getRGB(x, y)

      r = (0x00ff0000 and rgb) shr 16
      g = (0x0000ff00 and rgb) shr 8
      b = (0x000000ff and rgb)

      if (r != g || g != b || b != r) {
        return false
      }
    }
  }
  return true
}

private val maxColors = 256

fun BufferedImage.hasMoreThan256Colors() : Boolean {
  val colors = HashSet<Int>()

  val w = width
  val h = height

  for (y in 0 .. h-1) {
    for (x in 0 .. w-1) {
      val color = getRGB(x, y);
      colors.add(color);

      if(colors.size > maxColors) {
        return true
      }
    }
  }
  return false

}