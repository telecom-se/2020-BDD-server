package fr.tse.advanced.databases.storage.data;

import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public class Int32 implements ValueType<Integer> {

	private Integer val;

	public Int32(Integer val) {
		this.val = val;
	}

	public Integer getVal() {
		return val;
	}
	
	public void setVal(Integer val) {
		this.val=val;
	}
	
	public int compareTo(ValueType o) {
		if (o instanceof Int32) {
			if(this.val == ((Int32)o).val) {
				return 0;
			} else if(this.val < ((Int32)o).val) {
				return -1;
			} else {
				return 1;
			}
		} else throw new WrongValueTypeException(this.getClass(),o.getClass());
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
