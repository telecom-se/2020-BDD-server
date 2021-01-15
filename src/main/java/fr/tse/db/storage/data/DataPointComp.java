package fr.tse.db.storage.data;

import java.util.BitSet;
import static fr.tse.db.storage.data.BitsConverter.*;

public class DataPointComp {
	//serie de bits
	private BitSet timestamp;
	//serie de bits
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
	
	public BitSet getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		
		this.timestamp =  LongToBitSet(timestamp);
;
	}
	public BitSet getValue() {
		return value;
	}
	public void setValue(ValueType value) {
		this.value = ValTypeToBitSet(value);
	}

	
	
}
