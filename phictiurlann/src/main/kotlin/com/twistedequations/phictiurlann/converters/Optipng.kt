package com.twistedequations.phictiurlann.converters

import com.twistedequations.phictiurlann.binaries.getBinraryFile
import com.twistedequations.phictiurlann.image.isPng
import com.twistedequations.phictiurlann.models.ConvertRequest
import com.twistedequations.phictiurlann.models.ConvertResult
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.InputStreamReader

class Optipng(private val project: Project) : Converter {

  private val binaryName = "optipng"

  private fun loadBinary(): String {
    val file = getBinraryFile(binaryName, binaryName, project)
    file.setExecutable(true)
    return file.absolutePath
  }


  override fun convert(convertRequest: ConvertRequest): ConvertResult {
    //convert file if needed
    if (! isPng(convertRequest.fileInput)) {
      return ConvertResult.didNotRun(convertRequest);
    }

    return palettiseImage(convertRequest)

  }

  private fun palettiseImage(convertRequest: ConvertRequest): ConvertResult {
    // convert file
    val inputPath = convertRequest.fileInput.absolutePath
    val binraryPath = loadBinary()
    val outputFile = convertRequest.fileOutput

    //Sample command
    // optipng -o2 - strip all -out image.png
    val pb = ProcessBuilder(binraryPath, "-o2", "-strip", "all", "-out", outputFile.absolutePath, inputPath);
    println("optipng command = ${pb.command()}")

    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    val reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    return ConvertResult.didRun(convertRequest);
  }
}