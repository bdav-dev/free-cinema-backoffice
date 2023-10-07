package exceptions;

public class UsernameAlreadyExistsException extends DisplayableException {

    public UsernameAlreadyExistsException() {
        super("Namenkonflikt", "Es existiert bereits ein Benutzer mit diesem Name");
    }

    public UsernameAlreadyExistsException(String name) {
        super("Namenkonflikt", "Es existiert bereits ein Benutzer mit dem Namen '" + name + "'");
    }

    
    
}
