package fr.tse.advanced.databases.storage.request;

import java.util.Collection;
import java.util.List;

import fr.tse.advanced.databases.storage.data.DataPoint;
import fr.tse.advanced.databases.storage.data.ValueType;
import fr.tse.advanced.databases.storage.exception.SeriesAlreadyExistsException;
import fr.tse.advanced.databases.storage.exception.SeriesNotFoundException;
import fr.tse.advanced.databases.storage.exception.TimestampAlreadyExistsException;
import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

/**
 * This interface will be used by query and list all CRUD requests possible
 *
 */
public interface Requests {
	
	/**
	 * Select value in series at given timestamp
	 * @param seriesName series name
	 * @param timestamp 
	 * @return a value 
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public ValueType selectByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Select all values with timestamp lower than a value
	 * @param seriesName series name
	 * @param timestamp requested timestamp
	 * @return collection of values
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public Collection<ValueType> selectLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Select all values with timestamp lower or equal than a value
	 * @param seriesName series name
	 * @param timestamp requested timestamp
	 * @return collection of values
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public Collection<ValueType> selectLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Select all values with timestamp higher than a value
	 * @param seriesName series name
	 * @param timestamp requested timestamp
	 * @return collection of values
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public Collection<ValueType> selectHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Select all values with timestamp higher or equal than a value
	 * @param seriesName series name
	 * @param timestamp requested timestamp
	 * @return collection of values
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public Collection<ValueType> selectHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	

	/**
	 * Select all values with timestamps between given values (both included)
	 * @param seriesName series name
	 * @param timestamp1 requested lower timestamp
	 * @param timestamp2 requested higher timestamp
	 * @return collection of values
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public Collection<ValueType> selectBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFoundException;
	
	/**
	 * Insert values in a series at given timestamps
	 * @param seriesName series name
	 * @param points list of timestamp/value points
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 * @see 2 Errors missing (Timestamp already exists & WrongValueType)
	 */
	public <ValType extends ValueType> void insertValue(String seriesName, Collection<DataPoint<ValType>> points) throws SeriesNotFoundException, WrongValueTypeException, TimestampAlreadyExistsException;
	
	/**
	 * Create a series with specified name and specified type
	 * @param seriesName series name
	 * @param type class of series values
	 * @throws SeriesAlreadyExistsException When series already exists
	 * @see Check second parameter
	 */
	public <ValType extends ValueType> void createSeries(String seriesName, Class<ValType> type) throws SeriesAlreadyExistsException;
	
	/**
	 * Delete series with specified name
	 * @param seriesName series name
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public void deleteSeries(String seriesName) throws SeriesNotFoundException;
	
	/**
	 * Delete value in series with specified timestamp
	 * @param seriesName series name
	 * @param timestamp required timestamp
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public void deleteByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Delete value in series with specified timestamp lower than given
	 * @param seriesName series name
	 * @param timestamp required timestamp
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public void deleteLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Delete value in series with specified timestamp lower or equal than given
	 * @param seriesName series name
	 * @param timestamp required timestamp
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public void deleteLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Delete value in series with specified timestamp higher than given
	 * @param seriesName series name
	 * @param timestamp required timestamp
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public void deleteHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	/**
	 * Delete value in series with specified timestamp higher or equal than given
	 * @param seriesName series name
	 * @param timestamp required timestamp
	 * @throws SeriesNotFoundException When series with specified name does not exist
	 */
	public void deleteHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;
	
	
	
}
