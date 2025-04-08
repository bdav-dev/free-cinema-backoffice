package dev.bdav.fcbo.freeui.concurrency;

@FunctionalInterface
public interface InterimPublisher<T> {
    void publish(T interimValue);
}
