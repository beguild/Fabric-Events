package dev.frydae.fabric.events;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.stream.IntStream;

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

    private final ListMultimap<EventPriority, RegisteredListener> registeredListeners = ArrayListMultimap.create();

    void callEvent(@NotNull Event event) {
        IntStream.rangeClosed(EventPriority.HIGHEST.getId(), EventPriority.LOWEST.getId())
                .forEach(i ->
                        registeredListeners.get(EventPriority.getPriority(i))
                                .stream()
                                .filter(registeredListener -> registeredListener.type().isAssignableFrom(event.getClass()))
                                .sorted((a, b) -> Boolean.compare(a.ignoreCancelled(), b.ignoreCancelled()))
                                .filter(registeredListener -> !(event instanceof Cancellable cancellable) || !cancellable.isCancelled() || !registeredListener.ignoreCancelled())
                                .forEach(registeredListener -> {
                                    try {
                                        registeredListener.method().invoke(registeredListener.listener(), event);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                })
                );
    }

    public void registerEvents(@NotNull Listener listener) {
        Method[] methods = listener.getClass().getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                EventHandler handlerAnnotation = method.getAnnotation(EventHandler.class);
                Class<?> parameterType = method.getParameterTypes()[0];

                Preconditions.checkArgument(method.getParameterCount() == 1, "Event handler methods must have exactly one parameter");
                Preconditions.checkArgument(Event.class.isAssignableFrom(parameterType), "Event handler methods must have exactly one parameter of type Event");

                registeredListeners.put(handlerAnnotation.priority(), new RegisteredListener(listener, parameterType, method, handlerAnnotation.ignoreCancelled()));
            }
        }
    }
}
