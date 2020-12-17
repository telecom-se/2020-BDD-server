package fr.tse.advanced.databases.storage.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

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
		
		Collection<ValueType> values = new ArrayList<ValueType>();
		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() < timestamp) {
				values.add(entry.getValue());
			}
		}
		
		return values;
	}

	public Collection<ValueType> selectLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		
		Collection<ValueType> values = new ArrayList<ValueType>();
		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() <= timestamp) {
				values.add(entry.getValue());
			}
		}
		
		return values;
	}

	public Collection<ValueType> selectHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		
		Collection<ValueType> values = new ArrayList<ValueType>();
		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() > timestamp) {
				values.add(entry.getValue());
			}
		}
		
		return values;
	}

	public Collection<ValueType> selectHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		
		Collection<ValueType> values = new ArrayList<ValueType>();
		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() >= timestamp) {
				values.add(entry.getValue());
			}
		}
		
		return values;
	}

	public Collection<ValueType> selectBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		
		Collection<ValueType> values = new ArrayList<ValueType>();
		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() >= timestamp1 && entry.getKey() <= timestamp2) {
				values.add(entry.getValue());
			}
		}
		
		return values;
	}

	public void insertValue(String seriesName, List<DataPoint> points) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		
		Iterator<DataPoint> pointIterator = points.iterator();
		while(pointIterator.hasNext()) {
			DataPoint point = pointIterator.next();
			series.addPoint(point.getTimestamp(), point.getValue());
		}
	}

	public void createSeries(String seriesName, Class<? extends ValueType> type) throws SeriesAlreadyExists {
		
		
	}

	public void deleteSeries(String seriesName) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		dataBase.deleteSeries(seriesName);
	}

	public void deleteByTimestamp(String seriesName, Long timestamp) throws SeriesNotFound {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		
		series.deletePoint(timestamp);
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
