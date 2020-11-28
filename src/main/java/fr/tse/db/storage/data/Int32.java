package fr.tse.db.storage.data;

/**
* This Int32 class encapsulates an int of 32-bits
*
* @author  Valentin, Alexandre, Youssef
* @since   2020-11
*/
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
