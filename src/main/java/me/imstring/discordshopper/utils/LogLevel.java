package me.imstring.discordshopper.utils;

import lombok.Getter;

public enum LogLevel {
    DEBUG("\033[1;36m"),
    INFO("\033[1;34m"),
    WARN("\033[1;93m"),
    ERROR("\033[1;31m");

    @Getter
    final String color;

    LogLevel(String color) {
        this.color = color;
    }
}
