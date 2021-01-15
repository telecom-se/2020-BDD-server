package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static DataBase instance = new DataBase();

    // parameters
    private Map<String, Series<ValueType>> series;

    // constructors
    private DataBase() {
        this.series = new HashMap<String, Series<ValueType>>();
    }

    private DataBase(List<Series> series) {
        this.series = new HashMap<String, Series<ValueType>>();
        for (int i = 0; i < series.size(); i++) {
            this.series.put(series.get(i).getName(), series.get(i));
        }
    }

    public static DataBase getInstance() {
        return instance;
    }

    /**
     * Static export of database into file
     *
     * @param db
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void dumpDatabase(DataBase db) throws FileNotFoundException, IOException {
        File fichier = new File("db.db");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier, false));
        oos.writeObject(db);
    }

    /**
     * Static import of database from file
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static DataBase loadDatabase() throws FileNotFoundException, IOException, ClassNotFoundException {
        File fichier = new File("db.db");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
        return (DataBase) ois.readObject();
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

        if (this.series.get(series.getName()) != null) {
            throw new SeriesAlreadyExistsException(series.getName());
        } else {
            this.series.put(series.getName(), series);
        }
    }

    /**
     * A method that gets a {@link Series} from your database
     *
     * @param seriesName the name of the series you want to retrieve
     *
     * @return the corresponding Series in the database
     * @throws SeriesNotFoundException if the series is not in your database
     */

    public void deleteSeries(String seriesName) throws SeriesNotFoundException {
        Series series = this.series.remove(seriesName);
        if (series == null) {
            throw new SeriesNotFoundException(seriesName);
        }
    }

    public Series getByName(String name) throws SeriesNotFoundException {
        if (this.series.get(name) != null) {
            return this.series.get(name);
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
        Map<String, SeriesComp> map = this.series.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> SeriesConverter.compress((SeriesUnComp) entry.getValue())
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
        Map<String, SeriesComp> map = (Map<String, SeriesComp>) in.readObject();
        this.series = map.entrySet().parallelStream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> SeriesConverter.uncompress(entry.getValue())
        ));
    }
}
