package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the series interface for data storing in database
 * Uncompressed version
 *
 * @author Arnaud, Valentin
 * @since 2020-11
 */
@Data
public class SeriesUnComp<ValType extends ValueType> implements Series {
    // Parameters
    private Class<ValType> type;
    private String name;
    private Map<Long, ValType> points;

    // Constructors
    public SeriesUnComp(String name, Class<ValType> type) {
        this.name = name;
        this.type = type;
        this.points = new HashMap<Long, ValType>();
    }

    public SeriesUnComp(String name, Class<ValType> type, Map<Long, ValType> points) {
        this.name = name;
        this.type = type;
        this.points = points;
    }

    @Override
    public void clearPoints() {
        this.points.clear();
    }

    @Override
    public void addPoint(Long key, ValueType value) throws WrongSeriesValueTypeException, TimestampAlreadyExistsException {
        if (value.getClass() != type) {
            throw new WrongSeriesValueTypeException(value.getClass(), this.getType());
        }
        if (this.points.get(key) != null) {
            throw new TimestampAlreadyExistsException();
        }
        this.points.put(key, (ValType) value);
    }

    @Override
    public void deletePoint(Long key) {
        ValType value = this.points.remove(key);
        if (value == null) {
            // TODO Not implemented Yet
        }
    }

    @Override
    public ValType getByTimestamp(Long key) {
        return this.points.get(key);
    }

    @Override
    public String toString() {
        return "Series [type=" + type.getSimpleName() + ", name=" + name + ", points=" + points + "]";
    }
}
