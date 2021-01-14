package fr.tse.db.query.error;

public class BadQueryException extends Exception {
	public static final String ERROR_MESSAGE_BAD_ACTION = "Bad action provided";
	
	public static final String ERROR_MESSAGE_CREATE_GENERAL = "Error in CREATE query";
	public static final String ERROR_MESSAGE_CREATE_IN_NAME_SPACES = "Error in CREATE query (space in name)";
	public static final String ERROR_MESSAGE_CREATE_IN_NAME_SPECIAL_CHARACTERS = "Error in CREATE query (special characters not allowed in name)";
	public static final String ERROR_MESSAGE_CREATE_IN_TYPE = "Error in CREATE query (type not exist)";
	
	public static final String ERROR_MESSAGE_INSERT_GENERAL = "Error in INSERT query";
	public static final String ERROR_MESSAGE_INSERT_IN_VALUES = "Error in inserted values";
	public static final String ERROR_MESSAGE_INSERT_IN_TYPE = "Wrong type provided for insert";

	
    public BadQueryException(String message) {
        super(message);
    }
}
