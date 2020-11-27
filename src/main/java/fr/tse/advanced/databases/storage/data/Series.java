package fr.tse.advanced.databases.storage.data;

import java.util.HashMap;
import java.util.Map;

public class Series<ValType extends ValueType> {

	// Parameters
	private String name;
	private Map<Long, ValType> points;
	
	// Constructor
	public Series(String name) {
		this.name = name;
		this.points = new HashMap<Long, ValType>();
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Long, ValType> getPoints() {
		return points;
	}

	public void setPoints(Map<Long, ValType> points) {
		this.points = points;
	}
	
	// Custom methods
	public void addPoint(Long key, ValType value) {
		this.points.put(key, value);
	}
	
	public ValType getByTimestamp(Long key) {
		return this.points.get(key);
	}
	
}

