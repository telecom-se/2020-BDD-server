package fr.tse.db.storage.data;

import lombok.Getter;

import java.io.Serializable;
import java.util.BitSet;

import static fr.tse.db.storage.data.BitsConverter.LongToBitSet;
import static fr.tse.db.storage.data.BitsConverter.ValTypeToBitSet;

public class DataPointComp implements Serializable {
    //serie de bits
    @Getter
    private BitSet timestamp;
    //serie de bits
    @Getter
    private BitSet value;

    public DataPointComp(BitSet timestamp, BitSet value) {
        super();
        this.timestamp = timestamp;
        this.value = value;
    }

    public DataPointComp(long timestamp, ValueType value) {
        this.timestamp = LongToBitSet(timestamp);
        this.value = ValTypeToBitSet(value);
    }

    public DataPointComp(long timestamp) {
        this.timestamp = LongToBitSet(timestamp);
        this.value = LongToBitSet(0L);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = LongToBitSet(timestamp);
    }

    public void setValue(ValueType value) {
        this.value = ValTypeToBitSet(value);
    }
}
