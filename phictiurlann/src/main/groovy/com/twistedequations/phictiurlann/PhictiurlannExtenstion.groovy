package com.twistedequations.phictiurlann

import org.gradle.api.Plugin
import org.gradle.api.Project

class PhictiurlannExtenstion {

    public boolean optimiseJpg = true;

    public boolean optimisePng = true;

    public boolean useWebp = true;

    public File imageDir;

    public int quality = 95;

    public int butteraugliThreshold = 1.1;
}
