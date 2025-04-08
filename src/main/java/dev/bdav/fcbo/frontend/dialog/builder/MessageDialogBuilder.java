package dev.bdav.fcbo.frontend.dialog.builder;

import dev.bdav.fcbo.backend.exception.FrontendException;
import dev.bdav.fcbo.freeui.components.SpecialButton;
import dev.bdav.fcbo.freeui.core.UI;
import dev.bdav.fcbo.frontend.dialog.MessageDialog;

public class MessageDialogBuilder {

    private MessageDialogBuilder() {
    }

    public static MessageDialog frontendException(FrontendException frontendException) {
        return new MessageDialog(
                UI.get(),
                MessageDialog.Content.of(
                        MessageDialog.Type.ERROR,
                        frontendException.getUserFriendlyTitle(),
                        frontendException.getUserFriendlyDescription(),
                        MessageDialog.Content.Button.of(
                                "Schließen",
                                SpecialButton.Variant.DEFAULT,
                                Runnable::run
                        )
                )
        );
    }

}
