package fr.tse.db.storage.data;

import java.util.HashMap;
import java.util.Map;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;

/**
* This Series class is a general container for points to store in the database
* 
* @author  Arnaud, Valentin
* @since   2020-11
*/
public class Series<ValType extends ValueType> {

	// Parameters
	private Class<ValType> type;
	private String name;
	private Map<Long, SerieQueue<ValType>> points;
	
	// Constructor
	public Series(String name, Class<ValType> type) {
		this.name = name;
		this.type = type;
		// the size of a range of time stamps is 1000
		this.points = new HashMap<Long, SerieQueue<ValType>>();
	}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Class<ValType> getType() {return type;}
	public Map<Long, SerieQueue<ValType>> getPointsComp() {return points;}
	public void clearPoints() {this.points.clear();}

	
	public Map<Long, ValType> getPoints() {
		Map<Long, ValType> pointsMap = new  HashMap<Long, ValType>();
		// go through every elements to make an uncompressed map. TO DO
		
		
		return pointsMap;}
		
	
	
	public void addPoint(Long key, ValType value) throws WrongSeriesValueTypeException, TimestampAlreadyExistsException {
		if(value.getClass() != type) {
			throw new WrongSeriesValueTypeException(value.getClass(), this.getType());
		}
		if(this.points.get(key) != null) {
			throw new TimestampAlreadyExistsException(key);
		}
		Long trunc = (key/1000)*1000;
		if (points.get(trunc)==null){
			this.points.put(trunc, new SerieQueue(trunc));
		}
		this.points.get(trunc).addVal(key, value);

	}
	
	public void deletePoint(Long key) {
		ValType value = this.points.get((key/1000)*1000).remove(key);
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
		return  this.points.get((key/1000)*1000).getVal(key);
	}

	
	
	@Override
	public String toString() {
		return "Series [type=" + type.getSimpleName() + ", name=" + name + ", points=" + points + "]";
	}
	
	
	
}




