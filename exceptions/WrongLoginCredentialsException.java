package exceptions;

public class WrongLoginCredentialsException extends DisplayableException {

    public WrongLoginCredentialsException() {
        super("Anmeldung nicht erfolgreich", "Falsche Anmeldedaten: Passwort oder Benutzername falsch");
    }

}
