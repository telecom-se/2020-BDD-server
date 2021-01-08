package fr.tse.db.storage.data;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import static fr.tse.db.storage.data.BitsConverter.*;
import java.util.BitSet;
import java.util.Iterator;


public class SerieQueue<ValType extends ValueType> {
	
	private LinkedList<DataPointComp> serie;
	private long top;
	
	
	public SerieQueue(long top) {
		super();
		this.top = top;
		serie = new LinkedList<DataPointComp>();
	}


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
	
	
	public ValType getVal(long timestamp) {
		
		BitSet timestampBit = LongToBitSet(timestamp);
		
		BitSet timeItBit = LongToBitSet(0L);
		BitSet valItBit = LongToBitSet(0L);
		
		Iterator I = serie.iterator();
		
		while( I.hasNext() &&  timeItBit !=timestampBit ) {
			DataPointComp last = (DataPointComp) I.next();
			timeItBit.xor(last.getTimestamp());
			valItBit.xor(last.getValue());
		}
		
		if(timeItBit ==timestampBit) {
			ValType val;
			
			val = BitSetToValType(valItBit, val.getClass().getSimpleName());
			
			return val;
			
			
		}
		else {
			return null;

		}
		
		
		
		
		
	}
	
	public ValType remove(long timestamp) {
		return null;
	}
}
