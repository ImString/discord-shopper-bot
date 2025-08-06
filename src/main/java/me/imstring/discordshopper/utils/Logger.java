package me.imstring.discordshopper.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Logger {
    private static final String APP_NAME = "DISCORD-SHOPPER";

    private final static boolean LOG_PRINT_STACK_SOURCE = false;
    private final static LogLevel minLogLevel = LogLevel.DEBUG;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void debug(Object... message) {
        print(LogLevel.DEBUG, message);
    }

    public static void info(Object... message) {
        print(LogLevel.INFO, message);
    }

    public static void warn(Object... message) {
        print(LogLevel.WARN, message);
    }

    public static void error(Object... message) {
        print(LogLevel.ERROR, message);
    }

    public static void error(Throwable e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    public static void error(Throwable e, Object... message) {
        print(LogLevel.ERROR, message);
    }

    private static void print(LogLevel logLevel, Object... message) {
        if (logLevel.ordinal() >= minLogLevel.ordinal()) {
            String timestamp = DATE_FORMAT.format(new Date());
            String level = logLevel.toString();
            String msg = Arrays.toString(message)
                    .replaceFirst("^\\[", "")
                    .replaceFirst("]$", "");

            System.out.printf(logLevel.getColor() + "%s [%s] [%s] %s%n", timestamp, APP_NAME, level, msg);

            if (LOG_PRINT_STACK_SOURCE) {
                StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                for (int i = 3; i < 6 && i < stack.length; i++) {
                    System.out.printf("%40s:\t\t%s%n", level, stack[i].toString());
                }
            }
        }
    }
}
