package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.SeriesAlreadyExists;
import fr.tse.db.storage.exception.SeriesNotFound;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DataBaseTest {

	// Series for test
	private DataBase database;
	
	@Before
	public void initialize() {
		this.database = new DataBase();
	}
	
	@Test
	public void addSeriesTestValid() throws SeriesAlreadyExists {
		Series<Int64> s = new Series<Int64>("name");

		this.database.addSeries(s);
		
		assertEquals(this.database.getSeries().size(), 1);
		assertEquals(this.database.getSeries().get("name"), s);
	}
	
	@Test(expected= SeriesAlreadyExists.class)
	public void addSeriesTestInvalid() throws SeriesAlreadyExists{
		Series<Int64> s = new Series<Int64>("name");
		this.database.addSeries(s);
		this.database.addSeries(s);
	
	}
	
	
	@Test
	public void getByNameTestValid() throws SeriesNotFound {
		Series<Int64> s = new Series<Int64>("name_test");
		this.database.getSeries().put("name_test", s);
		Series<Int64> result=this.database.getByName("name_test");;
		
		assertEquals(result, s);
		
	}

	@Test(expected = SeriesNotFound.class)
	public void getByNameTestInvalid() throws SeriesNotFound{
		
		Series<Int64> result = this.database.getByName("Wrng_name");

	}
	
	


}
