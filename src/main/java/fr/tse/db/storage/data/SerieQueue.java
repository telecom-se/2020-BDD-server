package fr.tse.db.storage.data;

import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import static fr.tse.db.storage.data.BitsConverter.*;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Intermediary container for SeriesComp that keeps a series of datapoints with dleta delat compression. 
 * @author remi huguenot
 *
 * @param <ValType>
 */
public class SerieQueue<ValType extends ValueType> {

	private LinkedList<DataPointComp> serie;
	private long top;


	public SerieQueue(long top) {
		super();
		this.top = top;
		serie = new LinkedList<DataPointComp>();
	}

	/**
	 * add value at the end of the linkedlist 
	 * @param key
	 * @param value
	 */
	public void addVal(Long key, ValType value) {
		//last element of comparison
		DataPointComp last;
		try {
			last = serie.getLast();
		}catch(NoSuchElementException e) {
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
	 * @param timestamp
	 * @return
	 */
	public ValType getVal(long timestamp) {

		BitSet timestampBit = LongToBitSet(timestamp);

		BitSet timeItBit = LongToBitSet(0L);
		BitSet valItBit = LongToBitSet(0L);

		Iterator<DataPointComp> I = serie.iterator();

		while( I.hasNext() &&  timeItBit !=timestampBit ) {
			DataPointComp last = (DataPointComp) I.next();
			timeItBit.xor(last.getTimestamp());
			valItBit.xor(last.getValue());
		}

		if(timeItBit ==timestampBit) {
			ValType val = null;
			val = (ValType) BitSetToValType(valItBit, val.getClass().getSimpleName());
			return val;
		}
		else {
			return null;
		}	
	}
	
	/**
	 * remove value speciied by timestamp.
	 * @param timestamp
	 * @return
	 */
	public ValType remove(long timestamp) {
		BitSet timestampBit = LongToBitSet(timestamp);

		BitSet timeItBit = LongToBitSet(0L);
		BitSet valItBit = LongToBitSet(0L);

		Iterator<DataPointComp> I = serie.iterator();

		while( I.hasNext() &&  timeItBit !=timestampBit ) {
			DataPointComp last = (DataPointComp) I.next();
			timeItBit.xor(last.getTimestamp());
			valItBit.xor(last.getValue());
		}

		if(timeItBit ==timestampBit) {
			ValType val = null;
			val = (ValType) BitSetToValType(valItBit, val.getClass().getSimpleName());
			I.remove();
			return val;
		}
		else {
			return null;
		}	
	}

	/**
	 * return all points from the list as an uncompressed map
	 * @return
	 */
	public Map<Long, ValType> getAllPoints(){

		Map<Long, ValType> AllPoints = new HashMap<Long, ValType>();
		ValType val = null;

		BitSet timeItBit = LongToBitSet(0L);
		BitSet valItBit = LongToBitSet(0L);

		Iterator<DataPointComp> I = serie.iterator();

		while( I.hasNext() ) {
			DataPointComp last = (DataPointComp) I.next();
			timeItBit.xor(last.getTimestamp());
			valItBit.xor(last.getValue());

			AllPoints.put(BitSetToLong(timeItBit), (ValType) BitSetToValType(valItBit, val.getClass().getSimpleName()));

		}
		return  AllPoints;
	}
}
