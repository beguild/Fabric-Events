package dev.frydae.fabric.events;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

public abstract class Event {
    /**
     * Shortcut method to call this event through the {@link EventManager}.
     *
     * <p>Will return false if the event was cancelled.
     *
     * <p>Result can be ignored if you don't care if it was cancelled
     *
     * @return true if the event was not cancelled, false if it was
     */
    @CanIgnoreReturnValue
    public boolean callEvent() {
        EventManager.getInstance().callEvent(this);

        if (this instanceof Cancellable) {
            return !((Cancellable) this).isCancelled();
        }

        return true;
    }
}
