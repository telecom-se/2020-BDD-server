package fr.tse.db.storage.data;


import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;


public class SeriesConverterTest {

	@Test
	public void compressTest() {
		
		SeriesUnComp<Int32> UnComp = new SeriesUnComp<Int32>("comp",Int32.class);

		
		UnComp.addPoint(5L, new Int32(4));
		
		SeriesComp<Int32> comp = SeriesConverter.compress(UnComp); 
		
		assertEquals("comp", comp.getName());
		assertEquals("Int32", comp.getType().getSimpleName());
		
		Map<Long, Int32> points = comp.getPoints();
		
		assertEquals(1, points.size() );
		assertEquals(new Integer(4), points.get(5L).getVal());
		

	}

	@Test
	public void unCompressTest() {
		
		SeriesComp<Int32> UnComp = new SeriesComp<Int32>("comp",Int32.class);

		
		UnComp.addPoint(5L, new Int32(4));
		
		SeriesUnComp<Int32> comp = SeriesConverter.unCompress(UnComp); 
		
		assertEquals("comp", comp.getName());
		assertEquals("Int32", comp.getType().getSimpleName());
		
		Map<Long, Int32> points = comp.getPoints();
		
		assertEquals(1, points.size() );
		assertEquals(new Integer(4), points.get(5L).getVal());
	}
}
