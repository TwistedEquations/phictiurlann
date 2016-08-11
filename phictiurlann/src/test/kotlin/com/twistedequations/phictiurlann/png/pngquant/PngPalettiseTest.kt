package com.twistedequations.phictiurlann.png.pngquant

import com.google.common.truth.Truth
import com.twistedequations.phictiurlann.butteraugli.ButteraugliTest
import org.gradle.api.Project
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import java.io.File

class PngPalettiseTest {

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

  @Test
  fun canPalettise() {
    val pngPaletise = PngPalettise(project)

    //Given
    val image1File = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}image1.png")

    image1File.parentFile.mkdirs()
    image1File.createNewFile()
    val outStream1 = image1File.outputStream()

    ButteraugliTest::class.java.getResourceAsStream("/images/pngpalettise_canpalettise.png").copyTo(outStream1)
    outStream1.close()

    val canPaletise = pngPaletise.canPalettise(image1File)
    Truth.assertThat(canPaletise).isTrue()

  }

  @Test
  fun cantPalettise() {
    val pngPaletise = PngPalettise(project)

    //Given
    val image1File = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}image1.png")

    image1File.parentFile.mkdirs()
    image1File.createNewFile()
    val outStream1 = image1File.outputStream()

    ButteraugliTest::class.java.getResourceAsStream("/images/pngpalettise_cantpalettise.png").copyTo(outStream1)
    outStream1.close()

    val canPaletise = pngPaletise.canPalettise(image1File)
    Truth.assertThat(canPaletise).isFalse()

  }

  @Test
  fun palettiseImage() {

  }

  @After
  fun tearDown() {
    tempFolder.delete()
  }

}