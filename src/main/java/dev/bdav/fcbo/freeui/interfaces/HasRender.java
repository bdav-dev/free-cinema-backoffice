package dev.bdav.fcbo.freeui.interfaces;

public interface HasRender {

    default void resetView() {
        removeAll();
    }

    default void rerender() {
        resetView();
        render();
    }

    void render();

    void removeAll();

}
