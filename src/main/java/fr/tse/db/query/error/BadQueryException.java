package fr.tse.db.query.error;

public class BadQueryException extends Exception {
	public static final String ERROR_MESSAGE_BAD_ACTION = "Bad action provided";

	public static final String ERROR_MESSAGE_SELECT_GENERAL = "Error in SELECT query";
	public static final String ERROR_MESSAGE_SELECT_NO_AGGREGATION = "Error in SELECT query (no aggregation provided)";
	
	public static final String ERROR_MESSAGE_CREATE_GENERAL = "Error in CREATE query";
	public static final String ERROR_MESSAGE_CREATE_IN_NAME_SPECIAL_CHARACTERS = "Error in CREATE query (special characters not allowed in name)";
	public static final String ERROR_MESSAGE_CREATE_IN_TYPE = "Error in CREATE query (type not exist)";
	
	public static final String ERROR_MESSAGE_INSERT_GENERAL = "Error in INSERT query";
	public static final String ERROR_MESSAGE_INSERT_IN_VALUES = "Error in inserted values";
	public static final String ERROR_MESSAGE_INSERT_IN_TYPE = "Wrong type provided for insert";

	public static final String ERROR_MESSAGE_DELETE_GENERAL_1 = "Error in query";
	public static final String ERROR_MESSAGE_DELETE_GENERAL_2 = "Error in DELETE query";
	public static final String ERROR_MESSAGE_DELETE_IN_NAME = "Incorrect series name provided";

	public static final String ERROR_MESSAGE_SHOW_GENERAL = "Error in SHOW query";
	
	public static final String ERROR_MESSAGE_CONDITIONS_TOO_MANY = "Too many conditions";
	public static final String ERROR_MESSAGE_CONDITIONS_GENERAL = "Error in conditions"; // Followed by a number.
    public BadQueryException(String message) {
        super(message);
    }
}
