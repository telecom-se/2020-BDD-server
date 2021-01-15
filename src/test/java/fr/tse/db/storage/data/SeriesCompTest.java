package fr.tse.db.storage.data;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SeriesCompTest {

    private SeriesComp<Int32> ser32;
    private SeriesComp<Int64> ser64;


    private Int32 p32;
    private Int64 p64;


    @Test
    public void getPointsTest() {

        ser32 = new SeriesComp<Int32>("SERIE32", Int32.class);
        ser64 = new SeriesComp<Int64>("SERIE64", Int64.class);

        p32 = new Int32(1);
        p64 = new Int64(1L);


        ser32.addPoint(1L, p32);
        p32.setVal(2);
        ser32.addPoint(2L, p32);

        ser64.addPoint(1L, p64);
        p64.setVal(2L);
        ser64.addPoint(2000L, p64);


        Map<Long, Int32> map32 = ser32.getPoints();
        Map<Long, Int64> map64 = ser64.getPoints();

        assertEquals(2, map32.size());
        assertEquals(new Integer(1), map32.get(1L).getVal());
        assertEquals(new Integer(2), map32.get(2L).getVal());


        assertEquals(2, map64.size());
        assertEquals((long) 1L, (long) map64.get(1L).getVal());
        assertEquals((long) 2L, (long) map64.get(2000L).getVal());

    }

    @Test
    public void addPointTest() {

        ser32 = new SeriesComp<Int32>("SERIE32", Int32.class);
        ser64 = new SeriesComp<Int64>("SERIE64", Int64.class);

        p32 = new Int32(1);
        p64 = new Int64(1L);


        ser32.addPoint(1L, p32);
        p32.setVal(2);
        ser32.addPoint(2L, p32);

        ser64.addPoint(1L, p64);
        p64.setVal(2L);
        ser64.addPoint(2L, p64);

        Map<Long, SerieQueue<Int32>> map32 = ser32.getPointsComp();
        Map<Long, SerieQueue<Int64>> map64 = ser64.getPointsComp();

        assertEquals(new Integer(1), map32.get(0L).getVal(1L, "Int32").getVal());
        assertEquals((long) 1, (long) map64.get(0L).getVal(1L, "Int64").getVal());
        assertEquals(new Integer(2), map32.get(0L).getVal(2L, "Int32").getVal());
        assertEquals((long) 2, (long) map64.get(0L).getVal(2L, "Int64").getVal());

    }

    @Test
    public void deletePointTest() {

        ser32 = new SeriesComp<Int32>("SERIE32", Int32.class);
        ser64 = new SeriesComp<Int64>("SERIE64", Int64.class);

        p32 = new Int32(1);
        p64 = new Int64(1L);


        ser32.addPoint(1L, p32);
        p32.setVal(2);
        ser32.addPoint(2L, p32);

        ser64.addPoint(1L, p64);
        p64.setVal(2L);
        ser64.addPoint(2L, p64);

        ser32.deletePoint(1L);
        ser64.deletePoint(2L);

        Map<Long, SerieQueue<Int32>> map32 = ser32.getPointsComp();
        Map<Long, SerieQueue<Int64>> map64 = ser64.getPointsComp();

        assertEquals(1, map32.size());
        assertEquals(1, map64.size());

        assertEquals(null, map32.get(0L).getVal(1L, "Int32"));
        assertEquals(null, map64.get(0L).getVal(2L, "Int64"));


    }

    @Test
    public void getByTimestampTest() {

        ser32 = new SeriesComp<Int32>("SERIE32", Int32.class);
        ser64 = new SeriesComp<Int64>("SERIE64", Int64.class);

        p32 = new Int32(1);
        p64 = new Int64(1L);


        ser32.addPoint(1L, p32);
        p32.setVal(2);
        ser32.addPoint(2L, p32);

        ser64.addPoint(1L, p64);
        p64.setVal(2L);
        ser64.addPoint(2000L, p64);


        assertEquals(new Integer(1), ser32.getByTimestamp(1L).getVal());
        assertEquals((long) 1, (long) ser64.getByTimestamp(1L).getVal());
        assertEquals((long) 2, (long) ser64.getByTimestamp(2000L).getVal());


    }

    @Test
    public void troncTimeTest() {
        ser32 = new SeriesComp<Int32>("SERIE32", Int32.class);

        assertEquals(0, ser32.truncateTime(10L));
        assertEquals(1000, ser32.truncateTime(1100L));


    }
}
