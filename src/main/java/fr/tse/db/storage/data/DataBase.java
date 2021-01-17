package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This DataBase class implements the main structure that stores all
 * the data using series (in memory only for now)
 * the data using {@link Series} (in memory only for now)
 * <br>
 * Implemented methods:
 * - getByName() returns a series from its given name
 * - addSeries() creates a series in the database
 *
 * @author Arnaud, Valentin
 * @since 2020-11
 */
public class DataBase implements Serializable {
    private static final DataBase instance = new DataBase();

    // parameters
    private Map<String, Series<ValueType>> series;

    // constructors
    private DataBase() {
        this.series = new HashMap<>();
    }

    private DataBase(List<Series> series) {
    	this.series = series.parallelStream().collect(Collectors.toMap(ser -> ser.getName(), ser -> ser));
    }

    public static DataBase getInstance() {
        return instance;
    }

    // getters and setters
    public Map<String, Series<ValueType>> getSeries() {
        return series;
    }

    public void setSeries(Map<String, Series<ValueType>> series) {
        this.series = series;
    }

    // methods

    /**
     * A method that adds a new {@link Series} in the database
     *
     * @param series is the Series you want to add
     *
     * @throws SeriesAlreadyExistsException if the series is already in your database
     */
    public void addSeries(Series<ValueType> series) throws SeriesAlreadyExistsException {
        if (this.series.putIfAbsent(series.getName(), series) != null) {
            throw new SeriesAlreadyExistsException(series.getName());
        }
    }

    /**
     * A method that gets a {@link Series} from your database
     *
     * @param seriesName the name of the series you want to retrieve
     *
     * @throws SeriesNotFoundException if the series is not in your database
     */
    public void deleteSeries(String seriesName) throws SeriesNotFoundException {
        if (this.series.remove(seriesName) == null) {
            throw new SeriesNotFoundException(seriesName);
        }
    }

    public Series getByName(String name) throws SeriesNotFoundException {
        Series<ValueType> series = this.series.get(name);
        if (series != null) {
            return series;
        } else {
            throw new SeriesNotFoundException(name);
        }
    }

    /**
     * Writes the object into an outputStream
     *
     * @param out
     *
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        Map<String, SeriesCompressed> map = this.series.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> SeriesConverter.compress((SeriesUncompressed) entry.getValue())
                ));
        out.writeObject(map);
    }

    /**
     * Reads a database from inputStream
     *
     * @param in
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        Map<String, SeriesCompressed> map = (Map<String, SeriesCompressed>) in.readObject();
        this.series = map.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> SeriesConverter.uncompress(entry.getValue())
                ));
    }
}
