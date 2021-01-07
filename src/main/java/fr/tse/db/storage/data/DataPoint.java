package fr.tse.db.storage.data;

public class DataPoint<ValType extends ValueType> {

	private Long timestamp;
	private ValType value;
	
	
	public DataPoint(Long timestamp, ValType value) {
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public ValType getValue() {
		return value;
	}
	public void setValue(ValType value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DataPoint [timestamp=" + timestamp + ", value=" + value + "]";
	}
	
	
	
	
}
