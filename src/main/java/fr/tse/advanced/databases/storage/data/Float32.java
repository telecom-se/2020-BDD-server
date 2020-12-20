package fr.tse.advanced.databases.storage.data;

import java.lang.Comparable;

public class Float32 implements ValueType, Comparable<Float32>{

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
	
	public int compareTo(Float32 o) {
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
		return "Float32[" + val + "]";
	}
	
	
}
