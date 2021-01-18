package fr.tse.db.storage.data;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class SeriesQueueTest {
    @Test
    public void addValTest() {
        SeriesQueue<Int32> series = new SeriesQueue<>(0L);
        ArrayList<DataPointCompressed> list = series.getSeries();

        BitSet one = new BitSet();
        BitSet ten = new BitSet();

        one.set(0, true);
        ten.set(3, true);
        ten.set(1, true);

        assertEquals(0, list.size());

        series.addVal(10L, new Int32(1));

        list = series.getSeries();

        assertEquals(1, list.size());
        assertEquals(list.get(0).getTimestamp(), ten);
        assertEquals(list.get(0).getValue(), one);

        series.addVal(11L, new Int32(1));
        list = series.getSeries();

        BitSet zero = new BitSet();
        zero.set(0, false);

        assertEquals(2, list.size());
        assertEquals(list.get(1).getTimestamp(), one);
        assertEquals(list.get(1).getValue(), zero);

        series.addVal(12L, new Int32(5));
        list = series.getSeries();

        assertEquals(3, list.size());
        assertEquals(list.get(1).getTimestamp(), one);
        assertEquals(list.get(1).getValue(), zero);
    }

    @Test
    public void getValTest() {
        SeriesQueue<Int32> series = new SeriesQueue<Int32>(0L);
        series.addVal(10L, new Int32(1));
        series.addVal(15L, new Int32(40));
        series.addVal(20L, new Int32(60));

        assertNull(series.getVal(78L, "Int32"));
        assertEquals(1, series.getVal(10L, "Int32").getVal());
        assertEquals(40, series.getVal(15L, "Int32").getVal());
        assertEquals(60, series.getVal(20L, "Int32").getVal());
        assertEquals(40, series.getVal(15L, "Int32").getVal());
    }

    @Test
    public void getVal2Test() {
        SeriesQueue<Int32> series = new SeriesQueue<Int32>(1500L);
        series.addVal(10L, new Int32(1));
        series.addVal(15L, new Int32(40));
        series.addVal(20L, new Int32(60));

        assertNull(series.getVal(78L, "Int32"));
        assertEquals(1, series.getVal(10L, "Int32").getVal());
        assertEquals(40, series.getVal(15L, "Int32").getVal());
        assertEquals(60, series.getVal(20L, "Int32").getVal());
        assertEquals(40, series.getVal(15L, "Int32").getVal());
    }

    @Test
    public void removeTest() {
        SeriesQueue<Int32> series = new SeriesQueue<Int32>(0L);
        series.addVal(10L, new Int32(1));

        series.remove(56L);
        assertEquals(1, series.getSeries().size());
        series.remove(10L);

        assertEquals(0, series.getSeries().size());
    }

    @Test
    public void getAllPointsTest() {
        SeriesQueue<Int32> series = new SeriesQueue<Int32>(0L);
        series.addVal(10L, new Int32(1));
        series.addVal(15L, new Int32(40));
        series.addVal(20L, new Int32(60));

        Map<Long, Int32> map = series.getAllPoints("Int32");
        assertEquals(3, map.size());
        assertEquals(Integer.valueOf(1), map.get(10L).getVal());
        assertEquals(Integer.valueOf(40), map.get(15L).getVal());
        assertEquals(Integer.valueOf(60), map.get(20L).getVal());
    }

    @Test
    public void getAllPoints2Test() {
        SeriesQueue<Int32> series = new SeriesQueue<Int32>(1500L);
        series.addVal(10L, new Int32(1));
        series.addVal(15L, new Int32(40));
        series.addVal(20L, new Int32(60));

        Map<Long, Int32> map = series.getAllPoints("Int32");
        assertEquals(3, map.size());
        assertEquals(Integer.valueOf(1), map.get(10L).getVal());
        assertEquals(Integer.valueOf(40), map.get(15L).getVal());
        assertEquals(Integer.valueOf(60), map.get(20L).getVal());
    }
}
