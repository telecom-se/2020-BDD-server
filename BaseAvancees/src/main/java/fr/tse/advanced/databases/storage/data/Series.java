package fr.tse.advanced.databases.storage.data;

import java.util.Map;
import java.util.HashMap;

public class Series {

	// parameters
	String name;
	Map <Long, Double> points;
	
	// constructor
	public Series(String name) {
		this.name = name;
		this.points = new HashMap<Long, Double>();
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Long, Double> getPoints() {
		return points;
	}

	public void setPoints(Map<Long, Double> points) {
		this.points = points;
	}
	
	// custom methods
	public void addPoint(Long key, Double value) {
		this.points.put(key, value);
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

	public Double getByTimestamp(Long key) {
		return this.points.get(key);
	}
	
}

