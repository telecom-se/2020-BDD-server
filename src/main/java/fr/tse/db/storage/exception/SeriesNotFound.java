package fr.tse.db.storage.exception;

/**
* This class defines an error that is thrown when a user tries to access a series
* that does not exist in the database
*
* @writer  Arnaud
* @author  Arnaud, Rémi, Youness
* @since   2020-11
*/
public class SeriesNotFound extends Exception {
	
	public SeriesNotFound(String s){
		super(s);
	}
}
