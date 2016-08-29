package com.twistedequations.phictiurlann.converters

import com.twistedequations.phictiurlann.binaries.getBinraryFile
import com.twistedequations.phictiurlann.image.isGrayscale
import com.twistedequations.phictiurlann.image.isJpg
import com.twistedequations.phictiurlann.models.ConvertRequest
import com.twistedequations.phictiurlann.models.ConvertResult
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.imageio.ImageIO

class Jpegtran(private val project: Project) : Converter {

  private val binaryName = "jpegtran"

  private fun loadBinary(): String {
    val file = getBinraryFile(binaryName, binaryName, project)
    file.setExecutable(true)
    return file.absolutePath
  }

  override fun convert(convertRequest: ConvertRequest): ConvertResult {

    if(!isJpg(convertRequest.fileInput)) {
      return ConvertResult.didNotRun(convertRequest);
    }

    val isGrayScale = ImageIO.read(convertRequest.fileInput).isGrayscale()

    // convert file
    val inputPath = convertRequest.fileInput.absolutePath
    val binraryPath = loadBinary()
    val outputFile = convertRequest.fileOutput

    val args = mutableListOf(binraryPath, "-copy", "none", "-optimize", "-outfile")
    if(isGrayScale) {
      args += "-grayscale"
    }

    args += arrayOf(outputFile.absolutePath, inputPath)

    //Sample command
    // jpegtran -optimize -copy none -outfile out.png image.png
    val pb = ProcessBuilder(args);
    println("jpegtran command = ${pb.command()}")

    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    val reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    return ConvertResult.didRun(convertRequest);
  }
}