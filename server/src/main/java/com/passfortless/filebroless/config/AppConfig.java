package com.passfortless.filebroless.config;

import java.util.Map;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithParentName;

@ConfigMapping(prefix = "filebroless")
public interface AppConfig {

    Map<String, BrowserConfig> browser();

    interface BrowserConfig {

        @WithParentName
        Map<String,String> config();
    }
}
