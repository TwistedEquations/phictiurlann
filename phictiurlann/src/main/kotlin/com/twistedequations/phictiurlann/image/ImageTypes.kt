package com.twistedequations.phictiurlann.image

import java.io.File

/**
 * Created by patrickd on 10/07/2016.
 */

private val PNG = Regex("png")
private val JPG = Regex("jpe?g")
private val WEBP = Regex("webp")

fun isPng(file : File) = file.extension.matches(PNG)

fun isJpg(file : File) = file.extension.matches(JPG)

fun isWebp(file : File) = file.extension.matches(WEBP)

fun isSupportedImage(file : File) = isPng(file) || isJpg(file) || isWebp(file)