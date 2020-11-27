package fr.tse.advanced.databases.storage.data;

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
	
	
	
}
