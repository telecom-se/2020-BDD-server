package fr.tse.db.storage.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

public class SerializationTest {
    @Test
    public void serializeDBTest() throws IOException, ClassNotFoundException {
        File file = new File("db.db");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, false));

        DataBase db = DataBase.getInstance();

        Series s = new SeriesUncompressed<>("seriesTest2", Int64.class);
        Long tmp = (long) 1000000;
        Int64 val = new Int64((long) 658);
        s.addPoint(tmp, val);
        Map<Long, Int64> result = s.getPoints();

        db.addSeries(s);

        oos.writeObject(db);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        DataBase db2 = (DataBase) ois.readObject();

        Assertions.assertEquals(db.getByName("seriesTest2").getName(), db2.getByName("seriesTest2").getName());
    }
}
