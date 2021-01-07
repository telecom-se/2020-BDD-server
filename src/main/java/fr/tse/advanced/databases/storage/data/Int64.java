package fr.tse.advanced.databases.storage.data;

import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public class Int64 implements ValueType {

	private Long val;
	
	public Int64(Long val) {
		this.val = val;
	}

	public Long getVal() {
		return val;
	}

	public void setVal(Long val) {
		this.val = val;
	}

	public int compareTo(ValueType o) {
		if (o instanceof Int64) {
			if(this.val == ((Int64)o).val) {
				return 0;
			} else if(this.val < ((Int64)o).val) {
				return -1;
			} else {
				return 1;
			}
		} else throw new WrongValueTypeException(this.getClass(),o.getClass());
	}

	@Override
	public String toString() {
		return "Int64[" + val + "]";
	}
	
	public void sum(ValueType i) throws WrongValueTypeException{
		if (i instanceof Int64) {
			this.val+= ((Int64) i).getVal();
		} else {
			throw new WrongValueTypeException(this.getClass(),i.getClass());
		}
	}

	public float divide(int denom) {
		return (float) this.val/denom;
	}
	
}
