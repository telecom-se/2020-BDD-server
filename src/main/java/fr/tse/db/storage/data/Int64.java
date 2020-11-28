package fr.tse.db.storage.data;

/**
* This Int64 class encapsulates an int of 64-bits
*
* @author  Valentin, Alexandre, Youssef
* @since   2020-11
*/
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
