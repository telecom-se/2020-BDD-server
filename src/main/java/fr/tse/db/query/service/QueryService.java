package fr.tse.db.query.service;

import fr.tse.db.query.domain.Series;
import fr.tse.db.query.error.BadQueryException;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QueryService {

    private Set<String> actions = new HashSet<>();

    QueryService() {
        this.actions.add("CREATE");
        this.actions.add("INSERT");
        this.actions.add("SELECT");
    }

    public List<Series> parseQuery(String query) throws BadQueryException {
        String[] commands = query.toLowerCase().split("\\s*;\\s*");
        System.out.println(commands.length + " command(s) found");
        for (String command : commands) {
            Pattern p = Pattern.compile("^(create|update|select|delete)");
            Matcher m = p.matcher(command);
            if(!m.find()) {
                throw new BadQueryException("Bad action provided");
            }
            switch (m.group(0)) {
                case "select": {
                    System.out.println("SELECT");
                    // Check if the select query is correct
                    Pattern selectPattern = Pattern.compile("^\\s*select\\s*(.*?)\\s*from\\s*(.*?)(?:(?:\\s*where\\s*)(.*?))?$");
                    Matcher selectMatcher = selectPattern.matcher(command);
                    if(!selectMatcher.find() || selectMatcher.groupCount() == 0) {
                        throw new BadQueryException("Error in SELECT query");
                    };
                    // If no series is provided
                    if(selectMatcher.group(1).isEmpty()) {
                        throw new BadQueryException("Error in SELECT query");
                    }
                    for(int i = 0; i <= selectMatcher.groupCount(); i++) {
                        System.out.println(i + selectMatcher.group(i));
                    }
                    String series = selectMatcher.group(2);
                    System.out.println("Series " + series);
                    // Check if conditions were provided
                    if(!selectMatcher.group(2).isEmpty()) {
                        String conditions = selectMatcher.group(3);
                        int count = parseConditions(conditions);
                        System.out.println(count + " condition(s)");
                    }
                    break;
                }
                case "create": {
                    Pattern selectPattern = Pattern.compile("^create\\s*(.*?)\\s*from\\s*(.*?)((?:\\s*where\\s*)(.*?))?$");
                    Matcher selectMatcher = selectPattern.matcher(command);
                    if(selectMatcher.find()) {
                        throw new BadQueryException("Error in query");
                    };
                    break;
                }
                case "insert": {
                    Pattern selectPattern = Pattern.compile("^insert\\s*(.*?)\\s*from\\s*(.*?)((?:\\s*where\\s*)(.*?))?$");
                    Matcher selectMatcher = selectPattern.matcher(command);
                    if(selectMatcher.find()) {
                        throw new BadQueryException("Error in query");
                    };
                    break;
                }
                case "delete": {
                    Pattern selectPattern = Pattern.compile("^delete\\s*(.*?)\\s*from\\s*(.*?)((?:\\s*where\\s*)(.*?))?$");
                    Matcher selectMatcher = selectPattern.matcher(command);
                    if(selectMatcher.find()) {
                        throw new BadQueryException("Error in query");
                    };

                    break;
                }
                default: {
                    throw new BadQueryException("Error in query action");
                }
            }
        }

        List<Series> series = new ArrayList<>();
        series.add(new Series((long) 12, 1));
        return series;
    }

    public int parseConditions(String conditions) {
        System.out.println(conditions);
        Pattern p = Pattern.compile("(timestamp|value)\\s+(<|>|=|<=|>=)\\s+([0-9]+)\\s*(and|or)?\\s*");
        Matcher m = p.matcher(conditions);
        int count = 0;
        while(m.find()) {
            count++;
        }
        return count;
    }
}
