package com.red.testframework.utils;

public class OSUtils {

    public enum OsType {
        WINDOWS, MAC, LINUX, OTHER
    };
    private static final String osName = System.getProperty("os.name").toLowerCase();

    private static boolean isWindows() {
        return (osName.contains("win"));
    }

    private static boolean isMac() {
        return (osName.contains("mac"));
    }

    private static boolean isUnix() {
        return (osName.contains("nux"));
    }

    public static OsType getOsType() {
        if(isUnix()) return OsType.LINUX;
        if(isWindows()) return OsType.WINDOWS;
        if(isMac()) return OsType.MAC;
        return OsType.OTHER;
    }
}