package fr.tse.db.storage.data;

import java.util.Map;

/**
 * Class with static methods to switch between compressed and uncompressed
 * series.
 *
 * @author remi huguenot
 */
public class SeriesConverter {
    public static <ValType extends ValueType> SeriesComp<ValType> compress(SeriesUnComp<ValType> seriesUnComp) {
        SeriesComp<ValType> nuSerr = new SeriesComp<ValType>(seriesUnComp.getName(), seriesUnComp.getType());

        Map<Long, ValType> points = seriesUnComp.getPoints();

        for (Long key : points.keySet()) {

            nuSerr.addPoint(key, points.get(key));
        }
        return nuSerr;
    }

    public static <ValType extends ValueType> SeriesUnComp<ValType> uncompress(SeriesComp<ValType> seriesComp) {
        SeriesUnComp<ValType> nuSerr = new SeriesUnComp<ValType>(seriesComp.getName(), seriesComp.getType());
        Map<Long, ValType> points = seriesComp.getPoints();

        for (Long key : points.keySet()) {

            nuSerr.addPoint(key, points.get(key));
        }

        return nuSerr;
    }
}
