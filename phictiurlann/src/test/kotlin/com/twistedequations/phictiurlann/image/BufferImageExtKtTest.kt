package com.twistedequations.phictiurlann.image

import com.google.common.truth.Truth
import org.junit.Test
import javax.imageio.ImageIO

class BufferImageExtKtTest {

  @Test
  fun isGrayscale() {
    val stream = BufferImageExtKtTest::class.java.getResourceAsStream("/images/buffered_image/is_gray_scale.jpg")
    val isGrayScale = ImageIO.read(stream).isGrayscale()
    Truth.assertThat(isGrayScale).isTrue()
  }

  @Test
  fun isNotGrayscale() {
    val stream = BufferImageExtKtTest::class.java.getResourceAsStream("/images/buffered_image/is_not_gray_scale.png")
    val isGrayScale = ImageIO.read(stream).isGrayscale()
    Truth.assertThat(isGrayScale).isFalse()
  }

  @Test
  fun hasLessThan256Colors() {
    val stream = BufferImageExtKtTest::class.java.getResourceAsStream("/images/buffered_image/less_then_256_colors.png")
    val has256Colors = ImageIO.read(stream).hasMoreThan256Colors()
    Truth.assertThat(has256Colors).isFalse()
  }

  @Test
  fun hasMoreThan256Colors() {
    val stream = BufferImageExtKtTest::class.java.getResourceAsStream("/images/buffered_image/more_than_256_colors.png")
    val has256Colors = ImageIO.read(stream).hasMoreThan256Colors()
    Truth.assertThat(has256Colors).isTrue()
  }

}