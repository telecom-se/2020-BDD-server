package fr.tse.db.storage.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;


public class DataBase {

	private static DataBase instance = new DataBase();
	public static DataBase getInstance() {return instance;}
	
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

	public void addSeries(Series series) throws SeriesAlreadyExistsException {
		
		if (this.series.get(series.getName())!= null) {
			throw new SeriesAlreadyExistsException(series.getName());
		}
		else {
			this.series.put(series.getName(), series);

		}
	}
	
	public void deleteSeries(String seriesName) throws SeriesNotFoundException {
		Series series = this.series.remove(seriesName);
		if(series == null) {
			throw new SeriesNotFoundException(seriesName);
		}
	}
	
	public Series getByName(String name) throws SeriesNotFoundException {
		if (this.series.get(name) != null) {
			return this.series.get(name);
		}
		else {
			throw new SeriesNotFoundException(name);
		}
		
	}
	
	
	
}
