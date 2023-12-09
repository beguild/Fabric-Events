package dev.frydae.fabric.events;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled();
}