package fr.tse.db.storage.data;

import java.util.HashMap;
import java.util.Map;

import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;
import lombok.Data;

/**
* This Series class is a general container for points to store in the database
* 
* @author  Arnaud, Valentin
* @since   2020-11
*/

@Data
public class Series<ValType extends ValueType> {

	// Parameters
	private Class<ValType> type;
	private String name;
	private Map<Long, ValType> points;
	
	// Constructors
	public Series(String name, Class<ValType> type) {
		this.name = name;
		this.type = type;
		this.points = new HashMap<Long, ValType>();
	}
	
	public Series(String name, Class<ValType> type,Map<Long, ValType> points ) {
		this.name = name;
		this.type = type;
		this.points = points;
	}

	public void clearPoints() {this.points.clear();}

	public void addPoint(Long key, ValType value) throws WrongSeriesValueTypeException, TimestampAlreadyExistsException {
		if(value.getClass() != type) {
			throw new WrongSeriesValueTypeException(value.getClass(), this.getType());
		}
		if(this.points.get(key) != null) {
			throw new TimestampAlreadyExistsException();
		}
		this.points.put(key, value);
	}
	
	public void deletePoint(Long key) {
		ValType value = this.points.remove(key);
		if(value == null) {
			// Not implemented Yet
		}
	}
	
	
	public ValType getByTimestamp(Long key) {
		return this.points.get(key);
	}

	@Override
	public String toString() {
		return "Series [type=" + type.getSimpleName() + ", name=" + name + ", points=" + points + "]";
	}
	
	
	
}




