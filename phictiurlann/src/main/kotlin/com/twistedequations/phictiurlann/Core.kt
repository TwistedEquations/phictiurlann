package com.twistedequations.phictiurlann

import com.twistedequations.phictiurlann.image.isSupportedImate
import com.twistedequations.phictiurlann.image.isWebp
import java.io.File

fun optimiseImages(dir: File, extenstion: PhictiurlannExtenstion) {
  if (! dir.exists()) {
    throw IllegalArgumentException("Image dir at ${dir.absolutePath} does not exist!!");
  }

  dir.listFiles()
      .filter { isSupportedImate(it) }
      .forEach { processImage(it, extenstion) }
}

private fun processImage(file: File, extenstion: PhictiurlannExtenstion) {
  if(extenstion.useWebp) {
    //Convert everything to webp if allowed
    if(isWebp(file)) {

    }

    return
  }
}
