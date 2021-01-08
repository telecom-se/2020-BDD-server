package fr.tse.db.query.error;

public class SeriesAlreadyExistsQueryException extends Exception{
    public SeriesAlreadyExistsQueryException(String message) {
        super(message);
    }
}
