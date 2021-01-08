package fr.tse.db.storage.data;

import java.util.BitSet;

public class BitsConverter {

	public static BitSet LongToBitSet(long value) {
	    BitSet bits = new BitSet();
	    int index = 0;
	    while (value != 0L) {
	      if (value % 2L != 0) {
	        bits.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return bits;
	  }

	  public static long BitSetToLong(BitSet bits) {
	    long value = 0L;
	    for (int i = 0; i < bits.length(); ++i) {
	      value += bits.get(i) ? (1L << i) : 0L;
	    }
	    return value;
	  }
	
	  public static BitSet ValTypeToBitSet(ValueType value) {
		  String cl = value.getVal().getClass().getSimpleName();  
		  BitSet bits = new BitSet();
		  int index;
		  switch(cl) {
		  case "Float32": 
			  //todo
			  break;
		  
		  case "Int32": //TOcheck
			  int valInt = ((Int32) value).getVal();
			  index = 0;
			  while (valInt != 0) {
			      if (valInt % 2 != 0) {
			        bits.set(index);
			      }
			      ++index;
			      valInt = valInt >>> 1;
			    }
			  break;
		  
		  case "Int64" : 
			  long valLong = ((Int64) value).getVal();
			  index = 0;
			  while (valLong != 0L) {
			      if (valLong % 2L != 0) {
			        bits.set(index);
			      }
			      ++index;
			      valLong = valLong >>> 1;
			    }
			    break;
		  default : break;
		  }
		    return bits;

		  
		  }

		  public static Int64 BitSetToInt64(BitSet bits) {
		    long value = 0L;
		    for (int i = 0; i < bits.length(); ++i) {
		      value += bits.get(i) ? (1L << i) : 0L;
		    }
		    return new Int64(value);
		  }
	  
		  public ValueType BitSetToValType(BitSet val, String className) {
			  switch (className) {
			  
			  }
			  return null;
		  }
		  
}
