package fr.tse.advanced.databases.storage.exception;

public class TimestampAlreadyExistsException extends RuntimeException {

	public TimestampAlreadyExistsException(Long timestamp) {
		super("\""+timestamp+"\"");
	}
}
