package dev.frydae.fabric.events;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import dev.frydae.beguild.systems.Log;
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

    /**
     * Calls the provided event
     * </p>
     * Iterates through all registered listeners and calls the method if the event type matches
     * </p>
     * Sorts the listeners by priority and then sorts them by whether they ignore cancelled events
     *
     * @param event The event to call
     */
    void callEvent(@NotNull Event event) {
        IntStream.rangeClosed(EventPriority.HIGHEST.getId(), EventPriority.LOWEST.getId())
                .forEach(i -> registeredListeners.get(EventPriority.getPriority(i))
                .stream()
                .filter(registeredListener -> registeredListener.type().equals(event.getClass()))
                .sorted((a, b) -> Boolean.compare(a.ignoreCancelled(), b.ignoreCancelled()))
                .forEach(registeredListener -> {
                    if (!(event instanceof Cancellable cancellable) || !cancellable.isCancelled() || !registeredListener.ignoreCancelled()) {
                        try {
                            registeredListener.method().invoke(registeredListener.listener(), event);
                        } catch (Exception e) {
                            Log.exception("Error calling event " + event.getClass().getSimpleName(), e);
                        }
                    }
                }));
    }

    /**
     * Registers all event handlers in the provided listeners
     * </p>
     * Searches for all methods annotated with {@link EventHandler} and registers them with the type of event in the parameter
     *
     * @param listeners The listeners to register
     */
    public void registerEvents(@NotNull Listener... listeners) {
        for (Listener listener : listeners) {
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
}
