package fr.tse.advanced.databases.storage.data;

import java.util.Map;

import fr.tse.advanced.databases.storage.exception.SeriesAlreadyExists;
import fr.tse.advanced.databases.storage.exception.SeriesNotFound;

import java.util.HashMap;
import java.util.List;

public class DataBase {

	// parameters
	Map<String, Series> series;
	
	// constructor
	public DataBase() {
		this.series = new HashMap<String, Series>();
	}
	
	public DataBase(List<Series> series) {
		this.series = new HashMap<String, Series>();
		for (int i=0 ; i<series.size() ; i++) {
			this.series.put(series.get(i).getName(), series.get(i));
		}
	}
	
	// getters and setters
	public Map<String, Series> getSeries() {
		return series;
	}

	public void setSeries(Map<String, Series> series) {
		this.series = series;
	}
	
	// methods
	public void addSeries(Series series) throws SeriesAlreadyExists {
		
		if (this.series.get(series.getName())!= null) {
			throw new SeriesAlreadyExists("S_NAME_EXISTS");
		}
		else {
			this.series.put(series.getName(), series);

		}
	}
	
	public Series getByName(String name) throws SeriesNotFound {
		if (this.series.get(name) != null) {
			return this.series.get(name);
		}
		else {
			throw new SeriesNotFound("S_NOT_FOUND");
		}
		
	}
}
