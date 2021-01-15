package fr.tse.db.storage.data;

import java.util.Map;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;

/**
 * This Series Interface is a general container for points to store in the database
 * @author remi huguenot
 *
 * @param <ValType>
 */
public interface Series<ValType extends ValueType> {

	/**
	 * return the name of the series
	 * @return String
	 */
	public String getName();
	/**
	 * remove all points from the series
	 */
	public  void clearPoints();
	/**
	 * return the type of stored data
	 * @return Class<ValType>
	 */
	public Class<ValType> getType();
	/**
	 * return a map off all stored points mapped by their timestamp
	 * @return
	 */
	public Map<Long, ValType> getPoints();
	/**
	 * add specified point to the series
	 * @param key
	 * @param value
	 * @throws WrongSeriesValueTypeException
	 * @throws TimestampAlreadyExistsException
	 */
	public void addPoint(Long key, ValType value)throws WrongSeriesValueTypeException, TimestampAlreadyExistsException;
	/**
	 * remove specified point from series
	 * @param key
	 */
	public void deletePoint(Long key);
	/**
	 * return value of specified timestamp or null if timestamps does not exist
	 * @param key
	 * @return
	 */
	public ValType getByTimestamp(Long key);
	
	public String toString();
}
