package fr.tse.advanced.databases.storage.request;

import java.util.Collection;
import java.util.List;

import fr.tse.advanced.databases.storage.data.DataBase;
import fr.tse.advanced.databases.storage.data.DataPoint;
import fr.tse.advanced.databases.storage.data.Series;
import fr.tse.advanced.databases.storage.data.ValueType;
import fr.tse.advanced.databases.storage.exception.SeriesAlreadyExists;
import fr.tse.advanced.databases.storage.exception.SeriesNotFound;

public class RequestsImpl implements Requests {

	public ValueType selectByTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		return series.getByTimestamp(timestamp);
	}

	public Collection<ValueType> selectLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		//series.getPoints()
		return null;
	}

	public Collection<ValueType> selectLowerOrEqualThanTimestamp(String seriesName, Long timestamp)
			throws SeriesNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ValueType> selectHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ValueType> selectHigherOrEqualThanTimestamp(String seriesName, Long timestamp)
			throws SeriesNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ValueType> selectBetweenTimestampBothIncluded(String seriesName, Long timestamp1,
			Long timestamp2) throws SeriesNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertValue(String seriesName, List<DataPoint> points) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		//series.addPoint(points.get, value);
		
	}

	public void createSeries(String seriesName, Class<? extends ValueType> type) throws SeriesAlreadyExists {
		// TODO Auto-generated method stub
		
	}

	public void deleteSeries(String seriesName) throws SeriesNotFound {
		// TODO Auto-generated method stub
		
	}

	public void deleteByTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		// TODO Auto-generated method stub
		
	}

	public void deleteLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		// TODO Auto-generated method stub
		
	}

	public void deleteLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		// TODO Auto-generated method stub
		
	}

	public void deleteHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		// TODO Auto-generated method stub
		
	}

	public void deleteHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		// TODO Auto-generated method stub
		
	}


	
}
