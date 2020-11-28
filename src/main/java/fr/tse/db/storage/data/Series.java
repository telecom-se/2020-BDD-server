package fr.tse.db.storage.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* This Series class is a general container for points to store in the database
* 
* @author  Arnaud, Valentin
* @since   2020-11
*/
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
	
	// hashCode() and equals()
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

	/**
	* A method that adds a point (timestamp + value) in your Series
	* 
	* @param key the timestamp of the point
	* @param value the value of the point
	*/
	public void addPoint(Long key, ValType value) {
		this.points.put(key, value);
	}
	
	/**
	* A method that gets a point from a given timestamp
	* 
	* @param key the timestamp of the point you want to retrieve
	* @return the value matching the given timestamp
	*/
	public ValType getByTimestamp(Long key) {
		return this.points.get(key);
	}
	
	/**
	* A method that gets all the points between two given timestamps.
	* Currently not implemented.
	* 
	* @param key1 the timestamp of the oldest date
	* @param key2 the timestamp of the most recent date
	* @return all the values between the given timestamps
	*/
	public List<ValType> getBetweenTimsetamps(Long key1, Long key2) {
		// TODO
		return null;
	}
}

