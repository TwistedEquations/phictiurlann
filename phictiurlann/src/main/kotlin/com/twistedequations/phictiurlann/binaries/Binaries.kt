package com.twistedequations.phictiurlann.binaries

import com.twistedequations.phictiurlann.Phictiurlann
import org.gradle.api.Project
import java.io.File
import java.io.InputStream
import java.util.Locale

/**
 * Created by patrickd on 09/07/2016.
 */

private fun getOperatingSystemType(): OSType {
  val osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
  if ((osName.indexOf("mac") >= 0) || (osName.indexOf("darwin") >= 0)) {
    return OSType.MacOS;
  } else if (osName.indexOf("win") >= 0) {
    return OSType.Windows;
  } else if (osName.indexOf("nux") >= 0) {
    return OSType.Linux;
  } else {
    return OSType.Other;
  }
}

private fun getOsDiranme(osType: OSType) : String = when(osType) {
  OSType.Windows -> "win"
  OSType.MacOS -> "osx"
  OSType.Linux -> "linux"
  else -> throw IllegalArgumentException("os must be one of windows, mac, or unix based");
}

private fun getBinraryResourceStream(groupName: String, fileName: String) : InputStream {

  val osFileDirName = getOsDiranme(getOperatingSystemType())
  val resourcePath = "${File.separator}binaries${File.separator}" +
      "$groupName${File.separator}$osFileDirName${File.separator}$fileName"

  println("Loading binary resourcePath = $resourcePath")

  return Phictiurlann::class.java.getResourceAsStream(resourcePath)
}


fun getBinraryFile(groupName: String, fileName: String, project: Project) : File {

  val buildFolderName = "${project.buildDir.absolutePath}${File.separator}phictiurlann${File.separator}" +
      "binaries${File.separator}$groupName${File.separator}$fileName";

  println("Loading binary $buildFolderName")

  val file = File(buildFolderName);
  if(!file.exists()) {
    file.parentFile.mkdirs()
    file.createNewFile()
    val fileStream = file.outputStream();
    val resourceStream = getBinraryResourceStream(groupName, fileName)

    resourceStream.copyTo(fileStream)
    resourceStream.close()
    fileStream.close()
  }

  return file
}

enum class OSType {
  Windows, MacOS, Linux, Other
};