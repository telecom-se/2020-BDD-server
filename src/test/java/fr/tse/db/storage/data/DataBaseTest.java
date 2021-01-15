package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.exception.SeriesNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DataBaseTest {

    // Series for test
    private DataBase database = DataBase.getInstance();

    @Test
    public void addSeriesTestValid() throws SeriesAlreadyExistsException {
        SeriesUnComp<Int64> s = new SeriesUnComp<Int64>("name1", Int64.class);
        int dbSize = this.database.getSeries().size();

        this.database.addSeries(s);

        assertEquals(dbSize + 1, this.database.getSeries().size());
        assertEquals(s, this.database.getSeries().get("name1"));
    }

    @Test
    public void addSeriesTestInvalid() throws SeriesAlreadyExistsException {
        SeriesUnComp<Int64> s = new SeriesUnComp<Int64>("name", Int64.class);
        this.database.addSeries(s);
        assertThrows(SeriesAlreadyExistsException.class, () -> this.database.addSeries(s));
    }


    @Test
    public void getByNameTestValid() throws SeriesNotFoundException {
        SeriesUnComp<Int64> s = new SeriesUnComp<Int64>("name_test", Int64.class);
        this.database.getSeries().put("name_test", s);
        Series<Int64> result = this.database.getByName("name_test");
        ;

        assertEquals(result, s);

    }

    @Test
    public void getByNameTestInvalid() throws SeriesNotFoundException {
        assertThrows(SeriesNotFoundException.class, () -> this.database.getByName("Wrong_name"));
    }
}