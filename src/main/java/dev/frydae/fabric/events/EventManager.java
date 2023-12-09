package dev.frydae.fabric.events;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public final class EventManager {
    // region Singleton Stuff
    private static volatile EventManager instance;

    private EventManager() {}

    public static EventManager getInstance() {
        if (instance == null) {
            synchronized (EventManager.class) {
                if (instance == null) {
                    instance = new EventManager();
                }
            }
        }
        
        return instance;
    }
    // endregion

    private final List<RegisteredListener> registeredListeners = Lists.newArrayList();

    private final Map<Class<?>, List<RegisteredListener>> listenerCache = Maps.newHashMap();

    void callEvent(@NotNull Event event) {
//        if (!listenerCache.containsKey(event.getClass())) {
//            List<RegisteredListener> list = registeredListeners.stream()
//                    .filter(listener -> listener.type().isAssignableFrom(event.getClass()))
//                    .sorted((a, b) -> Boolean.compare(a.ignoreCancelled(), b.ignoreCancelled()))
//                    .toList();
//
//            listenerCache.put(event.getClass(), list);
//        }

        if (listenerCache.containsKey(event.getClass())) {
            listenerCache.get(event.getClass())
                    .stream()
                    .filter(registeredListener ->  !(event instanceof Cancellable cancellable) || !cancellable.isCancelled() || !registeredListener.ignoreCancelled())
                    .forEach(registeredListener -> {
                        try {
                            registeredListener.method().invoke(registeredListener.listener(), event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public void registerEvents(@NotNull Listener listener) {
        Method[] methods = listener.getClass().getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                EventHandler handlerAnnotation = method.getAnnotation(EventHandler.class);
                Class<?> parameterType = method.getParameterTypes()[0];

                Preconditions.checkArgument(method.getParameterCount() == 1, "Event handler methods must have exactly one parameter");
                Preconditions.checkArgument(Event.class.isAssignableFrom(parameterType), "Event handler methods must have exactly one parameter of type Event");

                registeredListeners.add(new RegisteredListener(listener, parameterType, method, handlerAnnotation.ignoreCancelled()));

                List<RegisteredListener> list = registeredListeners.stream()
                        .filter(registeredListener -> registeredListener.type().isAssignableFrom(parameterType))
                        .sorted((a, b) -> Boolean.compare(a.ignoreCancelled(), b.ignoreCancelled()))
                        .toList();

                listenerCache.put(parameterType, list);
            }
        }
    }
}
