package fr.tse.db.storage.request;

import fr.tse.db.storage.data.*;
import fr.tse.db.storage.exception.EmptySeriesException;
import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ActiveProfiles("test")
public class RequestTest {

	// Series for test
	private SeriesUnComp<Int64> series= new SeriesUnComp<Int64>("seriesTest", Int64.class);
	private SeriesUnComp<Int32> series32= new SeriesUnComp<Int32>("seriesTest32", Int32.class);
	private DataBase database= DataBase.getInstance();
	Long tmp = (long) 1000000;
	Long tmp2 =  (long) 1000001;
	Long tmp3 = (long) 999999;
	Int64 val = new Int64((long) 658);
	Int32 val32 = new Int32((45));

	private RequestsImpl req;

	@Before
	public void initialize() throws SeriesAlreadyExistsException {
		req= new RequestsImpl();
		DataBase database = DataBase.getInstance();
		ArrayList<String> seriesNameList = new ArrayList<>(database.getSeries().keySet());
		for(String name : seriesNameList){
			database.deleteSeries(name);
		}
		

		series = new SeriesUnComp<Int64>("seriesTest", Int64.class);
		series32 = new SeriesUnComp<Int32>("seriesTest32", Int32.class);
		

		series.addPoint(tmp, val);
		
		
		series32.addPoint(tmp, val32);
		
		database.addSeries(series);
		database.addSeries(series32);
	}
	
	@Test
	public void showAllSeriesTest() {
		Map<String, String> allSeries= req.showAllSeries();
		assertEquals(2,allSeries.size());
		assertEquals((new Int64(0L)).getClass().getSimpleName(), allSeries.get("seriesTest"));
	}
	
	@Test
	public void selectByTimestampTest() throws SeriesNotFoundException  {		
		Series series1 =  req.selectByTimestamp("seriesTest", tmp);
		
		assertEquals(series1.getPoints().size(),1);
		assertEquals(series1.getPoints().get(tmp), val);
		
		Series series2 =  req.selectByTimestamp("seriesTest32", tmp);
		
		assertEquals(series2.getPoints().size(),1);
		assertEquals(series2.getPoints().get(tmp), val32);

		
	}
	
	
	@Test
	public void selectSeriesTest() throws SeriesNotFoundException {
		Series series1=req.selectSeries("seriesTest");
		assertEquals(series1,series);
	}
	
	@Test
	public void selectLowerThanTimestampTest() throws SeriesNotFoundException {

		Series series1 = req.selectLowerThanTimestamp("seriesTest", tmp2);
		
		assertEquals(series1.getPoints().size(), 1);
		assertEquals(series1.getPoints().get(tmp), val);
				
		Series series2 = req.selectLowerThanTimestamp("seriesTest", tmp);
		
		assertEquals(series2.getPoints().size(), 0);
		
		Series series3 = req.selectLowerThanTimestamp("seriesTest", tmp3 );
		
		assertEquals(series3.getPoints().size(), 0);
	}
	
	
	@Test
	public void selectLowerOrEqualThanTimestampTest() throws SeriesNotFoundException {
		Series series1 = req.selectLowerOrEqualThanTimestamp("seriesTest", tmp2);
		
		assertEquals(series1.getPoints().size(), 1);
		assertEquals(series1.getPoints().get(tmp), val);
				
		Series series2 = req.selectLowerOrEqualThanTimestamp("seriesTest", tmp);
		
		assertEquals(series2.getPoints().size(), 1);
		assertEquals(series2.getPoints().get(tmp), val);
		
		Series series3 = req.selectLowerOrEqualThanTimestamp("seriesTest", tmp3 );
		
		assertEquals(series3.getPoints().size(), 0);
	}
	
	

