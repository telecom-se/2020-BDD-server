package fr.tse.advanced.databases.storage.data;

public class Int64 implements ValueType, Comparable<Int64>{

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

	public int compareTo(Int64 o) {
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
		return "Int64[" + val + "]";
	}
	
}
