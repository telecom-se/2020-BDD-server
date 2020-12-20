package fr.tse.advanced.databases.storage.exception;

public class SeriesAlreadyExistsException extends RuntimeException {

	public SeriesAlreadyExistsException(String s) {
		super("\""+s+"\"");
	}
}
