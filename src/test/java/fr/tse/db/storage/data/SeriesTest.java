package fr.tse.db.storage.data;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class SeriesTest {

	// Series for test
	private Series<Int64> series;
	private Series<Int32> series32;
	
	@Before
	public void initialize() {
		this.series = new SeriesUnComp<Int64>("seriesTest", Int64.class);
		this.series32 = new SeriesUnComp<Int32>("seriesTest32", Int32.class);
	}
	
	@Test
	public void addPointTest() {
		
		Long tmp = (long) 1000000;

		Int64 val = new Int64((long) 658);
		this.series.addPoint(tmp, val);
		Map<Long, Int64> result = this.series.getPoints();
		
		assertEquals(1, result.size());
		assertEquals(result.get(tmp), val);
		
		
		Int32 val32 = new Int32(6584);
		this.series32.addPoint(tmp, val32);
		Map<Long, Int32> result32 = this.series32.getPoints();
		
		assertEquals(1, result32.size());
		assertEquals(result32.get(tmp), val32);
	}
	
	@Test
	public void getByTimestampTest() {
		
		Long tmp = (long) 1000000;
		Int64 val = new Int64((long) 658);
		this.series.getPoints().put(tmp, val);
		Int64 result = this.series.getByTimestamp(tmp);
		assertEquals(result, val);
	}
}
