package fr.tse.advanced.databases.storage.data;

import java.lang.Comparable;

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
	
	
}
