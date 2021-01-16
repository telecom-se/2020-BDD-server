package fr.tse.db.storage.request;


import fr.tse.db.storage.data.Series;
import fr.tse.db.storage.data.ValueType;
import fr.tse.db.storage.exception.*;

import java.util.Map;

/**
 * This interface will be used by query and list all CRUD requests possible
 */
public interface Requests {
    /**
     * Shows all Series name and types in database
     *
     * @return
     */
    Map<String, String> showAllSeries();

    /**
     * Select all values from series
     *
     * @param seriesName series name
     *
     * @return new Series
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectSeries(String seriesName) throws SeriesNotFoundException;

    /**
     * Select value in series at given timestamp
     *
     * @param seriesName series name
     * @param timestamp
     *
     * @return new Series with 1 element
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Select all values with timestamp lower than a value
     *
     * @param seriesName series name
     * @param timestamp  requested timestamp
     *
     * @return new Series
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Select all values with timestamp lower or equal than a value
     *
     * @param seriesName series name
     * @param timestamp  requested timestamp
     *
     * @return new Series
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Select all values with timestamp higher than a value
     *
     * @param seriesName series name
     * @param timestamp  requested timestamp
     *
     * @return new Series
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Select all values with timestamp higher or equal than a value
     *
     * @param seriesName series name
     * @param timestamp  requested timestamp
     *
     * @return new Series
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Select all values with timestamps between given values (both included)
     *
     * @param seriesName series name
     * @param timestamp1 requested lower timestamp
     * @param timestamp2 requested higher timestamp
     *
     * @return new Series
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    Series selectBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFoundException;

    /**
     * Select all values not in between given timestamps
     *
     * @param seriesName series name
     * @param timestamp1 requested lower timestamp
     * @param timestamp2 requested lower timestamp
     *
     * @return new Series
     * @throws SeriesNotFoundException
     */
    Series selectNotInBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFoundException;

    /**
     * Insert values in a series at given timestamps
     *
     * @param seriesName     series name
     * @param insertedPoints series which values are to be inserted into the series defined by seriesName
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void insertValue(String seriesName, Series insertedPoints) throws SeriesNotFoundException, WrongSeriesValueTypeException, TimestampAlreadyExistsException;

    /**
     * Create a series with specified name and specified type
     *
     * @param seriesName series name
     * @param type       class of series values
     *
     * @throws SeriesAlreadyExistsException When series already exists
     */
    <ValType extends ValueType> void createSeries(String seriesName, Class<ValType> type) throws SeriesAlreadyExistsException;

    /**
     * Delete series with specified name
     *
     * @param seriesName series name
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void deleteSeries(String seriesName) throws SeriesNotFoundException;

    /**
     * Delete value in series with specified timestamp
     *
     * @param seriesName series name
     * @param timestamp  required timestamp
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void deleteByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;


    void deleteAllPoints(String seriesName) throws SeriesNotFoundException;

    /**
     * Delete value in series with specified timestamp lower than given
     *
     * @param seriesName series name
     * @param timestamp  required timestamp
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void deleteLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Delete value in series with specified timestamp lower or equal than given
     *
     * @param seriesName series name
     * @param timestamp  required timestamp
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void deleteLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Delete value in series with specified timestamp higher than given
     *
     * @param seriesName series name
     * @param timestamp  required timestamp
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void deleteHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Delete value in series with specified timestamp higher or equal than given
     *
     * @param seriesName series name
     * @param timestamp  required timestamp
     *
     * @throws SeriesNotFoundException When series with specified name does not exist
     */
    void deleteHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException;

    /**
     * Delete all values between boundaries
     *
     * @param seriesName
     * @param timestamp1
     * @param timestamp2
     *
     * @throws SeriesNotFoundException
     */
    void deleteBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFoundException;

    /**
     * Delete all values not in between two boundaries
     *
     * @param seriesName
     * @param timestamp1
     * @param timestamp2
     *
     * @throws SeriesNotFoundException
     */
    void deleteNotInBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFoundException;

    /**
     * Returns average of the values in entry
     *
     * @param series
     *
     * @return average as float
     * @throws EmptySeriesException if collection is empty
     */
    float average(Series series) throws EmptySeriesException;

    /**
     * Returns min value of the entry
     *
     * @param series
     *
     * @return minimum of collection
     * @throws EmptySeriesException if collection is empty
     */
    ValueType min(Series series) throws EmptySeriesException;

    /**
     * Returns max value of the entry
     *
     * @param series
     *
     * @return maximum of collection
     * @throws EmptySeriesException if collection is empty
     */
    ValueType max(Series series) throws EmptySeriesException;

    /**
     * Returns number of entries
     *
     * @param series
     *
     * @return count as int
     */
    int count(Series series);

    /**
     * Returns the sum of the entries
     *
     * @param series
     *
     * @return sum of collection
     * @throws EmptySeriesException if collection is empty
     */
    ValueType sum(Series series) throws EmptySeriesException;
}
