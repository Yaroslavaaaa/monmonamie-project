package kz.narxoz.monamieproject.observer.listeners;

import java.io.File;

public interface EventListener {
    void subscribe(String email);
    void unsubscribe(String email);
    void news(String news);
}
