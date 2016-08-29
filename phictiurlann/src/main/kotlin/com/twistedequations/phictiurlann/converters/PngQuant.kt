package com.twistedequations.phictiurlann.converters

import com.google.common.annotations.VisibleForTesting
import com.twistedequations.phictiurlann.binaries.getBinraryFile
import com.twistedequations.phictiurlann.image.hasMoreThan256Colors
import com.twistedequations.phictiurlann.image.isPng
import com.twistedequations.phictiurlann.models.ConvertRequest
import com.twistedequations.phictiurlann.models.ConvertResult
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.HashSet
import javax.imageio.ImageIO

class Pngquant(private val project: Project) : Converter {

  private val binaryName = "pngquant"

  private fun loadBinary(): String {
    val file = getBinraryFile(binaryName, binaryName, project)
    file.setExecutable(true)
    return file.absolutePath
  }

  @VisibleForTesting
  internal fun canPalettise(file: File) : Boolean {
    val image = ImageIO.read(file.inputStream().buffered())
    return !image.hasMoreThan256Colors();
  }


  override fun convert(convertRequest: ConvertRequest) : ConvertResult {

    if(!isPng(convertRequest.fileInput)) {
      return ConvertResult.didNotRun(convertRequest);
    }

    // check the color count
    if(!canPalettise(convertRequest.fileInput)) {
      return ConvertResult.didNotRun(convertRequest);
    }

    //convert file if needed
    return palettiseImage(convertRequest)

  }

  private fun palettiseImage(convertRequest: ConvertRequest) : ConvertResult {
    // convert file
    val inputPath = convertRequest.fileInput.absolutePath
    val binraryPath = loadBinary()
    val outputFile = convertRequest.fileOutput

    //Sample command
    // pngquant --quality=100-100 --output out.png image.png
    val outputArg = "--output"
    val pb = ProcessBuilder(binraryPath, "--quality=100-100", outputArg, outputFile.absolutePath, inputPath);
    println("pngquant command = ${pb.command()}")

    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    val reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    return ConvertResult.didRun(convertRequest);
  }
}