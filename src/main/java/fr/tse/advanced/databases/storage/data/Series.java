package fr.tse.advanced.databases.storage.data;

import java.util.HashMap;
import java.util.Map;

import fr.tse.advanced.databases.storage.exception.TimestampAlreadyExistsException;
import fr.tse.advanced.databases.storage.exception.WrongSeriesValueTypeException;

public class Series<ValType extends ValueType> {

	// Parameters
	private Class<ValType> type;
	private String name;
	private Map<Long, ValType> points;
	
	// Constructor
	public Series(String name, Class<ValType> type) {
		this.name = name;
		this.type = type;
		this.points = new HashMap<Long, ValType>();
	}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Class<ValType> getType() {return type;}
	public Map<Long, ValType> getPoints() {return points;}
	public void clearPoints() {this.points.clear();}

	public void addPoint(Long key, ValType value) throws WrongSeriesValueTypeException, TimestampAlreadyExistsException {
		if(value.getClass() != type) {
			throw new WrongSeriesValueTypeException(value.getClass(), this.getType());
		}
		if(this.points.get(key) != null) {
			throw new TimestampAlreadyExistsException(key);
		}
		this.points.put(key, value);
	}
	
	public void deletePoint(Long key) {
		ValType value = this.points.remove(key);
		if(value == null) {
			// Not implemented Yet
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public ValType getByTimestamp(Long key) {
		return this.points.get(key);
	}

	@Override
	public String toString() {
		return "Series [type=" + type.getSimpleName() + ", name=" + name + ", points=" + points + "]";
	}
	
	
	
}

