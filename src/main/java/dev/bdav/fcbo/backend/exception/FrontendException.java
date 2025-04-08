package dev.bdav.fcbo.backend.exception;

public abstract class FrontendException extends RuntimeException {
    public FrontendException() {
        super();
    }

    public FrontendException(String message) {
        super(message);
    }

    public FrontendException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrontendException(Throwable cause) {
        super(cause);
    }

    protected FrontendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract String getUserFriendlyTitle();
    public abstract String getUserFriendlyDescription();
}
