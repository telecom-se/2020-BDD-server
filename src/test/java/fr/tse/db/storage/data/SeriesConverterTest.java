package fr.tse.db.storage.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import static fr.tse.db.storage.data.SeriesConverter.*
;class SeriesConverterTest {

	@Test
	void compressTest() {
		
		SeriesUnComp<Int32> UnComp = new SeriesUnComp<Int32>("comp",Int32.class);

		
		UnComp.addPoint(5L, new Int32(4));
		
		SeriesComp<Int32> comp = compressInt32(UnComp); 
		
		assertEquals("comp", comp.getName());
		assertEquals("Int32", comp.getType().getSimpleName());
		
		Map<Long, Int32> points = comp.getPoints();
		
		assertEquals(1, points.size() );
		assertEquals(4, points.get(5L).getVal());
		

	}

	@Test
	void unCompressTest() {
		
		SeriesComp<Int32> UnComp = new SeriesComp<Int32>("comp",Int32.class);

		
		UnComp.addPoint(5L, new Int32(4));
		
		SeriesUnComp<Int32> comp = unCompressInt32(UnComp); 
		
		assertEquals("comp", comp.getName());
		assertEquals("Int32", comp.getType().getSimpleName());
		
		Map<Long, Int32> points = comp.getPoints();
		
		assertEquals(1, points.size() );
		assertEquals(4, points.get(5L).getVal());
	}
}
