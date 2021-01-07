package fr.tse.advanced.databases.storage.request;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import fr.tse.advanced.databases.storage.data.DataBase;
import fr.tse.advanced.databases.storage.data.DataPoint;
import fr.tse.advanced.databases.storage.data.Int32;
import fr.tse.advanced.databases.storage.data.Int64;
import fr.tse.advanced.databases.storage.data.Series;
import fr.tse.advanced.databases.storage.data.ValueType;
import fr.tse.advanced.databases.storage.exception.SeriesAlreadyExistsException;
import fr.tse.advanced.databases.storage.exception.SeriesNotFoundException;

public class RequestTest {

	// Series for test
	private Series<Int64> series= new Series<Int64>("seriesTest", Int64.class);
	private Series<Int32> series32= new Series<Int32>("seriesTest32", Int32.class);
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
		try {
			database.deleteSeries("seriesTest");
			database.deleteSeries("seriesTest32");
		}catch(SeriesNotFoundException e) {
			System.out.println("base not initialised");
		}
		

		series = new Series<Int64>("seriesTest", Int64.class);
		series32 = new Series<Int32>("seriesTest32", Int32.class);
		

		series.addPoint(tmp, val);
		
		
		series32.addPoint(tmp, val32);
		
		database.addSeries(series);
		database.addSeries(series32);
	}
	
	
	
	@Test
	public void selectByTimestampTest() throws SeriesNotFoundException  {		
		Int64 ret64 = (Int64) req.selectByTimestamp("seriesTest", tmp);
		
		assertEquals(ret64.getVal(), val.getVal());
		
		Int32 ret32 = (Int32) req.selectByTimestamp("seriesTest32", tmp);
		
		assertEquals(ret32.getVal(), val32.getVal());

		
	}
	
	@Test
	public void selectLowerThanTimestampTest() throws SeriesNotFoundException {

		Collection<ValueType> ret64 = req.selectLowerThanTimestamp("seriesTest", tmp2);
		
		assertEquals(ret64.size(), 1);
		Int64 num = (Int64) ret64.iterator().next();
		assertEquals(num.getVal(), val.getVal());
				
		Collection<ValueType> ret64_2 = req.selectLowerThanTimestamp("seriesTest", tmp);
		
		assertEquals(ret64_2.size(), 0);
		
		Collection<ValueType> ret64_3 = req.selectLowerThanTimestamp("seriesTest", tmp3 );
		
		assertEquals(ret64_3.size(), 0);
	}
	
	
	@Test
	public void selectLowerOrEqualsThanTimestampTest() throws SeriesNotFoundException {
		Collection<ValueType> ret64 = req.selectLowerOrEqualThanTimestamp("seriesTest", tmp2);
		
		assertEquals(ret64.size(), 1);
		Int64 num = (Int64) ret64.iterator().next();
		assertEquals(num.getVal(), val.getVal());
		
		Collection<ValueType> ret64_2 = req.selectLowerOrEqualThanTimestamp("seriesTest", tmp);
		
		assertEquals(ret64_2.size(), 1);
		Int64 num_2 = (Int64) ret64_2.iterator().next();
		assertEquals(num_2.getVal(), val.getVal());
		
		Collection<ValueType> ret64_3 = req.selectLowerOrEqualThanTimestamp("seriesTest", tmp3 );
		
		assertEquals(ret64_3.size(), 0);
	}
	
	

	@Test
	public void selectHigherThanTimestampTest() throws SeriesNotFoundException {
		Collection<ValueType> ret64 = req.selectHigherThanTimestamp("seriesTest", tmp3);
		
		assertEquals(ret64.size(), 1);
		Int64 num = (Int64) ret64.iterator().next();
		assertEquals(num.getVal(), val.getVal());
		
		Collection<ValueType> ret64_2 = req.selectHigherThanTimestamp("seriesTest", tmp);
		
		assertEquals(ret64_2.size(), 0);

		Collection<ValueType> ret64_3 = req.selectHigherThanTimestamp("seriesTest", tmp2 );
		
		assertEquals(ret64_3.size(), 0);
	}
	
	
	@Test
	public void selectHigherOrEqualsThanTimestampTest() throws SeriesNotFoundException {
		Collection<ValueType> ret64 = req.selectHigherOrEqualThanTimestamp("seriesTest", tmp3);
		System.out.println(ret64);
		
		assertEquals(ret64.size(), 1);
		Int64 num = (Int64) ret64.iterator().next();
		assertEquals(num.getVal(), val.getVal());
				
		Collection<ValueType> ret64_2 = req.selectHigherOrEqualThanTimestamp("seriesTest", tmp);
		
		assertEquals(ret64_2.size(), 1);
		Int64 num_2 = (Int64) ret64_2.iterator().next();
		assertEquals(num_2.getVal(), val.getVal());
				
		Collection<ValueType> ret64_3 = req.selectHigherOrEqualThanTimestamp("seriesTest", tmp2);
		
		assertEquals(ret64_3.size(), 0);
	}
	
	
	@Test
	public void selectBetweenTimestampBothIncluded() throws SeriesNotFoundException {
		Collection<ValueType> ret64 = req.selectBetweenTimestampBothIncluded("seriesTest", tmp - (long) 3,tmp3);
		
		assertEquals(ret64.size(), 0);
		
		Collection<ValueType> ret64_2 = req.selectBetweenTimestampBothIncluded("seriesTest", tmp,tmp);
		
		assertEquals(ret64_2.size(), 1);
		Int64 num = (Int64) ret64_2.iterator().next();
		assertEquals(num.getVal(), val.getVal());
	}
	
	
	@Test
	public void insertValueTest() throws SeriesNotFoundException {
		Int64 nuVal = new Int64((long) 696);
		DataPoint p1 = new DataPoint(tmp2, new Int64((long)696 ) );
		ArrayList<DataPoint<ValueType>> nuPoint = new ArrayList<DataPoint<ValueType>>();
		nuPoint.add(p1);
		
		req.insertValue("seriesTest", nuPoint);
		
		Collection<ValueType> ret =  req.selectHigherOrEqualThanTimestamp("seriesTest", tmp2);
		
		assertEquals( ret.size(), 1);
		Int64 num = (Int64) ret.iterator().next();
		assertEquals(num.getVal(), nuVal.getVal());
		
		database.getByName("seriesTest").deletePoint(tmp2);
	}

	
	@Test
	public void createSeriesTest() throws SeriesAlreadyExistsException, SeriesNotFoundException {
		
		req.createSeries("nuSer", Int32.class);
		assertEquals( database.getByName("nuSer").getName(), "nuSer");

		
	}
	
	
	@Test()
	public void deleteSeriesTest() throws SeriesNotFoundException, SeriesAlreadyExistsException{
		
		Series<Int64> toDel = new Series<Int64>("toDel", Int64.class);
		
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
		
		ArrayList<ValueType> seriesValues = new ArrayList<ValueType>();
		
		seriesValues.add(val1);
		seriesValues.add(val2);
		
		Float av= req.average(seriesValues );
		
		assertEquals(Float.valueOf(25),  av);
	}
	
	
	@Test
	public void minTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		ArrayList<ValueType> seriesValues = new ArrayList<ValueType>();
		
		seriesValues.add(val1);
		seriesValues.add(val2);
		seriesValues.add(val3);
		
		ValueType min = req.min(seriesValues);
		
		assertEquals(10,(long)min.getVal());
	}

	@Test
	public void maxTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		ArrayList<ValueType> seriesValues = new ArrayList<ValueType>();
		
		seriesValues.add(val1);
		seriesValues.add(val2);
		seriesValues.add(val3);
		
		ValueType min = req.max(seriesValues);
		
		assertEquals(30,(long)min.getVal());
	}
	
	@Test
	public void countTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		ArrayList<ValueType> seriesValues = new ArrayList<ValueType>();
		
		seriesValues.add(val1);
		seriesValues.add(val2);
		seriesValues.add(val3);
		
		int count = req.count(seriesValues);
		
		assertEquals(3,count);
	}
	
	@Test
	public void sumTest() {
		Int64 val1 = new Int64((long) 20);
		Int64 val2 = new Int64((long) 30);
		Int64 val3 = new Int64((long) 10);

		ArrayList<ValueType> seriesValues = new ArrayList<ValueType>();
		
		seriesValues.add(val1);
		seriesValues.add(val2);
		seriesValues.add(val3);
		
		ValueType sum = req.sum(seriesValues);
		
		assertEquals(60, (long)sum.getVal());
	}
	
	

}