	@Test
	public void selectHigherThanTimestampTest() throws SeriesNotFoundException {
		Series series1 = req.selectHigherThanTimestamp("seriesTest", tmp2);
		
		assertEquals(0, series1.getPoints().size());
				
		Series series2 = req.selectHigherThanTimestamp("seriesTest", tmp);
		
		assertEquals(0, series2.getPoints().size());
		
		Series series3 = req.selectHigherThanTimestamp("seriesTest", tmp3 );
		
		assertEquals(1, series3.getPoints().size());
		assertEquals(val, series3.getPoints().get(tmp));
	}
	
	
	@Test
	public void selectHigherOrEqualThanTimestampTest() throws SeriesNotFoundException {
		Series series1 = req.selectHigherOrEqualThanTimestamp("seriesTest", tmp2);
		
		assertEquals(0, series1.getPoints().size());
				
		Series series2 = req.selectHigherOrEqualThanTimestamp("seriesTest", tmp);
		
		assertEquals(1,series2.getPoints().size());
		assertEquals(val, series2.getPoints().get(tmp));

		
		Series series3 = req.selectHigherOrEqualThanTimestamp("seriesTest", tmp3 );
		
		assertEquals(1, series3.getPoints().size());
		assertEquals(val, series3.getPoints().get(tmp));
	}
	
	
	@Test
	public void selectBetweenTimestampBothIncluded() throws SeriesNotFoundException {
		Series series1 = req.selectBetweenTimestampBothIncluded("seriesTest", tmp - (long) 3,tmp3);
		
		assertEquals(series1.getPoints().size(), 0);
		
		Series series2 = req.selectBetweenTimestampBothIncluded("seriesTest", tmp,tmp);
		
		assertEquals(series2.getPoints().size(), 1);
		assertEquals(series2.getPoints().get(tmp), val);
		
		Series series3 = req.selectBetweenTimestampBothIncluded("seriesTest", tmp3,tmp2);
		
		assertEquals(series3.getPoints().size(), 1);
		assertEquals(series3.getPoints().get(tmp), val);
	}
	@Test
	public void selectNotInBetweenTimestampBothIncludedTest() {
		
		Series series1 = req.selectNotInBetweenTimestampBothIncluded("seriesTest", tmp3-(long) 1, tmp3);
		
		assertEquals(1,series1.getPoints().size());
		
		Series series2 = req.selectNotInBetweenTimestampBothIncluded("seriesTest", tmp,tmp);
		
		assertEquals(1,series2.getPoints().size());
		assertEquals(val, series2.getPoints().get(tmp));
		
		Series series3 = req.selectNotInBetweenTimestampBothIncluded("seriesTest", tmp3,tmp2);
		assertEquals(0, series3.getPoints().size());

		
	}
	
