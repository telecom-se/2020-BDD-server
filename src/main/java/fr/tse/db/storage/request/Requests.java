package fr.tse.db.storage.request;

import java.util.Collection;

import fr.tse.db.storage.data.DataPoint;
import fr.tse.db.storage.data.ValueType;
import fr.tse.db.storage.exception.EmptyCollectionException;
import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;
import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;

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
	public <ValType extends ValueType> void insertValue(String seriesName, Collection<DataPoint<ValType>> points) throws SeriesNotFoundException, WrongSeriesValueTypeException, TimestampAlreadyExistsException;
	
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
	/**
	 * Returns average of the values in entry
	 * @param seriesValues
	 * @return average as float
	 * @throws EmptyCollectionException if collection is empty
	 */
	public float average(Collection<ValueType> seriesValues) throws EmptyCollectionException;
	
	/**
	 * Returns min value of the entry
	 * @param seriesValues
	 * @return minimum of collection
	 * @throws EmptyCollectionException if collection is empty
	 */
	public ValueType min(Collection<ValueType> seriesValues) throws EmptyCollectionException;
	
	/**
	 * Returns max value of the entry
	 * @param seriesValues
	 * @return maximum of collection
	 * @throws EmptyCollectionException if collection is empty
	 */
	public ValueType max(Collection<ValueType> seriesValues) throws EmptyCollectionException;
	
	/**
	 * Returns number of entries
	 * @param seriesValues
	 * @return count as int
	 */
	public int count(Collection<ValueType> seriesValues);
	
	/**
	 * Returns the sum of the entries
	 * @param seriesValues
	 * @return sum of collection
	 * @throws EmptyCollectionException if collection is empty
	 */
	public ValueType sum(Collection<ValueType> seriesValues) throws EmptyCollectionException;
}
