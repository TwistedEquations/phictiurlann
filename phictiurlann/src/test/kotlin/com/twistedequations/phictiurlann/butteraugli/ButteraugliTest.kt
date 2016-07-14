package com.twistedequations.phictiurlann.butteraugli

import com.google.common.truth.Truth
import com.twistedequations.phictiurlann.PhictiurlannExtenstion
import org.gradle.api.Project
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import java.io.File

/**
 * Created by patrickd on 13/07/2016.
 */
class ButteraugliTest {
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
    val file = com.twistedequations.phictiurlann.binaries.getBinraryFile("butteraugli", "compare_pngs", project)
    val buildDirFile = File(buildDir, "${File.separator}phictiurlann${File.separator}binraries" +
        "${File.separator}butteraugli${File.separator}compare_pngs")

    Truth.assertThat(file.exists()).isTrue()
    Truth.assertThat(buildDirFile.exists()).isTrue()
  }

  @Test
  fun compare() {

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
    ButteraugliTest::class.java.getResourceAsStream("/images/butteraugli_image1.png").copyTo(outStream1)
    ButteraugliTest::class.java.getResourceAsStream("/images/butteraugli_image2.png").copyTo(outStream2)
    outStream1.close()
    outStream2.close()

    val value = Butteraugli(project, PhictiurlannExtenstion()).compare(image1File, image2File)

    Truth.assertThat(value).isWithin(0.001).of(8.791153)
  }

  @Test
  fun isAcceptable() {

    //Given
    val image1File = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}image1.png")

    val image2File = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}image1.png")

    image1File.parentFile.mkdirs()
    image2File.parentFile.mkdirs()
    image1File.createNewFile()
    image2File.createNewFile()

    //Copy images
    val outStream1 = image1File.outputStream()
    val outStream2 = image2File.outputStream()
    ButteraugliTest::class.java.getResourceAsStream("/images/butteraugli_image1.png").copyTo(outStream1)
    ButteraugliTest::class.java.getResourceAsStream("/images/butteraugli_image2.png").copyTo(outStream2)
    outStream1.close()
    outStream2.close()

    val value = Butteraugli(project, PhictiurlannExtenstion()).compare(image1File, image2File)
    val result = Butteraugli(project, PhictiurlannExtenstion()).isAcceptable(value)

    Truth.assertThat(result).isTrue()
  }
}