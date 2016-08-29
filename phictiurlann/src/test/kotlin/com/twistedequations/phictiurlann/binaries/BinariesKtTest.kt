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
    val file = getBinraryFile("jpegtran", "jpegtran", project)
    val buildDirFile = File(buildDir, "${File.separator}phictiurlann${File.separator}binaries" +
        "${File.separator}jpegtran${File.separator}jpegtran")

    Truth.assertThat(file.exists()).isTrue()
    Truth.assertThat(buildDirFile.exists()).isTrue()
  }

}