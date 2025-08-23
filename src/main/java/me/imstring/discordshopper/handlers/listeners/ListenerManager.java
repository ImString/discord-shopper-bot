package me.imstring.discordshopper.handlers.listeners;

import com.google.common.reflect.ClassPath;
import lombok.RequiredArgsConstructor;
import me.imstring.discordshopper.Core;
import me.imstring.discordshopper.utils.Logger;

@RequiredArgsConstructor
public class ListenerManager {

    private final Core instance;

    public void registerAll() {
        Logger.info("Registering listeners...");

        ClassPath classPath;
        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (Exception e) {
            Logger.error(e);
            return;
        }

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(this.getClass().getPackageName() + ".executors")) {
            try {
                Class<?> clazz = classInfo.load();
                Object listenerInstance = clazz.getDeclaredConstructor(Core.class).newInstance(instance);
                if (!listenerInstance.getClass().getSuperclass().getName().equals("net.dv8tion.jda.api.hooks.ListenerAdapter")) {
                    continue;
                }

                instance.getJdaBuilder().addEventListeners(listenerInstance);
                Logger.info("Registered listener: " + clazz.getSimpleName());
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }
}
