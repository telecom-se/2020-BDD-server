package fr.tse.db.storage.service;

import fr.tse.db.storage.domain.Series;
import fr.tse.db.storage.error.BadQueryException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QueryService {

    private Set<String> actions = new HashSet<>();
    QueryService() {
        this.actions.add("CREATE");
        this.actions.add("INSERT");
        this.actions.add("SELECT");
}

    public List<Series> parseQuery(String query) throws BadQueryException {
        String[] commands = query.split(";");
        for (String command: commands) {
            String[] word = command.split("\\s+");
            switch (word[0]) {
                case "SELECT": {
                    if(word.length <= 4) {
                        throw new BadQueryException("Error in query");
                    }
                }
                case "CREATE": {

                }
                case "INSERT": {

                }
                default: {
                    throw new BadQueryException("Error in query");
                }
            }
        }

        List<Series> series =  new ArrayList<>();
        series.add(new Series((long) 12, 1));
        return series;
    }
}
