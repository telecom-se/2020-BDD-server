package fr.tse.db.storage.data;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.junit.Test;

public class ReadWriteOnFileTest {
	@Test
	public void writeDBTest() throws FileNotFoundException, IOException, ClassNotFoundException {
		File fichier =  new File("db.db") ;
		
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier,false)) ;
		
		DataBase db = DataBase.getInstance();
		
		Long tmp = (long) 1000000;

		Int64 val = new Int64((long) 658);
		Series s=new SeriesUnComp<Int64>("seriesTest", Int64.class);
		s.addPoint(tmp, val);
		Map<Long, Int64> result = s.getPoints();
		
		db.addSeries(s);
		
		oos.writeObject(db) ;
		
		ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;

		DataBase db2=(DataBase) ois.readObject();
		
		assertEquals(db.getSeries().get("seriesTest").getName() , db2.getSeries().get("seriesTest").getName());

	}

	
	
	
}
