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
public class SerieQueue<ValType extends ValueType> implements Serializable {
    private LinkedList<DataPointComp> serie;
    private long top;

    public SerieQueue(long top) {
        super();
        this.top = top;
        serie = new LinkedList<DataPointComp>();
    }

    public LinkedList<DataPointComp> getSerie() {
        return serie;
    }

    /**
     * add value at the end of the linkedlist
     *
     * @param key
     * @param value
     */
    public void addVal(Long key, ValType value) {
        //last element of comparison
        DataPointComp last;
        try {
            last = serie.getLast();
        } catch (NoSuchElementException e) {
            // if the list is empty
            last = new DataPointComp(top);
        }

        //change data into bitsets
        BitSet NuTime = LongToBitSet(key);
        BitSet NuVal = ValTypeToBitSet(value);

        //compare the values
        NuTime.xor(last.getTimestamp());
        NuVal.xor(last.getValue());

        serie.add(new DataPointComp(NuTime, NuVal));
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

        Iterator<DataPointComp> I = serie.iterator();

        DataPointComp last = (DataPointComp) I.next();
        timeItBit.xor(last.getTimestamp());
        valItBit.xor(last.getValue());

        while (I.hasNext() && !timeItBit.equals(timestampBit)) {
            last = (DataPointComp) I.next();
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

        Iterator<DataPointComp> I = serie.iterator();

        while (I.hasNext() && !timeItBit.equals(timestampBit)) {
            DataPointComp last = (DataPointComp) I.next();
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

        Map<Long, ValType> AllPoints = new HashMap<Long, ValType>();

        BitSet timeItBit = LongToBitSet(top);
        BitSet valItBit = LongToBitSet(0L);

        Iterator<DataPointComp> I = serie.iterator();

        while (I.hasNext()) {
            DataPointComp last = (DataPointComp) I.next();
            timeItBit.xor(last.getTimestamp());
            valItBit.xor(last.getValue());

            AllPoints.put(BitSetToLong(timeItBit), (ValType) BitSetToValType(valItBit, valtype));

        }
        return AllPoints;
    }
}
