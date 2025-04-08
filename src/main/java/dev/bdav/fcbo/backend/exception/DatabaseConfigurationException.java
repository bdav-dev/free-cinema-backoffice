package dev.bdav.fcbo.backend.exception;

public class DatabaseConfigurationException extends FrontendException {
    public DatabaseConfigurationException(String message) {
        super(message);
    }

    @Override
    public String getUserFriendlyTitle() {
        return "Keine Datenbankkonfiguration";
    }

    @Override
    public String getUserFriendlyDescription() {
        return "Diese Aktion konnte nicht ausgeführt werden, da die Verbindung zur Datenbank nicht konfiguriert wurde.\n\nBitte konfiguriere die Datenbankverbindung in den Einstellungen.";
    }
}
