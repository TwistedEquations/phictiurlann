package com.twistedequations.phictiurlann.butteraugli

import com.twistedequations.phictiurlann.PhictiurlannExtenstion
import com.twistedequations.phictiurlann.binaries.getBinraryFile
import com.twistedequations.phictiurlann.image.isPng
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Created by patrickd on 13/07/2016.
 */
class Butteraugli(private val project: Project,
                  private val extenstion: PhictiurlannExtenstion) {

  fun compare(image1: File, image2: File): Double {

    if (! isPng(image1) || ! isPng(image1)) {
      throw IllegalArgumentException("Both images must be pngs")
    }

    val file = getBinraryFile("butteraugli", "compare_pngs", project)
    file.setExecutable(true)

    //When
    val pb = ProcessBuilder(file.absolutePath, image1.absolutePath, image2.absolutePath);
    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    val reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    return lines[0].toDouble()
  }

  fun isAcceptable(value: Double): Boolean {
    return value <= extenstion.butteraugliThreshold;
  }
}
