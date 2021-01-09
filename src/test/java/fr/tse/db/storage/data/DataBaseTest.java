package fr.tse.db.storage.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;


public class DataBaseTest {

	// Series for test
	private DataBase database=DataBase.getInstance();
	
	@Before
	public void initialize() {
	}
	
	@Test
	public void addSeriesTestValid() throws SeriesAlreadyExistsException {
		Series<Int64> s = new Series<Int64>("name1", Int64.class);
		int dbSize=this.database.getSeries().size();
		this.database.addSeries(s);
		
		assertEquals(dbSize+1,this.database.getSeries().size());
		assertEquals(s,this.database.getSeries().get("name1"));
	}
	
	@Test(expected= SeriesAlreadyExistsException.class)
	public void addSeriesTestInvalid() throws SeriesAlreadyExistsException{
		Series<Int64> s = new Series<Int64>("name2", Int64.class);
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