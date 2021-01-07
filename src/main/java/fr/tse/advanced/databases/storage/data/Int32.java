package fr.tse.advanced.databases.storage.data;

import java.lang.Comparable;

import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public class Int32 implements ValueType, Comparable<Int32>{

	private Integer val;

	public Int32(Integer val) {
		this.val = val;
	}

	public Integer getVal() {
		return val;
	}

	public int compareTo(Int32 o) {
		if(this.val == o.val) {
			return 0;
		} else if(this.val < o.val) {
			return -1;
		} else {
			return 1;
		}
	}

	@Override
	public String toString() {
		return "Int32[" + val + "]";
	}

	public void sum(ValueType i) throws WrongValueTypeException{
		if (i instanceof Int32) {
			this.val+= ((Int32) i).getVal();
		} else {
			throw new WrongValueTypeException(this.getClass(),i.getClass());
		}
	}

	public float divide(int denom) {
		return (float) this.val/denom;
	}
	
	
}
