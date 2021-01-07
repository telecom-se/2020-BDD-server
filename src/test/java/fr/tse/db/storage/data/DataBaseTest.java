package fr.tse.db.storage.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;


public class DataBaseTest {

	// Series for test
	private DataBase database;
	
	@Before
	public void initialize() {
		this.database = new DataBase();
	}
	
	@Test
	public void addSeriesTestValid() throws SeriesAlreadyExistsException {
		Series<Int64> s = new Series<Int64>("name", Int64.class);

		this.database.addSeries(s);
		
		assertEquals(this.database.getSeries().size(), 1);
		assertEquals(this.database.getSeries().get("name"), s);
	}
	
	@Test(expected= SeriesAlreadyExistsException.class)
	public void addSeriesTestInvalid() throws SeriesAlreadyExistsException{
		Series<Int64> s = new Series<Int64>("name", Int64.class);
		this.database.addSeries(s);
		this.database.addSeries(s);
	
	}
	
	
	@Test
	public void getByNameTestValid() throws SeriesNotFoundException {
		Series<Int64> s = new Series<Int64>("name_test", Int64.class);
		this.database.getSeries().put("name_test", s);
		Series<Int64> result=this.database.getByName("name_test");;
		
		assertEquals(result, s);
		
	}

	@Test(expected = SeriesNotFoundException.class)
	public void getByNameTestInvalid() throws SeriesNotFoundException{
		
		Series<Int64> result = this.database.getByName("Wrng_name");

	}
	
	


}
