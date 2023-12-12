package dev.frydae.fabric.events;

public enum EventPriority {
    HIGHEST(0),
    HIGH(1),
    NORMAL(2),
    LOW(3),
    LOWEST(4);

    private final int id;

    EventPriority(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EventPriority getPriority(int id) {
        for (EventPriority priority : values()) {
            if (priority.getId() == id) {
                return priority;
            }
        }

        return NORMAL;
    }
}
