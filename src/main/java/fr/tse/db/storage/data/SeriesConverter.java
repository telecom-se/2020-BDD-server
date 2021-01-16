package fr.tse.db.storage.data;

import java.util.Map;

/**
 * Class with static methods to switch between compressed and uncompressed
 * series.
 * 
 * @author remi huguenot
 *
 */
public class SeriesConverter {

	public static <ValType extends ValueType> SeriesComp<ValType> compress(SeriesUnComp<ValType> Serr) {

		SeriesComp<ValType> nuSerr = new SeriesComp<ValType>(Serr.getName(), Serr.getType());

		Map<Long, ValType> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}
		return nuSerr;

	}

	public static <ValType extends ValueType> SeriesUnComp<ValType> unCompress(SeriesComp<ValType> Serr) {

		SeriesUnComp<ValType> nuSerr = new SeriesUnComp<ValType>(Serr.getName(), Serr.getType());
		Map<Long, ValType> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;
	}

}
