package io.github.iwag.newsapp;

import org.greenrobot.eventbus.EventBus;

public class GlobalBus {
    private static EventBus eventBus;
    public static EventBus getBus() {
        if (eventBus==null) {
            eventBus = new EventBus();
        }
        return eventBus;
    }
}
