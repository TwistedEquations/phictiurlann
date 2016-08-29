package com.twistedequations.phictiurlann.tasks

import com.twistedequations.phictiurlann.CoreKt
import com.twistedequations.phictiurlann.PhictiurlannExtenstion
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class OptimiseImageTask extends DefaultTask {

    @TaskAction
    def task() {

        File imageDir = getProject().phictiurlann.imageDir
        PhictiurlannExtenstion extenstion = getProject().phictiurlann
        if(!imageDir.exists() || !imageDir.isDirectory()) {
            throw new IllegalArgumentException("Image dir ${imageDir.absolutePath} does not exist")
        }

        CoreKt.optimiseImages(imageDir, extenstion)
    }
}
