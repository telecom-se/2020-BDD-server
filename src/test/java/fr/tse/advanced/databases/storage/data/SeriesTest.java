package fr.tse.advanced.databases.storage.data;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SeriesTest {

	// Series for test
	private Series series;
	
	@Before
	public void initialize() {
		this.series = new Series("seriesTest");
	}
	
	@Test
	public void addPointTest() {
		Long tmp = (long) 1000000;
		this.series.addPoint(tmp, 30.8);
		Map<Long, Double> result = this.series.getPoints();
		
		assertEquals(1, result.size());
		assertEquals(result.get(tmp), (Double) 30.8);
	}
	
	@Test
	public void getByTimestampTest() {
		Long tmp = (long) 1000000;
		this.series.getPoints().put(tmp, 3.14159);
		Double result = this.series.getByTimestamp(tmp);
		
		assertEquals(result, (Double) 3.14159);
	}

}
