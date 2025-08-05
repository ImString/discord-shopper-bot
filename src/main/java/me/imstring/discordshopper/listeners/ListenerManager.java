package me.imstring.discordshopper.listeners;

import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.utils.Logger;

@RequiredArgsConstructor
public class ListenerManager {

    private final Core instance;

    public void register() {
        ClassPath classPath;
        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (Exception e) {
            Logger.fatal(e);
            return;
        }

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("me.imstring.discordshopper.listeners.executors")) {
            try {
                Class<?> clazz = classInfo.load();
                Object listenerInstance = clazz.getDeclaredConstructor(Core.class).newInstance(instance);
                if (!listenerInstance.getClass().getSuperclass().getName().equals("net.dv8tion.jda.api.hooks.ListenerAdapter")) {
                    continue;
                }

                instance.getJdaBuilder().addEventListeners(listenerInstance);
            } catch (Exception e) {
                Logger.fatal(e);
            }
        }
    }
}
