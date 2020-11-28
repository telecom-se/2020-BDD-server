package fr.tse.db.storage.exception;

/**
* This class defines an error that is thrown when a user tries to create a series
* that already exists in the database
*
* @writer  Arnaud
* @author  Arnaud, Rémi, Youness
* @since   2020-11
*/
public class SeriesAlreadyExists extends Exception {

	public SeriesAlreadyExists(String s) {
		super(s);
	}
}
