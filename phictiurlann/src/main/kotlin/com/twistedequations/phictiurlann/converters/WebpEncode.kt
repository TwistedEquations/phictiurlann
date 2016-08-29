package com.twistedequations.phictiurlann.converters

import com.twistedequations.phictiurlann.binaries.getBinraryFile
import com.twistedequations.phictiurlann.image.isJpg
import com.twistedequations.phictiurlann.image.isWebp
import com.twistedequations.phictiurlann.models.ConvertRequest
import com.twistedequations.phictiurlann.models.ConvertResult
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.InputStreamReader

class WebpEncode(private val project: Project) : Converter {

  private val binaryName = "cwebp"

  private fun loadBinary(): String {
    val file = getBinraryFile("webp", binaryName, project)
    file.setExecutable(true)
    return file.absolutePath
  }

  override fun convert(convertRequest: ConvertRequest): ConvertResult {

    if(isWebp(convertRequest.fileInput)) {
      return ConvertResult.didNotRun(convertRequest);
    }

    // convert file
    val inputPath = convertRequest.fileInput.absolutePath
    val binraryPath = loadBinary()
    val outputFile = convertRequest.fileOutput

    //Sample command
    // cwebp -optimize -copy none -outfile out.png image.png
    val pb = ProcessBuilder(binraryPath, "-q", "100", "-o", outputFile.absolutePath, "--", inputPath);
    println("cwebp command = ${pb.command()}")

    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    val reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    return ConvertResult.didRun(convertRequest);

  }
}