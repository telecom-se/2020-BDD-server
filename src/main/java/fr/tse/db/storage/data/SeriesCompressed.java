package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the series interface for data storing in database
 * This version implements the delta-delta compression :
 * data is stored by range of timestamps in which the compression is implemented.
 * the size of ranges is defined in the variable rangeSize in the method {@link #truncateTime(long)}.
 * The default range size is 1000.
 *
 * @author Rémi, Alexandre
 * @since 2020-11
 */
@EqualsAndHashCode
public class SeriesCompressed<ValType extends ValueType> implements Series, Serializable {
    @Getter
    private final Class<ValType> type;
    private final Map<Long, SeriesQueue<ValType>> points;
    @Getter @Setter
    private String name;

    public SeriesCompressed(String name, Class<ValType> type) {
        this.name = name;
        this.type = type;
        this.points = new HashMap<>();
    }

    public Map<Long, SeriesQueue<ValType>> getCompressedPoints() {
        return points;
    }

    @Override
    public void clearPoints() {
        this.points.clear();
    }

    public Map<Long, ValType> getPoints() {
        Map<Long, ValType> pointsMap = new HashMap<>();
        for (Long key : points.keySet()) {
            Map<Long, ValType> keyPoints = points.get(key).getAllPoints(type.getSimpleName());
            for (Long key2 : keyPoints.keySet()) {
                pointsMap.put(key2, keyPoints.get(key2));
            }
        }
        return pointsMap;
    }

    @Override
    public void addPoint(Long key, ValueType value) throws WrongSeriesValueTypeException, TimestampAlreadyExistsException {
        if (value.getClass() != type) {
            throw new WrongSeriesValueTypeException(value.getClass(), this.getType());
        }
        if (this.points.get(truncateTime(key)) != null) {
            if (this.points.get(truncateTime(key)).getVal(key, type.getName()) != null) {
                throw new TimestampAlreadyExistsException();
            }
        }

        Long trunc = truncateTime(key);
        if (points.get(trunc) == null) {
            this.points.put(trunc, new SeriesQueue(trunc));
        }
        this.points.get(trunc).addVal(key, (ValType) value);
    }

    public void deletePoint(Long key) {
        this.points.get(truncateTime(key)).remove(key);
    }

    public ValType getByTimestamp(Long key) {
        return this.points.get(truncateTime(key)).getVal(key, type.getSimpleName());
    }

    /**
     * Calculate the truncature of a timestamps to put it in a range
     * change the rangeSize variable to modify the number of datapoints in a range.
     *
     * @param time
     *
     * @return
     */
    private long truncateTime(long time) {
        long rangeSize = 1000;
        return (time / rangeSize) * rangeSize;
    }
}