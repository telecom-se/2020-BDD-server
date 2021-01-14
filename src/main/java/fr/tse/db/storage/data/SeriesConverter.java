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

	public static SeriesComp<Int32> compressInt32(SeriesUnComp<Int32> Serr) {

		SeriesComp<Int32> nuSerr = new SeriesComp<Int32>(Serr.getName(), Serr.getType());

		Map<Long, Int32> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;

	}

	public static SeriesUnComp<Int32> unCompressInt32(SeriesComp<Int32> Serr) {

		SeriesUnComp<Int32> nuSerr = new SeriesUnComp<Int32>(Serr.getName(), Serr.getType());
		Map<Long, Int32> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;
	}

	public static SeriesComp<Int64> compressInt64(SeriesUnComp<Int64> Serr) {

		SeriesComp<Int64> nuSerr = new SeriesComp<Int64>(Serr.getName(), Serr.getType());

		Map<Long, Int64> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;

	}

	public static SeriesUnComp<Int64> unCompressInt64(SeriesComp<Int64> Serr) {

		SeriesUnComp<Int64> nuSerr = new SeriesUnComp<Int64>(Serr.getName(), Serr.getType());
		Map<Long, Int64> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;
	}

	public static SeriesComp<Float32> compressFloat32(SeriesUnComp<Float32> Serr) {

		SeriesComp<Float32> nuSerr = new SeriesComp<Float32>(Serr.getName(), Serr.getType());

		Map<Long, Float32> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;

	}

	public static SeriesUnComp<Float32> unCompressFloat32(SeriesComp<Float32> Serr) {

		SeriesUnComp<Float32> nuSerr = new SeriesUnComp<Float32>(Serr.getName(), Serr.getType());
		Map<Long, Float32> points = Serr.getPoints();

		for (Long key : points.keySet()) {

			nuSerr.addPoint(key, points.get(key));
		}

		return nuSerr;
	}

}
