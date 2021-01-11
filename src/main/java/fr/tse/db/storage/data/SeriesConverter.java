package fr.tse.db.storage.data;

import java.util.Map;
/**
 * Class with static methods to switch between compressed and uncompressed series.
 * 
 * @author remi huguenot
 *
 */
public class SeriesConverter {

	public static SeriesComp<ValueType> compress( SeriesUnComp<ValueType> Serr ) {
		
		SeriesComp<ValueType> nuSerr = new SeriesComp<ValueType>(Serr.getName(), Serr.getType());
		
		Map<Long,ValueType> points = Serr.getPoints();
		
		for (Long key : points.keySet()) {
			
			nuSerr.addPoint(key, points.get(key));
		}		
		
		return nuSerr;
		
	}
	
	public static SeriesUnComp<ValueType> unCompress (SeriesComp<ValueType> Serr) {
		
		SeriesUnComp<ValueType> nuSerr = new SeriesUnComp<ValueType>(Serr.getName(), Serr.getType());
		Map<Long,ValueType> points = Serr.getPoints();
		
		for (Long key : points.keySet()) {
			
			nuSerr.addPoint(key, points.get(key));
		}		
		
		return nuSerr;		
	}
}
