package com.github.kataokanagi.utils;

// Android-like logging helper
public class Log {

    private static final boolean ENABLE_DEBUG = true;
    private static final boolean ENABLE_VERBOSE = true;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    // DEBUG
    public static void d(String tag, String msg) {
        if (ENABLE_DEBUG) {
            System.out.printf("[%s] %s\n", tag, msg);
        }
    }

    // VERBOSE
    public static void v(String tag, String msg) {
        if (ENABLE_VERBOSE) {
            System.out.printf("[%s] %s\n", tag, msg);
        }
    }

    // ERROR
    public static void e(String tag, String msg) {
        System.err.printf(ANSI_RED + "[%s] %s\n" + ANSI_RESET, tag, msg);
    }

    // WARNING
    public static void w(String tag, String msg) {
        System.out.printf(ANSI_YELLOW + "[%s] %s\n" + ANSI_RESET, tag, msg);
    }

}
