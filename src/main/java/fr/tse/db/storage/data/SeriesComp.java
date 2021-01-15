package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the series interface for data storing in database
 * This version implements the delta-delta compression :
 * data is stored by range of timestamps in which the compression is implemented.
 * the size of ranges is defined in the variable rangeSize in the method troncTime.
 * The default range size is 1000.
 *
 * @author Rémi, Alexandre
 * @since 2020-11
 */
public class SeriesComp<ValType extends ValueType> implements Series, Serializable {

    private Class<ValType> type;
    private String name;
    private Map<Long, SerieQueue<ValType>> points;

    public SeriesComp(String name, Class<ValType> type) {
        this.name = name;
        this.type = type;
        // the size of a range of time stamps is 1000
        this.points = new HashMap<Long, SerieQueue<ValType>>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<ValType> getType() {
        return type;
    }

    public Map<Long, SerieQueue<ValType>> getPointsComp() {
        return points;
    }

    @Override
    public void clearPoints() {
        this.points.clear();
    }

    public Map<Long, ValType> getPoints() {
        Map<Long, ValType> pointsMap = new HashMap<Long, ValType>();
        for (Long key : points.keySet()) {
            Map<Long, ValType> keyPoints = points.get(key).getAllPoints(type.getSimpleName());

            //pareil
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
            this.points.put(trunc, new SerieQueue(trunc));
        }
        this.points.get(trunc).addVal(key, (ValType) value);
    }

    public void deletePoint(Long key) {
        this.points.get(truncateTime(key)).remove(key);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((points == null) ? 0 : points.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SeriesComp other = (SeriesComp) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (points == null) {
            if (other.points != null) {
                return false;
            }
        } else if (!points.equals(other.points)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    public ValType getByTimestamp(Long key) {
        return this.points.get(truncateTime(key)).getVal(key, type.getSimpleName());
    }

    @Override
    public String toString() {
        return "Series [type=" + type.getSimpleName() + ", name=" + name + ", points=" + points + "]";
    }

    /**
     * Calculate the truncature of a timestamps to put it in a range
     * change the rangeSize variable to modify the number of datapoints in a range.
     *
     * @param time
     *
     * @return
     */
    public long truncateTime(long time) {
        long rangeSize = 1000;
        return (time / rangeSize) * rangeSize;
    }
}




