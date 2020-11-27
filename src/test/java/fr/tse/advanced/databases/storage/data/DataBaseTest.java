package fr.tse.advanced.databases.storage.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataBaseTest {

	// Series for test
	private DataBase database;
	
	@Before
	public void initialize() {
		this.database = new DataBase();
	}
	
	@Test
	public void addSeriesTest() {
		Series<Int64> s = new Series<Int64>("name");
		this.database.addSeries(s);
		
		assertEquals(this.database.getSeries().size(), 1);
		assertEquals(this.database.getSeries().get("name"), s);
	}
	
	@Test
	public void getByNameTest() {
		Series<Int64> s = new Series<Int64>("name_test");
		this.database.getSeries().put("name_test", s);
		Series result = this.database.getByName("name_test");
		
		assertEquals(result, s);
	}

}
