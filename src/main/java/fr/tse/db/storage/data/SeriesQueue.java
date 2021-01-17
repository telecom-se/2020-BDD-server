package fr.tse.db.storage.data;

import java.io.Serializable;
import java.util.*;

import static fr.tse.db.storage.data.BitsConverter.*;

/**
 * Intermediary container for SeriesComp that keeps a series of datapoints with delta delta compression.
 *
 * @param <ValType>
 *
 * @author remi huguenot
 */
public class SeriesQueue<ValType extends ValueType> implements Serializable {
    private final LinkedList<DataPointCompressed> series;
    private final long top;

    public SeriesQueue(long top) {
        super();
        this.top = top;
        series = new LinkedList<>();
    }

    public LinkedList<DataPointCompressed> getSeries() {
        return series;
    }

    /**
     * add value at the end of the linkedlist
     *
     * @param key
     * @param value
     */
    public void addVal(Long key, ValType value) {
        // last element of comparison
        DataPointCompressed last = series.isEmpty() ?
                new DataPointCompressed(top)
                : series.getLast();

        //change data into bitsets
        BitSet newTime = LongToBitSet(key);
        BitSet newVal = ValTypeToBitSet(value);

        //compare the values
        newTime.xor(last.getTimestamp());
        newVal.xor(last.getValue());

        series.add(new DataPointCompressed(newTime, newVal));
    }

    /**
     * return value specified by timestamp or null if the timestamp does not exists.
     * The String parameter can be "Int32", "Int64" or "Float32"
     *
     * @param timestamp
     * @param valtype
     *
     * @return
     */
    public ValType getVal(long timestamp, String valtype) {
        BitSet timestampBit = LongToBitSet(timestamp);

        BitSet timeItBit = LongToBitSet(top);
        BitSet valItBit = LongToBitSet(0L);

        Iterator<DataPointCompressed> I = series.iterator();

        DataPointCompressed last = I.next();
        timeItBit.xor(last.getTimestamp());
        valItBit.xor(last.getValue());

        while (I.hasNext() && !timeItBit.equals(timestampBit)) {
            last = I.next();
            timeItBit.xor(last.getTimestamp());
            valItBit.xor(last.getValue());
        }

        if (timeItBit.equals(timestampBit)) {
            return (ValType) BitSetToValType(valItBit, valtype);
        } else {
            return null;
        }
    }

    /**
     * remove value speciied by timestamp.
     *
     * @param timestamp
     *
     * @return
     */
    public void remove(long timestamp) {
        BitSet timestampBit = LongToBitSet(timestamp);

        BitSet timeItBit = LongToBitSet(top);
        BitSet valItBit = LongToBitSet(0L);

        Iterator<DataPointCompressed> I = series.iterator();

        while (I.hasNext() && !timeItBit.equals(timestampBit)) {
            DataPointCompressed last = I.next();
            timeItBit.xor(last.getTimestamp());
            valItBit.xor(last.getValue());
        }

        if (timeItBit.equals(timestampBit)) {
            I.remove();
        }

    }

    /**
     * return all points from the list as an uncompressed map
     * The String parameter can be "Int32", "Int64" or "Float32"
     *
     * @param valtype
     *
     * @return
     */
    public Map<Long, ValType> getAllPoints(String valtype) {
        Map<Long, ValType> AllPoints = new HashMap<>();

        BitSet timeItBit = LongToBitSet(top);
        BitSet valItBit = LongToBitSet(0L);

        for (DataPointCompressed last : series) {
            timeItBit.xor(last.getTimestamp());
            valItBit.xor(last.getValue());
            AllPoints.put(BitSetToLong(timeItBit), (ValType) BitSetToValType(valItBit, valtype));
        }
        return AllPoints;
    }
}
