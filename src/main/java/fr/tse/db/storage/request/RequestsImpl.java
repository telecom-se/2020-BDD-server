package fr.tse.db.storage.request;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.tse.db.storage.data.DataBase;
import fr.tse.db.storage.data.Series;
import fr.tse.db.storage.data.ValueType;
import fr.tse.db.storage.exception.EmptySeriesException;
import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;
import fr.tse.db.storage.exception.TimestampAlreadyExistsException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;

public class RequestsImpl implements Requests {

	@Override
	public Series selectSeries(String seriesName) throws SeriesNotFoundException {
		DataBase db = DataBase.getInstance();
		return db.getByName(seriesName);
	}

	@Override
	public Series selectByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream().filter(map -> map.getKey() == timestamp)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public Series selectLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream().filter(map -> map.getKey() < timestamp)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public Series selectLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream().filter(map -> map.getKey() <= timestamp) 
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public Series selectHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream().filter(map -> map.getKey() > timestamp)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public Series selectHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream().filter(map -> map.getKey() >= timestamp) 
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public Series selectBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2)
			throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream()
				.filter(map -> (map.getKey() >= timestamp1 && map.getKey() <= timestamp2))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public Series selectNotInBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2)
			throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		Map<Long, ValueType> result = series.getPoints().entrySet().stream()
				.filter(map -> (map.getKey() <= timestamp1 || map.getKey() >= timestamp2))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return new Series<>(null, series.getType(), result);
	}

	@Override
	public void insertValue(String seriesName, Series insertedPoints)
			throws SeriesNotFoundException, WrongSeriesValueTypeException, TimestampAlreadyExistsException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		if (!series.getType().equals(insertedPoints.getType())) {
			throw new WrongSeriesValueTypeException(series.getType(), insertedPoints.getType());
		}
		if (((Series<ValueType>) insertedPoints).getPoints().entrySet().stream()
				.allMatch(map -> !series.getPoints().containsKey(map.getKey()))) {
			series.getPoints().putAll(insertedPoints.getPoints());
		} else {
			throw new TimestampAlreadyExistsException();
		}
	}

	@Override
	public <ValType extends ValueType> void createSeries(String seriesName, Class<ValType> type)
			throws SeriesAlreadyExistsException {
		DataBase dataBase = DataBase.getInstance();
		Series<ValType> series = new Series<ValType>(seriesName, type);
		dataBase.addSeries(series);
	}

	@Override
	public void deleteSeries(String seriesName) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		dataBase.deleteSeries(seriesName);
	}

	@Override
	public void deleteByTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		series.deletePoint(timestamp);
	}

	@Override
	public void deleteAllPoints(String seriesName) throws SeriesNotFoundException {
		Series<ValueType> series = DataBase.getInstance().getByName(seriesName);
		series.getPoints().clear();
	}

	public void deleteLowerThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if (entry.getKey() < timestamp) {
				iterator.remove();
			}
		}
	}

	public void deleteLowerOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if (entry.getKey() <= timestamp) {
				iterator.remove();
			}
		}
	}

	public void deleteHigherThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if (entry.getKey() > timestamp) {
				iterator.remove();
			}
		}
	}

	public void deleteHigherOrEqualThanTimestamp(String seriesName, Long timestamp) throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if (entry.getKey() >= timestamp) {
				iterator.remove();
			}
		}
	}

	@Override
	public void deleteBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2)
			throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if (entry.getKey() >= timestamp1 && entry.getKey() <= timestamp2) {
				iterator.remove();
			}
		}
	}

	@Override
	public void deleteNotInBetweenTimestampBothIncluded(String seriesName, Long timestamp1, Long timestamp2)
			throws SeriesNotFoundException {
		DataBase dataBase = DataBase.getInstance();
		Series series = dataBase.getByName(seriesName);

		Iterator<Entry<Long, ValueType>> iterator = series.getPoints().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ValueType> entry = iterator.next();
			if (entry.getKey() <= timestamp1 && entry.getKey() >= timestamp2) {
				iterator.remove();
			}
		}
	}

	@Override
	public float average(Series series) throws EmptySeriesException {
		Iterator<ValueType> iterator = series.getPoints().values().iterator();
		if (iterator.hasNext()) {
			ValueType sum = iterator.next();
			int count = 1;
			while (iterator.hasNext()) {
				ValueType entry = iterator.next();
				sum.sum(entry);
				count++;
			}
			return sum.divide(count);
		} else {
			throw new EmptySeriesException();
		}
	}

	@Override
	public ValueType min(Series series) throws EmptySeriesException {
		return ((Series<ValueType>) series).getPoints().values().parallelStream().min(ValueType::compareTo).get();
	}

	@Override
	public ValueType max(Series series) throws EmptySeriesException {
		return ((Series<ValueType>) series).getPoints().values().parallelStream().max(ValueType::compareTo).get();
	}

	@Override
	public int count(Series series) {
		return series.getPoints().size();
	}

	@Override
	public ValueType sum(Series series) throws EmptySeriesException {
		Iterator<ValueType> iterator = series.getPoints().values().iterator();
		if (iterator.hasNext()) {
			ValueType sum = iterator.next();
			while (iterator.hasNext()) {
				ValueType entry = iterator.next();
				sum.sum(entry);
			}
			return sum;
		} else {
			throw new EmptySeriesException();
		}
	}

}
