package fr.tse.db.storage.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.junit.Test;

public class ReadWriteOnFileTest {
	@Test
	public void writeDBTest() throws FileNotFoundException, IOException {
		File fichier =  new File("./tests/db.db") ;
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
		
		DataBase db = DataBase.getInstance();
		
		Long tmp = (long) 1000000;

		Int64 val = new Int64((long) 658);
		Series s=new SeriesUnComp<Int64>("seriesTest", Int64.class);
		s.addPoint(tmp, val);
		Map<Long, Int64> result = s.getPoints();
		
		db.addSeries(s);
		
		oos.writeObject(db) ;

	}
}
