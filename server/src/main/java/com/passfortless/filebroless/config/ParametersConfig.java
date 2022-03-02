package com.passfortless.filebroless.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) // instance is immutable
@ToString
public class ParametersConfig {

    @NonNull // workingDirectory and port cannot be null since they are required by the
             // builder
    private final String workingDirectory;
    private final int port;

    private boolean openBrowser;
    private String browserName;

    private static ParametersConfigBuilder builder() {
        return new ParametersConfigBuilder();
    }

    public static ParametersConfigBuilder builder(int port, String workingDirectory) {
        return builder().port(port).workingDirectory(workingDirectory);
    }
}
