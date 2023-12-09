package dev.frydae.fabric.events;

import java.lang.reflect.Method;

public record RegisteredListener(Listener listener, Class<?> type, Method method, boolean ignoreCancelled) {}
