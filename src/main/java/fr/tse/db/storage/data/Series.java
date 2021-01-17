package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;

import java.util.Map;

/**
 * This Series Interface is a general container for points to store in the database
 *
 * @param <ValType>
 */
public interface Series<ValType extends ValueType> {
    /**
     * return the name of the series
     *
     * @return String
     */
    String getName();

    /**
     * remove all points from the series
     */
    void clearPoints();

    /**
     * return the type of stored data
     *
     * @return Class<ValType>
     */
    Class<ValType> getType();

    /**
     * return a map off all stored points mapped by their timestamp
     *
     * @return
     */
    Map<Long, ValType> getPoints();

    /**
     * add specified point to the series
     *
     * @param key
     * @param value
     *
     * @throws WrongSeriesValueTypeException
     * @throws TimestampAlreadyExistsException
     */
    void addPoint(Long key, ValType value) throws WrongSeriesValueTypeException, TimestampAlreadyExistsException;

    /**
     * remove specified point from series
     *
     * @param key
     */
    void deletePoint(Long key);

    /**
     * return value of specified timestamp or null if timestamps does not exist
     *
     * @param key
     *
     * @return
     */
    ValType getByTimestamp(Long key);

    String toString();
}
