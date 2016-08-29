package com.twistedequations.phictiurlann

import org.gradle.api.Plugin
import org.gradle.api.Project

class Phictiurlann implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("phictiurlann", PhictiurlannExtenstion)
    }
}
