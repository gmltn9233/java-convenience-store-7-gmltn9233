package store.common.exception;

public class StoreException extends IllegalArgumentException {

    private static final String PREFIX = "[ERROR] ";
    private final String errorMessage;

    public StoreException(String message) {
        super(PREFIX + message);
        this.errorMessage = PREFIX + message;
    }
}
