package com.passfortless.filebroless.platforms;

import com.passfortless.filebroless.utils.SystemUtils;

public class PlatformFactory {

    public static Platform getPlatform() {
        if (SystemUtils.IS_MAC) {
            return PlatformType.MAC;
        } else if (SystemUtils.IS_WINDOWS) {
            return PlatformType.WINDOWS;
        } else if (SystemUtils.IS_UNIX) {
            return PlatformType.LINUX;
        }
        throw new IllegalArgumentException("Platform not supported");
    }
}
