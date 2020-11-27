package fr.tse.db.query.error;

public class BadQueryException extends Exception {
    public BadQueryException(String message) {
        super(message);
    }
}
