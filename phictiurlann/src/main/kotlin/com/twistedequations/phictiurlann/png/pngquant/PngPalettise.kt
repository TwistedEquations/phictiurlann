package com.twistedequations.phictiurlann.png.pngquant

import com.twistedequations.phictiurlann.binaries.getBinraryFile
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.HashSet
import javax.imageio.ImageIO

class PngPalettise(private val project: Project) {

  private val binaryName = "pngquant"
  private val maxColors = 256

  private fun loadBinary(): String {
    val file = getBinraryFile(binaryName, binaryName, project)
    file.setExecutable(true)
    return file.absolutePath
  }

  fun canPalettise(file: File) : Boolean {
    val image = ImageIO.read(file.inputStream().buffered())

    val colors = HashSet<Int>()

    val w = image.width;
    val h = image.height;
    for (y in 0 .. h-1) {
      for (x in 0 .. w-1) {
        val color = image.getRGB(x, y);
        colors.add(color);

        if(colors.count() > maxColors) {
          return false
        }
      }
    }
    return true
  }


  fun palettiseImage(file :File) : File {

    // check the color count
    if(!canPalettise(file)) {
     return file
    }

    //convert file if needed
    return convert(file)

  }

  private fun convert(file : File) : File {
    // convert file
    val inputPath = file.absolutePath
    val binraryPath = loadBinary()
    val outputDir = File("${project.buildDir}/phictiurlann/pngquant")
    if (! outputDir.exists()) {
      outputDir.mkdirs()
    }

    val outputFile = File(outputDir, file.name)

    //Sample command
    // pngquant --quality=100-100 --output out.png image.png
    val outputArg = "--output ${outputFile.absolutePath}"
    val pb = ProcessBuilder(binraryPath, "-quality=100-100", outputArg, inputPath);
    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    val reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    return outputFile;
  }
}