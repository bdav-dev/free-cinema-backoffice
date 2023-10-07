package free_ui;

import ui.pages.ConfirmExitPage;

public abstract class DatatypePage extends Page {

    public static enum AccessType {
        READ, EDIT, CREATE;
    }

    private AccessType accessType;

    public DatatypePage(AccessType accessType) {
        super();
        this.accessType = accessType;
    }

    public void prepareUI() {
        switch (accessType) {
            case READ -> prepareRead();
            case EDIT -> prepareEdit();
            case CREATE -> prepareCreate();
        }
    }

    @Override
    public void onClose() {
        if (accessType == AccessType.READ) {
            defaultOnCloseOperation();
            return;
        }

        addChildPanelToTopParent(() -> new ConfirmExitPage("Schließen", "Zurück", "Fenster schließen?",
                "Womöglich gibt es ungespeicherte Änderungen.", "Möchtest du dieses Fenster wirklich schließen?"));
    }

    public abstract void prepareEdit();

    public abstract void prepareRead();

    public abstract void prepareCreate();

    public abstract void save();

    public abstract void create();

    public void discard() {
        onClose();
    }
}
