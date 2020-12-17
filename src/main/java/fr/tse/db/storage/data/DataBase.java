package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.SeriesAlreadyExists;
import fr.tse.db.storage.exception.SeriesNotFound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* This DataBase class implements the main structure that stores all
* the data using {@link Series} (in memory only for now)
* 
* @author  Arnaud, Valentin
* @since   2020-11
*/
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
	
	/**
	* A method that adds a new {@link Series} in the database
	* 
	* @param series is the Series you want to add
	* @exception SeriesAlreadyExists if the series is already in your database
	*/
	public void addSeries(Series series) throws SeriesAlreadyExists {
		
		if (this.series.get(series.getName())!= null) {
			throw new SeriesAlreadyExists("S_NAME_EXISTS");
		}
		else {
			this.series.put(series.getName(), series);
		}
	}
	
	/**
	* A method that gets a {@link Series} from your database
	* 
	* @param name the name of the series you want to retrieve
	* @return the corresponding Series in the database
	* @exception SeriesNotFound if the series is not in your database
	*/
	public Series getByName(String name) throws SeriesNotFound {
		
		if (this.series.get(name) != null) {
			return this.series.get(name);
		}
		else {
			throw new SeriesNotFound("S_NOT_FOUND");
		}
	}
}
