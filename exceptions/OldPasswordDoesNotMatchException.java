package exceptions;

public class OldPasswordDoesNotMatchException extends DisplayableException {

    public OldPasswordDoesNotMatchException() {
        super("Passwortfehler", "Das aktuelle Passwort stimmt nicht.");
    }
    
}
