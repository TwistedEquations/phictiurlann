package com.twistedequations.phictiurlann

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by patrickd on 07/07/2016.
 */
class Phictiurlann implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("phictiurlann", PhictiurlannExtenstion)
    }
}
