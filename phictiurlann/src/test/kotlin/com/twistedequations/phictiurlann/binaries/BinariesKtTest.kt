package com.twistedequations.phictiurlann.binaries

import com.google.common.truth.Truth
import org.gradle.api.Project
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito
import java.io.File

/**
 * Created by patrickd on 09/07/2016.
 */
class BinariesTest {

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
  fun getBinraryFile() {

    //When
    val file = getBinraryFile("jpegoptim", "jpegoptim", project)
    val buildDirFile = File(buildDir, "${File.separator}phictiurlann${File.separator}binraries" +
        "${File.separator}jpegoptim${File.separator}jpegoptim")

    Truth.assertThat(file.exists()).isTrue()
    Truth.assertThat(buildDirFile.exists()).isTrue()
  }

}