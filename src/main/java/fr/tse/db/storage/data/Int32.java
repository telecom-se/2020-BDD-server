package fr.tse.db.storage.data;

public class Int32 implements ValueType{

	private Integer val;

	public Int32(Integer val) {
		this.val = val;
	}

	public Integer getVal() {
		return val;
	}

	public void setVal(Integer val) {
		this.val = val;
	}
	
	
	
}
