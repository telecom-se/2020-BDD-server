package fr.tse.db.storage.data;

import lombok.Getter;

import java.io.Serializable;
import java.util.*;

import static fr.tse.db.storage.data.BitsConverter.*;

/**
 * Intermediary container for {@link SeriesCompressed} that maintains a series of datapoints with delta-delta compression.
 */
public class SeriesQueue<ValType extends ValueType> implements Serializable {

    @Getter
    private final ArrayList<DataPointCompressed> series = new ArrayList<>();

    private final long top;

    public SeriesQueue(long top) {
        super();
        this.top = top;
    }

    /**
     * Add a value with specified timestamp at the end of the series
     */
    public void addVal(Long timestamp, ValType value) {
        // last element of comparison
        DataPointCompressed last = series.isEmpty() ?
                new DataPointCompressed(top)
                : series.get(series.size() - 1);

        //change data into bitsets
        BitSet newTime = LongToBitSet(timestamp);
        BitSet newVal = ValTypeToBitSet(value);

        //compare the values
        newTime.xor(last.getTimestamp());
        newVal.xor(last.getValue());

        series.add(new DataPointCompressed(newTime, newVal));
    }

    /**
     * Return the value associated with the given timestamp, or null if the timestamp does not exist.
     *
     * @param timestamp goal timestamp
     * @param valtype   "Int32", "Int64" or "Float32"
     *
     * @return The value associated with the given timestamp
     */
    public ValType getVal(long timestamp, String valtype) {
        BitSet goalTimestamp = LongToBitSet(timestamp);
        BitSet lastTimestamp = LongToBitSet(top);
        BitSet lastValue = LongToBitSet(0L);

        for (DataPointCompressed point : series) {
            lastTimestamp.xor(point.getTimestamp());
            if (lastTimestamp.equals(goalTimestamp)) {
                lastValue.xor(point.getValue());
                return (ValType) BitSetToValType(lastValue, valtype);
            }
            lastTimestamp = (BitSet) point.getTimestamp().clone();
            lastValue = (BitSet) point.getValue().clone();
        }
        return null;
    }

    /**
     * Remove the value associated with the given timestamp.
     *
     * @param timestamp goal timestamp
     */
    public void remove(long timestamp) {
        BitSet goalTimestamp = LongToBitSet(timestamp);
        BitSet lastTimestamp = LongToBitSet(top);

        Iterator<DataPointCompressed> I = series.iterator();
        while (I.hasNext()) {
            DataPointCompressed point = I.next();
            lastTimestamp.xor(point.getTimestamp());
            if (lastTimestamp.equals(goalTimestamp)) {
                I.remove();
                return;
            }
            lastTimestamp = (BitSet) point.getTimestamp().clone();
        }
    }

    /**
     * Return all points from the series as an uncompressed map
     *
     * @param valtype "Int32", "Int64" or "Float32"
     *
     * @return all points from the series
     */
    public Map<Long, ValType> getAllPoints(String valtype) {
        Map<Long, ValType> allPoints = new HashMap<>();

        BitSet lastTimestamp = LongToBitSet(top);
        BitSet lastValue = LongToBitSet(0L);

        for (DataPointCompressed point : series) {
            lastTimestamp.xor(point.getTimestamp());
            lastValue.xor(point.getValue());
            allPoints.put(BitSetToLong(lastTimestamp), (ValType) BitSetToValType(lastValue, valtype));
            lastTimestamp = (BitSet) point.getTimestamp().clone();
            lastValue = (BitSet) point.getValue().clone();
        }
        return allPoints;
    }
}
