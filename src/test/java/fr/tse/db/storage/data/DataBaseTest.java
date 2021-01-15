package fr.tse.db.storage.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(profiles = "test")
public class DataBaseTest {

	// Series for test
	private DataBase database=DataBase.getInstance();
	
	@Before
	public void initialize() {
	}
	
	@Test
	public void addSeriesTestValid() throws SeriesAlreadyExistsException {
		SeriesUnComp<Int64> s = new SeriesUnComp<Int64>("name1", Int64.class);
		int dbSize=this.database.getSeries().size();

		this.database.addSeries(s);
		
		assertEquals(dbSize+1,this.database.getSeries().size());
		assertEquals(s,this.database.getSeries().get("name1"));
	}
	
	@Test(expected= SeriesAlreadyExistsException.class)
	public void addSeriesTestInvalid() throws SeriesAlreadyExistsException{
		SeriesUnComp<Int64> s = new SeriesUnComp<Int64>("name", Int64.class);

		this.database.addSeries(s);
		this.database.addSeries(s);
	
	}
	
	
	@Test
	public void getByNameTestValid() throws SeriesNotFoundException {
		SeriesUnComp<Int64> s = new SeriesUnComp<Int64>("name_test", Int64.class);
		this.database.getSeries().put("name_test", s);
		Series<Int64> result=this.database.getByName("name_test");;
		
		assertEquals(result, s);
		
	}

	@Test(expected = SeriesNotFoundException.class)
	public void getByNameTestInvalid() throws SeriesNotFoundException{
		
		Series<Int64> result = this.database.getByName("Wrng_name");

	}
	
	


}
