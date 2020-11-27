package fr.tse.db.storage.error;

public class BadQueryException extends Exception {
    public BadQueryException(String message) {
        super(message);
    }
}
