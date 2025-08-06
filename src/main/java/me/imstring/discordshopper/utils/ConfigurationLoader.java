package me.imstring.discordshopper.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.lang.reflect.Field;

public class ConfigurationLoader {

    private static final Dotenv dotenv = Dotenv.load();

    public static void loadConfig(Class<?> configClass) {
        for (Field field : configClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigurationOption.class)) continue;

            ConfigurationOption option = field.getAnnotation(ConfigurationOption.class);
            String envValue = dotenv.get(option.value());

            if (envValue == null) continue;

            try {
                field.setAccessible(true);
                Class<?> type = field.getType();

                Object parsedValue;

                if (type == String.class) {
                    parsedValue = envValue;
                } else if (type == int.class || type == Integer.class) {
                    parsedValue = Integer.parseInt(envValue);
                } else if (type == boolean.class || type == Boolean.class) {
                    parsedValue = Boolean.parseBoolean(envValue);
                } else if (type.isEnum()) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Enum> enumType = (Class<? extends Enum>) type;
                    parsedValue = Enum.valueOf(enumType, envValue.toUpperCase());
                } else {
                    throw new RuntimeException("Tipo não suportado: " + type.getName());
                }

                field.set(null, parsedValue);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao carregar configuração: " + field.getName(), e);
            }
        }
    }
}
