package fr.tse.advanced.databases.storage.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.tse.advanced.databases.storage.exception.SeriesAlreadyExists;
import fr.tse.advanced.databases.storage.exception.SeriesNotFound;

public class DataBaseTest {

	// Series for test
	private DataBase database;
	
	@Before
	public void initialize() {
		this.database = new DataBase();
	}
	
	@Test
	public void addSeriesTestValid() throws SeriesAlreadyExists {
		Series s = new Series("name");
		this.database.addSeries(s);
		
		assertEquals(this.database.getSeries().size(), 1);
		assertEquals(this.database.getSeries().get("name"), s);
	}
	
	@Test(expected= SeriesAlreadyExists.class)
	public void addSeriesTestInvalid() throws SeriesAlreadyExists{
		Series s = new Series("name");
		this.database.addSeries(s);
		this.database.addSeries(s);
	
	}
	
	
	@Test
	public void getByNameTestValid() throws SeriesNotFound {
		Series s = new Series("name_test");
		this.database.getSeries().put("name_test", s);
		Series result;
		
		result = this.database.getByName("name_test");
		assertEquals(result, s);
		
	}

	@Test(expected = SeriesNotFound.class)
	public void getByNameTestInvalid() throws SeriesNotFound{
		
		Series result = this.database.getByName("Wrng_name");

	}
	
	
	
	
}
