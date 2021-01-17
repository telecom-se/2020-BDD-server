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
     * Add value at the end of the series
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
     * return value specified by timestamp or null if the timestamp does not exists.
     * The String parameter can be "Int32", "Int64" or "Float32"
     *
     * @param timestamp
     * @param valtype
     *
     * @return
     */
    public ValType getVal(long timestamp, String valtype) {

        //System.out.println(timestamp);
        BitSet timestampBit = LongToBitSet(timestamp);

        BitSet timeItBit = LongToBitSet(top);
        BitSet valItBit = LongToBitSet(0L);
        ValType result = null;
        //ListIterator<DataPointCompressed> I =  series.listIterator();
        DataPointCompressed last;
        int it = 0;
        boolean condition = false;
        while (it < series.size() && !condition) {
            last = series.get(it);
            // System.out.println(last.getTimestamp());
            timeItBit.xor(last.getTimestamp());
            valItBit.xor(last.getValue());
            if (timeItBit.equals(timestampBit)) {
                condition = true;
                result = (ValType) BitSetToValType(valItBit, valtype);
            } else {
                timeItBit = last.getTimestamp();
                valItBit = last.getValue();
                it++;
            }
        }

        return result;
    }

    /**
     * remove value specified by timestamp.
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
            if (timeItBit.equals(timestampBit)) {
                I.remove();
            } else {
                timeItBit = last.getTimestamp();
                valItBit = last.getValue();
            }
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
            timeItBit = last.getTimestamp();
            valItBit = last.getValue();
        }
        return AllPoints;
    }
}
