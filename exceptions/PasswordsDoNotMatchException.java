package exceptions;

public class PasswordsDoNotMatchException extends DisplayableException {

    public PasswordsDoNotMatchException() {
        super("Passwortfehler", "Die beiden Passwörter stimmen nicht überein.");
    }
    
}
