package com.passfortless.filebroless.utils;
public interface SystemUtils {
    String OS_NAME = System.getProperty("os.name").toLowerCase();
    boolean IS_WINDOWS = (OS_NAME.indexOf("win") >= 0);
    boolean IS_MAC = (OS_NAME.indexOf("mac") >= 0);
    boolean IS_UNIX = (OS_NAME.indexOf("nix") >= 0 || OS_NAME.indexOf("nux") >= 0 || OS_NAME.indexOf("aix") > 0 || OS_NAME.indexOf("freebsd") >= 0);
    boolean IS_SOLARIS = (OS_NAME.indexOf("sunos") >= 0);
}
