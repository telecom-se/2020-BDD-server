package fr.tse.advanced.databases.storage.data;

import java.lang.Comparable;

import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public class Float32 implements ValueType {

	private Float val;

	public Float32(Float val) {
		this.val = val;
	}

	public Float getVal() {
		return val;
	}

	public void setVal(Float val) {
		this.val = val;
	}
	
	public int compareTo(ValueType o) {
		if (o instanceof Float32) {
			if(this.val == ((Float32)o).val) {
				return 0;
			} else if(this.val < ((Float32)o).val) {
				return -1;
			} else {
				return 1;
			}
		} else throw new WrongValueTypeException(this.getClass(),o.getClass());
	}

	@Override
	public String toString() {
		return "Float32[" + val + "]";
	}
	
	public void sum(ValueType i) throws WrongValueTypeException{
		if (i instanceof Float32) {
			this.val+= ((Float32) i).getVal();
		} else {
			throw new WrongValueTypeException(this.getClass(),i.getClass());
		}
	}

	public float divide(int denom) {
		return (float) this.val/denom;
	}
	
	
}
