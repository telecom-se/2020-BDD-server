package fr.tse.db.query.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import fr.tse.db.query.domain.Series;
import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.query.error.SeriesAlreadyExistsQueryException;
import fr.tse.db.storage.data.Float32;
import fr.tse.db.storage.data.Int32;
import fr.tse.db.storage.data.Int64;
import fr.tse.db.storage.exception.SeriesAlreadyExistsException;
import fr.tse.db.storage.request.Requests;
import fr.tse.db.storage.request.RequestsImpl;

@Service
public class QueryService {

    private Set<String> actions = new HashSet<>();
    
    // TODO A mettre dans un fichier constante
    public List<String> typeList = Arrays.asList(new String[]{"int32", "int64", "float32"});
    
    
    QueryService() {
        this.actions.add("CREATE");
        this.actions.add("INSERT");
        this.actions.add("SELECT");
    }

    public List handleQuery(String query) throws BadQueryException, SeriesAlreadyExistsQueryException {
        String[] commands = query.toLowerCase().split("\\s*;\\s*");
        System.out.println(commands.length + " command(s) found");
        HashMap<String, Object> result = this.parseQuery(commands[0]);
        List<Series> series = new ArrayList<>();
        
        Requests requests = new RequestsImpl();
        		
        switch(result.get("action").toString()) {
            case "select": {

                break;
            }
            case "create": {
            	try {
                	// Int32 type
                	if (typeList.get(0).equals((String) result.get("type"))) {
                		requests.createSeries((String) result.get("name"), Int32.class);
                	// Int64 type
                    }else if(typeList.get(1).equals((String) result.get("type"))) {
                    	requests.createSeries((String) result.get("name"), Int64.class); 
                    // Float32 type
                    }else if(typeList.get(2).equals((String) result.get("type"))) {
                    	requests.createSeries((String) result.get("name"), Float32.class);
                    }
            	} catch (SeriesAlreadyExistsException seriesAlreadyExistsException) {
            		throw new SeriesAlreadyExistsQueryException("Serie already exist");
            	}
                return null;
            }
            case "delete": {
                return null;
            }
            case "insert": {
                return null;
            }
            default: return null;
        }
        return series;
    }

    public HashMap<String, Object> parseQuery(String command) throws BadQueryException {
        HashMap<String, Object> result = new HashMap<>();
        Pattern p = Pattern.compile("^(create|update|select|delete|show)");
        Matcher m = p.matcher(command);
        if(!m.find()) {
            throw new BadQueryException("Bad action provided");
        }
        switch (m.group(1)) {
            case "select": {
                // Check if the select query is correct
                Pattern selectPattern = Pattern.compile("^\\s*select\\s+(.*?)\\s+from\\s+(.*?)(?:(?:\\s+where\\s+)(.*?))?$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if(!selectMatcher.find()) {
                    throw new BadQueryException("Error in SELECT query");
                };
                // If no series is provided
                if(selectMatcher.group(1).isEmpty()) {
                    throw new BadQueryException("Error in SELECT query: No timestamp provided");
                }
                result.put("function", selectMatcher.group(1));
                for(int i = 0; i <= selectMatcher.groupCount(); i++) {
                    System.out.println(i + " " + selectMatcher.group(i));
                }
                String series = selectMatcher.group(2);
                result.put("series", series);
                System.out.println("Series " + series);
                // Check if conditions were provided
                if(selectMatcher.group(3) != null) {
                    String conditions = selectMatcher.group(3);
                    int count = parseConditions(conditions);
                    System.out.println(count + " condition(s)");
                }
                break;
            }
            case "create": {
                Pattern selectPattern = Pattern.compile("^\\s*create\\s+(.+?)\\s+(.+?)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);

                // Check if regex matchs the command and respect two entities
                if(!selectMatcher.find() || selectMatcher.groupCount() < 2) {
                    throw new BadQueryException("Error in CREATE query");
                }

                // Get the name and the type given in the command
                String name = selectMatcher.group(1);
                String type = selectMatcher.group(2);

                // Check name or type contains one or more spaces
                if(name.contains(" ")) {
                    throw new BadQueryException("Error in CREATE query (space in name)");
                }
                
                // Check if type exist
                if (!typeList.contains(type)) {
                    throw new BadQueryException("Error in CREATE query (type not exist)");
                }

                // Check the name and type synthax
                Pattern selectPatternSynthax = Pattern.compile("[a-zA-Z0-9_-]+");
                Matcher selectMatcherSynthaxName = selectPatternSynthax.matcher(name);

                if (!selectMatcherSynthaxName.matches()) {
                    throw new BadQueryException("Error in CREATE query (special characters not allowed in name)");
                }
                
                // Insert in hashmap the action, the serie name and the type
                result.put("action", "create");
                result.put("name", name);
                result.put("type", type);

                break;
            }
            case "insert": {
                Pattern selectPattern = Pattern.compile("^insert\\s+into\\s+(.*?)\\s+values\\s+\\(\\((.*?)\\)\\)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if(!selectMatcher.find() || selectMatcher.group(1).isEmpty() || selectMatcher.group(2).isEmpty()) {
                    throw new BadQueryException("Error in INSERT query");
                };
                result.put("action", "insert");
                String series = selectMatcher.group(1);
                result.put("series", series);
                String values = selectMatcher.group(2);
                String[] splitedValues = values.split("\\)\\,\\s+\\(");
                ArrayList<String[]> pairs = new ArrayList<>();
                for (String splitedValue : splitedValues) {
                    String[] pair = splitedValue.split(",\\s*");
                    if (pair.length != 2) {
                        throw new BadQueryException("Error in inserted values");
                    }
                    try {
                        Integer.parseInt(pair[0]);
                        Float.parseFloat(pair[1]);
                    } catch (NumberFormatException nfe) {
                        throw new BadQueryException("Wrong type provided for insert");
                    }
                    pairs.add(pair);
                }
                result.put("pairs", pairs);
                break;
            }
            case "delete": {
                Pattern selectPattern = Pattern.compile("^delete\\s*(.*?)\\s*from\\s*(.*?)((?:\\s*where\\s*)(.*?))?$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if(selectMatcher.find()) {
                    throw new BadQueryException("Error in query");
                };
                result.put("action", "delete");
                break;
            }
            case "show": {
                Pattern selectPattern = Pattern.compile("^show\\s+(.*?)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if(!selectMatcher.find() || selectMatcher.group(1).isEmpty()) {
                    throw new BadQueryException("Error in SHOW query");
                };
                result.put("action", "show");
                result.put("series", selectMatcher.group(1));
                break;
            }
            default: {
                throw new BadQueryException("Error in query action");
            }
        }
        return result;
    }

    public int parseConditions(String conditions) {
        Pattern p = Pattern.compile("(timestamp|value)\\s+(<|>|=|<=|>=)\\s+([0-9]+)\\s*(and|or)?\\s*");
        Matcher m = p.matcher(conditions);
        int count = 0;
        while(m.find()) {
            count++;
        }
        return count;
    }
}
