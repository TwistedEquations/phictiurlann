package com.twistedequations.phictiurlann.converters

import com.google.common.truth.Truth
import com.twistedequations.phictiurlann.converters.Pngquant
import com.twistedequations.phictiurlann.models.ConvertRequest
import com.twistedequations.phictiurlann.models.ConvertResult
import org.gradle.api.Project
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import java.io.File

class OptipngTest {

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
  fun optipngConvert() {
    val converter = Optipng(project)

    //Given
    val imageInputFile = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}imagein.png")

    val imageOutputFile = File(buildDir, "${File.separator}phictiurlann${File.separator}pngs" +
        "${File.separator}imageout.png")

    imageInputFile.parentFile.mkdirs()

    val outStream = imageInputFile.outputStream()
    PngquantTest::class.java.getResourceAsStream("/images/optipng/optipng.png").copyTo(outStream)
    outStream.close()

    val result = converter.convert(ConvertRequest(imageInputFile, imageOutputFile))

    Truth.assertThat(result.didConvert).isTrue()
    Truth.assertThat(result.fileOutput?.exists()).isTrue()
    Truth.assertThat(imageOutputFile.length()).isLessThan(imageInputFile.length())
  }

  @After
  fun tearDown() {
    tempFolder.delete()
  }

}