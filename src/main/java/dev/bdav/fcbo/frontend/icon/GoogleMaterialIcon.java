package dev.bdav.fcbo.frontend.icon;

import dev.bdav.fcbo.freeui.interfaces.IconCodeProvidable;

public enum GoogleMaterialIcon implements IconCodeProvidable {

    WEST("\uf1e6"),
    ACCOUNT_CIRCLE("\ue853"),
    POWER_SETTINGS_NEW("\ue8ac"),
    LOGIN("\uea77"),
    PERSON_ADD("\ue7fe"),
    PASSKEY("\uf87f"),
    EMOJI_PEOPLE("\uea1d"),
    DATABASE("\uf20e"),
    VISIBILITY("\ue8f4"),
    VISIBILITY_OFF("\ue8f5");

    private final String iconCode;

    GoogleMaterialIcon(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getIconCode() {
        return iconCode;
    }

}
