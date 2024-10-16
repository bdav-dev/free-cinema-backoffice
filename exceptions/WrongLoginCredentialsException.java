package exceptions;

public class WrongLoginCredentialsException extends DisplayableException {

    public WrongLoginCredentialsException(String description) {
        super("Anmeldung nicht erfolgreich", description);
    }

    

}
