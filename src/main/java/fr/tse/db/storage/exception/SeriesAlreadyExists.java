package fr.tse.db.storage.exception;

/**
* This class defines an error that is thrown when a user tries to create a
* {@link fr.tse.db.storage.data.Series Series} that already exists in the
* {@link fr.tse.db.storage.data.DataBase DataBase}
*
* @author  Arnaud, Rémi, Youness
* @since   2020-11
*/
public class SeriesAlreadyExists extends Exception {

	public SeriesAlreadyExists(String s) {
		super(s);
	}
}
