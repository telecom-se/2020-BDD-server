package fr.tse.advanced.databases.storage.data;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class DataBase {

	// parameters
	private Map<String, Series> series;
	
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
	public void addSeries(Series series) {
		this.series.put(series.getName(), series);
	}
	
	public Series getByName(String name) {
		return this.series.get(name);
	}
}
