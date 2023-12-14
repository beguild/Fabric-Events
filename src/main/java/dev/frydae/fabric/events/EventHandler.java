package dev.frydae.fabric.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    /**
     * Define if the handler ignores a cancelled event.
     * <p>
     * If ignoreCancelled is true and the event is cancelled, the method is
     * not called. Otherwise, the method is always called.
     *
     * @return whether cancelled events should be ignored
     */
    boolean ignoreCancelled() default false;

    /**
     * Define the priority of the event.
     * <p>
     * First priority to the last priority executed:
     * <ol>
     *     <li>HIGHEST</li>
     *     <li>HIGH</li>
     *     <li>NORMAL</li>
     *     <li>LOW</li>
     *     <li>LOWEST</li>
     * </ol>
     *
     * @return the priority
     */
    EventPriority priority() default EventPriority.NORMAL;
}
