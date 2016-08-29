package com.twistedequations.phictiurlann.converters

import com.twistedequations.phictiurlann.models.ConvertRequest
import com.twistedequations.phictiurlann.models.ConvertResult

/**
 * Interface for wrapping image processing binaries
 */
interface Converter {

   fun convert(convertRequest: ConvertRequest) : ConvertResult

}