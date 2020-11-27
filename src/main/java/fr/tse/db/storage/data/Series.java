package fr.tse.db.storage.data;

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

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Series other = (Series) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
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