	@Test
	public void insertValueTest() throws SeriesNotFoundException {
		Int64 nuVal = new Int64((long) 696);
		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		m1.put(tmp2, new Int64((long) 696));
		Series<Int64> nuPoint = new SeriesUnComp<>(null,Int64.class,m1);

		req.insertValue("seriesTest", nuPoint);
				
		assertEquals(2, series.getPoints().size());
		assertEquals(series.getPoints().get(tmp2).getVal(), nuVal.getVal());

		series.deletePoint(tmp2);
	}

	
	@Test
	public void createSeriesTest() throws SeriesAlreadyExistsException, SeriesNotFoundException {
		
		req.createSeries("nuSer", Int32.class);
		assertEquals( database.getByName("nuSer").getName(), "nuSer");

		
	}
	
	
	@Test()
	public void deleteSeriesTest() throws SeriesNotFoundException, SeriesAlreadyExistsException{

		SeriesUnComp<Int64> toDel = new SeriesUnComp<Int64>("toDel", Int64.class);

		database.addSeries(toDel);

		req.deleteSeries("toDel");
		try {
			database.getByName("toDel");
            fail();
		} catch (SeriesNotFoundException e) {
			assertEquals(e.getMessage(),"\""+"toDel"+"\""  );
        }

	}
	
	
	@Test
	public void deleteByTimestampTest() throws SeriesNotFoundException{
		Int64 nuVal = new Int64((long) 666);

		database.getByName("seriesTest").addPoint(tmp3, nuVal);

		req.deleteByTimestamp("seriesTest", tmp3);
		
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp3), null);
	}
	
	@Test
	public void deleteLowerThanTimestampTest() throws SeriesNotFoundException{
		
		Int64 nuVal = new Int64((long) 666);

		database.getByName("seriesTest").addPoint(tmp3, nuVal);
		database.getByName("seriesTest").addPoint(tmp3 - (long) 1, nuVal);

		req.deleteLowerThanTimestamp("seriesTest", tmp);
		
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp3), null);
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp3- (long)1), null);

	}
	
	
	@Test
	public void deleteLowerOrEqualThanTimestampTest() throws SeriesNotFoundException{
		Int64 nuVal = new Int64((long) 666);

		database.getByName("seriesTest").addPoint(tmp3, nuVal);
		database.getByName("seriesTest").addPoint(tmp3 - (long) 1, nuVal);

		req.deleteLowerOrEqualThanTimestamp("seriesTest", tmp3);
		
		assertEquals(((Int64)database.getByName("seriesTest").getPoints().get(tmp)).getVal(), val.getVal());
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp3), null);
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp3- (long)1), null);

	}
	
	
	@Test
	public void deleteHigherThanTimestampTest() throws SeriesNotFoundException{
		Int64 nuVal = new Int64((long) 666);

		database.getByName("seriesTest").addPoint(tmp2, nuVal);
		database.getByName("seriesTest").addPoint(tmp2 + (long) 1, nuVal);

		req.deleteHigherThanTimestamp("seriesTest", tmp2);
		
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp2), nuVal);
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp2 + (long)1), null);
		
		database.getByName("seriesTest").deletePoint(tmp2);

		}
	
	
	@Test
	public void deleteHigherOrEqualThanTimestampTest() throws SeriesNotFoundException{
		Int64 nuVal = new Int64((long) 666);

		database.getByName("seriesTest").addPoint(tmp2, nuVal);
		database.getByName("seriesTest").addPoint(tmp2 + (long) 1, nuVal);

		req.deleteHigherOrEqualThanTimestamp("seriesTest", tmp2);
		
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp2), null);
		assertEquals(database.getByName("seriesTest").getPoints().get(tmp2 + (long)1), null);
	}
	
	@Test
	public void averageTest() {
		
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		
		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		m1.put(tmp, val1 );
		m1.put(tmp2, val2);
		Series<Int64> seriesValues = new SeriesUnComp<>("abc",Int64.class,m1);
		
		Float av= req.average(seriesValues);
		
		assertEquals(Float.valueOf(25), av);
	}
	
	
	@Test
	public void minTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		m1.put(tmp, val1 );
		m1.put(tmp2, val2);
		m1.put(tmp3, val3);
		Series<Int64> seriesValues = new SeriesUnComp<>("abc",Int64.class,m1);
		
		ValueType min = req.min(seriesValues);
		
		assertEquals(10,(long) min.getVal());
	}
	
	@Test(expected = EmptySeriesException.class)
	public void minTest2() {
		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		Series<Int64> seriesValues = new SeriesUnComp<>("abc",Int64.class,m1);

		ValueType min = req.min(seriesValues);
	}

	@Test
	public void maxTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		m1.put(tmp, val1 );
		m1.put(tmp2, val2);
		m1.put(tmp3, val3);
		Series<Int64> seriesValues = new SeriesUnComp<>("abc",Int64.class,m1);
		
		ValueType max = req.max(seriesValues);
		
		assertEquals(30,(long) max.getVal());
	}
	
	@Test
	public void countTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		m1.put(tmp, val1 );
		m1.put(tmp2, val2);
		m1.put(tmp3, val3);
		Series<Int64> seriesValues = new SeriesUnComp<>("abc",Int64.class,m1);
		
		int count = req.count(seriesValues);
		
		assertEquals(3,count);
	}
	
	@Test
	public void sumTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		Map<Long,Int64> m1 = new HashMap<Long, Int64>();
		m1.put(tmp, val1 );
		m1.put(tmp2, val2);
		m1.put(tmp3, val3);
		Series<Int64> seriesValues = new SeriesUnComp<>("abc",Int64.class,m1);
		ValueType sum = req.sum(seriesValues);
		
		assertEquals(60, (long)sum.getVal());
	}
	
	

}
