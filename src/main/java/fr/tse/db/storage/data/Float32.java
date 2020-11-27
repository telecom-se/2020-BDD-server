package fr.tse.db.storage.data;

public class Float32 implements ValueType{

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
	
	
	
}
