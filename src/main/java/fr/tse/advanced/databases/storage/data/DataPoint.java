package fr.tse.advanced.databases.storage.data;

public class DataPoint {

	private Long timestamp;
	private ValueType value;
	
	
	public DataPoint(Long timestamp, ValueType value) {
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public ValueType getValue() {
		return value;
	}
	public void setValue(ValueType value) {
		this.value = value;
	}
	
	
	
	
}
