package fr.tse.db.storage.data;

import java.util.Map;

/**
 * Class with static methods to switch between compressed and uncompressed
 * series.
 *
 * @author remi huguenot
 */
public class SeriesConverter {
    public static <ValType extends ValueType> SeriesCompressed<ValType> compress(SeriesUncompressed<ValType> seriesUncompressed) {
        SeriesCompressed<ValType> newSeries = new SeriesCompressed<>(seriesUncompressed.getName(), seriesUncompressed.getType());
        Map<Long, ValType> points = seriesUncompressed.getPoints();

        for (Long key : points.keySet()) {
            newSeries.addPoint(key, points.get(key));
        }
        return newSeries;
    }

    public static <ValType extends ValueType> SeriesUncompressed<ValType> uncompress(SeriesCompressed<ValType> seriesCompressed) {
        SeriesUncompressed<ValType> newSeries = new SeriesUncompressed<>(seriesCompressed.getName(), seriesCompressed.getType());
        Map<Long, ValType> points = seriesCompressed.getPoints();

        for (Long key : points.keySet()) {
            newSeries.addPoint(key, points.get(key));
        }
        return newSeries;
    }
}
