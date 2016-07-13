package com.twistedequations.phictiurlann.binaries

import com.google.common.truth.Truth
import com.twistedequations.phictiurlann.Phictiurlann
import org.gradle.api.Project
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Created by patrickd on 09/07/2016.
 */
class ComparePngsTest {

  @Rule @JvmField
  var tempFolder = TemporaryFolder();

  lateinit var project : Project
  lateinit var buildDir: File

  @Before
  fun setUp() {
    //Given
    tempFolder.create()
    buildDir= tempFolder.newFolder();
    project = Mockito.mock(Project::class.java)

    Mockito.`when`(project.buildDir).thenReturn(buildDir)
  }

  @After
  fun tearDown() {
    tempFolder.delete()
  }

  @Test
  fun getBinraryFile() {

    //When
    val file = getBinraryFile("butteraugli", "compare_pngs", project)
    val buildDirFile = File(buildDir, "${File.separator}phictiurlann${File.separator}binraries" +
        "${File.separator}butteraugli${File.separator}compare_pngs")

    Truth.assertThat(file.exists()).isTrue()
    Truth.assertThat(buildDirFile.exists()).isTrue()
  }

  @Test
  fun comparePngs() {

    //Given
    val image1File = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}image1.png")

    val image2File = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}image2.png")

    image1File.parentFile.mkdirs()
    image2File.parentFile.mkdirs()
    image1File.createNewFile()
    image2File.createNewFile()

    //Copy images
    val outStream1 = image1File.outputStream()
    val outStream2 = image2File.outputStream()
    ComparePngsTest::class.java.getResourceAsStream("/images/butteraugli_image1.png").copyTo(outStream1)
    ComparePngsTest::class.java.getResourceAsStream("/images/butteraugli_image2.png").copyTo(outStream2)
    outStream1.close()
    outStream2.close()

    val file = getBinraryFile("butteraugli", "compare_pngs", project)
    file.setExecutable(true)

    Truth.assertThat(file.exists()).isTrue()

    //When
    val pb = ProcessBuilder(file.absolutePath, image1File.absolutePath, image2File.absolutePath);
    pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
    val process = pb.start();
    var reader = BufferedReader(InputStreamReader(process.inputStream));
    val lines = reader.readLines();
    process.waitFor()
    Truth.assertThat(lines[0].toDouble()).isWithin(0.01).of(8.791153)
  }

}