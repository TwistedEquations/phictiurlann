package com.twistedequations.phictiurlann.models

import java.io.File

data class ConvertResult(val fileInput: File,
                         val fileOutput: File? = null,
                         val didConvert: Boolean = false,
                         val sizeDiff: Long = 0) {
  companion object {
    fun didNotRun(convertRequest: ConvertRequest): ConvertResult {

      return ConvertResult(fileInput = convertRequest.fileInput,
                           didConvert = false);
    }

    fun didRun(convertRequest: ConvertRequest): ConvertResult {

      val sizeDiff = Math.abs(convertRequest.fileInput.length() - convertRequest.fileOutput.length())

      return ConvertResult(fileInput = convertRequest.fileInput,
                           fileOutput = convertRequest.fileOutput,
                           didConvert = true,
                           sizeDiff = sizeDiff);
    }
  }

}

data class ConvertRequest(val fileInput: File, val fileOutput: File)