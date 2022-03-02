package com.passfortless.filebroless.platforms;

import java.util.Arrays;
import java.util.Optional;

import com.passfortless.filebroless.utils.FunctionalUtil;

public enum PlatformType implements Platform {
    MAC("mac"), LINUX("linux"), WINDOWS("windows");

    private final String name;

    PlatformType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Optional<Platform> get(String name) {
        return Arrays.stream(values())
                .filter(p -> p.name.equalsIgnoreCase(name))
                .findFirst().flatMap(FunctionalUtil.cast(Platform.class));
    }
}
