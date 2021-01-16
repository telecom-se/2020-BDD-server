package fr.tse.db.query.utils;

import fr.tse.db.storage.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;

/**
 * Class used to populate the database
 */
@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    @Profile("test_query_controller")
    CommandLineRunner initTestDatabase() {
        return args -> {
            initSeries();
        };
    }

    private void initSeries() {
        this.clearSeries();
        /*
        create 3 series :
        _"seriesint32" type Int32
        _"seriesInt64" type Int64
        _"seriesFloat32" type Float32
         */
        Series seriesInt32 = new SeriesUnComp("seriesint32", Int32.class);
        Series seriesInt64 = new SeriesUnComp("seriesint64", Int64.class);
        Series seriesFloat32 = new SeriesUnComp("seriesfloat32", Float32.class);
        // Populate those series with 5 datas :
        seriesInt32.addPoint(1L, new Int32(2));
        seriesInt32.addPoint(2L, new Int32(4));
        seriesInt32.addPoint(3L, new Int32(12));
        seriesInt32.addPoint(4L, new Int32(48));
        seriesInt32.addPoint(5L, new Int32(240));

        seriesInt64.addPoint(1L, new Int64(240L));
        seriesInt64.addPoint(2L, new Int64(480L));
        seriesInt64.addPoint(3L, new Int64(1440L));
        seriesInt64.addPoint(4L, new Int64(5760L));
        seriesInt64.addPoint(5L, new Int64(28800L));

        seriesFloat32.addPoint(1L, new Float32(2.1f));
        seriesFloat32.addPoint(2L, new Float32(4.2f));
        seriesFloat32.addPoint(3L, new Float32(12.6f));
        seriesFloat32.addPoint(4L, new Float32(48.24f));
        seriesFloat32.addPoint(5L, new Float32(240.120f));

        //save those series into database
        DataBase db = DataBase.getInstance();
        db.addSeries(seriesInt32);
        db.addSeries(seriesInt64);
        db.addSeries(seriesFloat32);
        log.info("Series populated");
    }

    private void clearSeries() {
        DataBase database = DataBase.getInstance();
        ArrayList<String> seriesNameList = new ArrayList<>(database.getSeries().keySet());
        for (String name : seriesNameList) {
            database.deleteSeries(name);
        }
    }
}

