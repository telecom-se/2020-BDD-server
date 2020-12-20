package fr.tse.advanced.databases.storage.exception;

public class SeriesNotFoundException extends RuntimeException {
	
	public SeriesNotFoundException(String s){
		super("\""+s+"\"");
	}

}
