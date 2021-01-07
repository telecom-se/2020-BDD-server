package fr.tse.advanced.databases.storage.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import fr.tse.advanced.databases.storage.data.DataBase;
import fr.tse.advanced.databases.storage.data.DataPoint;
import fr.tse.advanced.databases.storage.data.Series;
import fr.tse.advanced.databases.storage.data.ValueType;
import fr.tse.advanced.databases.storage.exception.SeriesAlreadyExistsException;
import fr.tse.advanced.databases.storage.exception.SeriesNotFoundException;
import fr.tse.advanced.databases.storage.exception.TimestampAlreadyExistsException;
import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public class RequestsImpl implements Requests {

	public ValueType selectByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);
		return series.getByTimestamp(timestamp);
	}

	public Collection<ValueType> selectLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
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

	public Collection<ValueType> selectLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
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

	public Collection<ValueType> selectHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
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

	public Collection<ValueType> selectHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
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

	public Collection<ValueType> selectBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2) throws SeriesNotFoundException {
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

	public <ValType extends ValueType> void insertValue(String seriesName, Collection<DataPoint<ValType>> points) throws SeriesNotFoundException, WrongValueTypeException, TimestampAlreadyExistsException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		// Pre-Check types
		Iterator<DataPoint<ValType>> pointIterator = points.iterator();
		while(pointIterator.hasNext()) {
			DataPoint<ValType> point = pointIterator.next();
			if(!point.getValue().getClass().equals(series.getType())) {
				throw new WrongValueTypeException(point.getValue().getClass(), series.getType());
			}
			if(series.getByTimestamp(point.getTimestamp()) != null) {
				throw new TimestampAlreadyExistsException(point.getTimestamp());
			}
		}

		pointIterator = points.iterator();
		while(pointIterator.hasNext()) {
			DataPoint<ValType> point = pointIterator.next();
			series.addPoint(point.getTimestamp(), point.getValue());
		}
	}

	public <ValType extends ValueType> void createSeries(String seriesName, Class<ValType> type) throws SeriesAlreadyExistsException {
		DataBase dataBase = DataBase.getInstance();
		Series<ValType> series = new Series<ValType>(seriesName, type);
		dataBase.addSeries(series);

	}

	public void deleteSeries(String seriesName) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		dataBase.deleteSeries(seriesName);
	}

	public void deleteByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		series.deletePoint(timestamp);
	}

	public void deleteLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() < timestamp) {
				iterator.remove();
			}
		}
	}

	public void deleteLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() <= timestamp) {
				iterator.remove();
			}
		}
	}

	public void deleteHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() > timestamp) {
				iterator.remove();
			}
		}
	}

	public void deleteHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if(entry.getKey() >= timestamp) {
				iterator.remove();
			}
		}
	}

	public ValueType average(Collection<ValueType> seriesValues) {
		
	}


	public ValueType min(Collection<ValueType> seriesValues) {
		
	}


	public ValueType max(Collection<ValueType> seriesValues) {
		
	}


	public int count(Collection<ValueType> seriesValues) {
	}


	public ValueType sum(Collection<ValueType> seriesValues) {
		
	}



}
