package me.imstring.discordshopper.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.lang.reflect.Field;

public class ConfigurationLoader {

    private static final Dotenv dotenv = Dotenv.load();

    public static void loadConfig(Class<?> configClass) {
        for (Field field : configClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigurationOption.class)) {
                ConfigurationOption option = field.getAnnotation(ConfigurationOption.class);
                String envValue = dotenv.get(option.value());

                if (envValue != null) {
                    try {
                        field.setAccessible(true);
                        field.set(null, envValue);
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao carregar configuração: " + field.getName(), e);
                    }
                }
            }
        }
    }
}
