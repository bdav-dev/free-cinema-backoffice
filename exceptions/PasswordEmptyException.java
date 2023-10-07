package exceptions;

public class PasswordEmptyException extends DisplayableException {

    public PasswordEmptyException() {
        super("Passwortfehler", "Das Password ist leer.");
    }
     
}
