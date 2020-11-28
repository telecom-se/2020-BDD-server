package fr.tse.db.storage.data;

/**
* This Float32 class encapsulates a float of 32-bits
*
* @writer  Arnaud
* @author  Valentin, Alexandre, Youssef
* @since   2020-11
*/
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
