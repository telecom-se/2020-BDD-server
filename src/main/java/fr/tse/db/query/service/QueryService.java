package fr.tse.db.query.service;


import fr.tse.db.query.error.BadQueryException;
import fr.tse.db.storage.data.*;
import fr.tse.db.storage.exception.SeriesNotFoundException;
import fr.tse.db.storage.exception.WrongSeriesValueTypeException;
import fr.tse.db.storage.request.Requests;
import fr.tse.db.storage.request.RequestsImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QueryService {

    public final List<String> typeList = Arrays.asList("int32", "int64", "float32");
    private final Requests request = new RequestsImpl();

    /**
     * This function is first called by the controller and delegates the parsing and then handle the result by calling
     * the storage functions depending on the conditions provided
     *
     * @param query the query to parse and proceed
     *
     * @return The hashMap with all the information
     */
    public HashMap<String, Object> handleQuery(String query) throws Exception {
        String[] commands = query.toLowerCase().split("\\s*;\\s*");
        HashMap<String, Object> result = this.parseQuery(commands[0]);
        HashMap<String, Object> resultMap = new HashMap<>();
        switch (result.get("action").toString()) {
            case "select": {
                String series = result.get("series").toString();
                String function = result.get("function").toString();
                List<String> operators = result.get("operators") != null ? (List<String>) result.get("operators") : null;
                List<Long> timestamps = result.get("timestamps") != null ? (List<Long>) result.get("timestamps") : null;
                String join = result.get("join") != null ? result.get("join").toString() : null;
                Series seriesResult;
                if (operators == null || operators.isEmpty() || timestamps == null || timestamps.isEmpty()) {
                    seriesResult = request.selectSeries(series);
                } else {
                    if (join == null) {
                        seriesResult = handleOperatorsCondition("select", operators.get(0), series, timestamps.get(0));
                    } else if (join.equals("and")) {
                        seriesResult = this.handleAndCondition("select", timestamps, operators, series);
                    } else {
                        seriesResult = this.handleOrCondition("select", timestamps, operators, series);
                    }
                }
                // Add all the series to response
                if (function.contains("all")) {
                    resultMap.put("values", seriesResult);
                }
                // Add minimum to response
                if (function.contains("min")) {
                    resultMap.put("min", request.min(seriesResult));
                }
                // Add maximum to response
                if (function.contains("max")) {
                    resultMap.put("max", request.max(seriesResult));
                }
                // Add average to response
                if (function.contains("average")) {
                    resultMap.put("average", request.average(seriesResult));
                }
                // Add sum to response
                if (function.contains("sum")) {
                    resultMap.put("sum", request.sum(seriesResult));
                }
                // Add count to response
                if (function.contains("count")) {
                    resultMap.put("count", request.count(seriesResult));
                }
                return resultMap;
            }
            case "create": {
                // Int32 type
                if (typeList.get(0).equals(result.get("type"))) {
                    request.createSeries((String) result.get("name"), Int32.class);
                    // Int64 type
                } else if (typeList.get(1).equals(result.get("type"))) {
                    request.createSeries((String) result.get("name"), Int64.class);
                    // Float32 type
                } else if (typeList.get(2).equals(result.get("type"))) {
                    request.createSeries((String) result.get("name"), Float32.class);
                }
                return null;
            }
            case "delete": {
                String series = result.get("series").toString();
                List<String> operators = result.get("operators") != null ? (List<String>) result.get("operators") : null;
                List<Long> timestamps = result.get("timestamps") != null ? (List<Long>) result.get("timestamps") : null;
                String join = result.get("join") != null ? result.get("join").toString() : null;
                // Check if conditions were provided
                if (operators == null || operators.isEmpty() || timestamps == null || timestamps.isEmpty()) {
                    request.deleteAllPoints(series);
                } else
                    // Check conditions the same way as the SELECT method
                    if (join == null) {
                        handleOperatorsCondition("delete", operators.get(0), series, timestamps.get(0));
                    } else if (join.equals("and")) {
                        handleAndCondition("delete", timestamps, operators, series);
                    } else {
                        handleOrCondition("delete", timestamps, operators, series);
                    }
                return null;
            }
            case "insert": {
                // Get series name
                String seriesName = (String) result.get("series");

                // Get series from name
                Series series = request.selectSeries(seriesName);

                // Get the list of pairs <Timestamp, Value>
                ArrayList<String[]> pairs = (ArrayList<String[]>) result.get("pairs");

                Series seriesTemp = new SeriesUnComp(seriesName, series.getType());

                try {
                    // For each pair in pairs list
                    for (String[] pair : pairs) {
                        // Get pair timestamp
                        Long timestamp = Long.parseLong(pair[0]);

                        // According to the value type
                        if (series.getType() == Int32.class) {
                            Int32 value = new Int32(Integer.parseInt(pair[1]));
                            seriesTemp.addPoint(timestamp, value);
                        } else if (series.getType() == Int64.class) {
                            Int64 value = new Int64(Long.parseLong(pair[1]));
                            seriesTemp.addPoint(timestamp, value);
                        } else if (series.getType() == Float32.class) {
                            Float32 value = new Float32(Float.parseFloat(pair[1]));
                            seriesTemp.addPoint(timestamp, value);
                        }
                    }
                } catch (NumberFormatException exc) {
                    throw new WrongSeriesValueTypeException(seriesTemp.getType(), seriesTemp.getType());
                }

                // Insert series in serie
                request.insertValue(seriesName, seriesTemp);

                return null;
            }
            case "show": {
                String series = result.get("series").toString();
                if (series.equals("all")) {
                    Map<String, String> data = request.showAllSeries();
                    ArrayList<HashMap<String, String>> returnedArray = new ArrayList<>();
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        String name = entry.getKey();
                        String value = entry.getValue();
                        HashMap<String, String> current = new HashMap<>();
                        current.put("name", name);
                        current.put("type", value);
                        returnedArray.add(current);
                    }
                    resultMap.put("info", returnedArray);
                } else {
                    // selection of series -> not in current version
                    // resultMap.put("info", request.showSeries(series));
                }

                return resultMap;
            }
            case "drop": {
                String series = result.get("series").toString();
                request.deleteSeries(series);
                resultMap.put("name", series);
                return resultMap;
            }
            default:
                return null;
        }
    }

    /**
     * Parse the query given in parameter
     *
     * @param command The query to parse     *
     *
     * @return a hash map with all the important information
     * @throws BadQueryException if the query string is ill-formed
     */
    public HashMap<String, Object> parseQuery(String command) throws BadQueryException {
        HashMap<String, Object> result = new HashMap<>();
        Pattern p = Pattern.compile("^(create|update|insert|select|delete|drop|show)");
        Matcher m = p.matcher(command);
        if (!m.find()) {
            throw new BadQueryException(BadQueryException.ERROR_MESSAGE_BAD_ACTION);
        }
        switch (m.group(1)) {
            case "select": {
                // Check if the select query is correct
                Pattern selectPattern = Pattern.compile("^\\s*select\\s+(.*?)\\s+from\\s+(.*?)(?:\\s+(?:where)\\s+(.*?))?$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if (!selectMatcher.find()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_SELECT_GENERAL);
                }
                // If no series is provided
                if (selectMatcher.group(1).isEmpty()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_SELECT_NO_AGGREGATION);
                }
                result.put("function", selectMatcher.group(1));
                for (int i = 0; i <= selectMatcher.groupCount(); i++) {
                    System.out.println(i + " " + selectMatcher.group(i));
                }
                String series = selectMatcher.group(2);
                if (series.isEmpty() || series.contains(" ")) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_SELECT_GENERAL);
                }
                result.put("series", series);
                System.out.println("Series " + series);
                // Check if conditions were provided
                if (selectMatcher.group(3) != null && !selectMatcher.group(3).isEmpty()) {
                    String conditions = selectMatcher.group(3);
                    HashMap<String, Object> whereConditions = parseConditions(conditions);
                    result.put("timestamps", whereConditions.get("timestamps"));
                    result.put("operators", whereConditions.get("operators"));
                    result.put("join", whereConditions.get("join"));
                }
                result.put("action", "select");
                break;
            }
            case "create": {
                Pattern selectPattern = Pattern.compile("^\\s*create\\s+(.+?)\\s+(.+?)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);

                // Check if regex matches the command and respect two entities
                if (!selectMatcher.find() || selectMatcher.groupCount() < 2) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_CREATE_GENERAL);
                }

                // Get the name and the type given in the command
                String name = selectMatcher.group(1);
                String type = selectMatcher.group(2);

                // Check if type exist
                if (!typeList.contains(type)) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_CREATE_IN_TYPE);
                }

                // Check the name and type syntax
                Pattern selectPatternSyntax = Pattern.compile("[a-zA-Z0-9_-]+");
                Matcher selectMatcherSyntaxName = selectPatternSyntax.matcher(name);

                if (!selectMatcherSyntaxName.matches()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_CREATE_IN_NAME_SPECIAL_CHARACTERS);
                }

                // Insert in hashmap the action, the series name and the type
                result.put("action", "create");
                result.put("name", name);
                result.put("type", type);
                break;
            }
            case "insert": {
                Pattern selectPattern = Pattern.compile("^insert\\s+into\\s+(.*?)\\s+values\\s+\\(\\((.*?)\\)\\)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if (!selectMatcher.find() || selectMatcher.group(1).isEmpty() || selectMatcher.group(2).isEmpty()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_INSERT_GENERAL);
                }
                result.put("action", "insert");
                String series = selectMatcher.group(1);
                result.put("series", series);
                String values = selectMatcher.group(2);
                String[] splittedValues = values.split("\\),\\s+\\(");
                ArrayList<String[]> pairs = new ArrayList<>();
                for (String splittedValue : splittedValues) {
                    String[] pair = splittedValue.split(",\\s*");
                    if (pair.length != 2) {
                        throw new BadQueryException(BadQueryException.ERROR_MESSAGE_INSERT_IN_VALUES);
                    }
                    try {
                        Integer.parseInt(pair[0]);
                        Float.parseFloat(pair[1]);
                    } catch (NumberFormatException nfe) {
                        throw new BadQueryException(BadQueryException.ERROR_MESSAGE_INSERT_IN_TYPE);
                    }
                    pairs.add(pair);
                }
                result.put("pairs", pairs);
                break;
            }
            case "delete": {
                Pattern deletePattern = Pattern.compile("^delete\\s+from\\s*(.*?)((?:\\s*where\\s*)(.*?))?$");
                Matcher deleteMatcher = deletePattern.matcher(command);
                if (!deleteMatcher.find()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_DELETE_GENERAL_1);
                }
                if (deleteMatcher.group(1).isEmpty()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_DELETE_IN_NAME);
                }
                if (deleteMatcher.group(2) != null && !deleteMatcher.group(2).isEmpty()) {
                    String conditions = deleteMatcher.group(2);
                    HashMap<String, Object> whereConditions = parseConditions(conditions);
                    result.put("timestamps", whereConditions.get("timestamps"));
                    result.put("operators", whereConditions.get("operators"));
                    result.put("join", whereConditions.get("join"));
                }
                result.put("series", deleteMatcher.group(1));
                result.put("action", "delete");
                break;
            }
            case "drop": {
                Pattern selectPattern = Pattern.compile("^drop\\s+(.*?)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if (!selectMatcher.find() || selectMatcher.group(1).isEmpty()) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_DELETE_GENERAL_1);
                }
                result.put("action", "drop");
                result.put("series", selectMatcher.group(1));
                break;
            }
            case "show": {
                Pattern selectPattern = Pattern.compile("^show\\s+(.*?)\\s*$");
                Matcher selectMatcher = selectPattern.matcher(command);
                if (!selectMatcher.find() || !selectMatcher.group(1).equals("all")) {
                    throw new BadQueryException(BadQueryException.ERROR_MESSAGE_SHOW_GENERAL);
                }
                result.put("action", "show");
                result.put("series", selectMatcher.group(1));
                break;
            }
            default: {
                throw new BadQueryException(BadQueryException.ERROR_MESSAGE_BAD_ACTION);
            }
        }
        return result;
    }

    public HashMap<String, Object> parseConditions(String conditions) throws BadQueryException {
        String[] splitConditions = conditions.split("(and|or)");
        if (splitConditions.length > 2) {
            throw new BadQueryException(BadQueryException.ERROR_MESSAGE_CONDITIONS_TOO_MANY);
        }
        if (splitConditions.length < 1) {
            throw new BadQueryException(BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL);
        }
        List<Long> timestamps = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        String joinCondition = null;
        if (conditions.contains("and")) {
            joinCondition = "and";
        }
        if (conditions.contains("or")) {
            joinCondition = "or";
        }
        for (String splitCondition : splitConditions) {
            Pattern p = Pattern.compile("timestamp\\s+(<|>|==|<=|>=)\\s+([0-9]+)\\s*");
            Matcher m = p.matcher(splitCondition);
            if (!m.find()) {
                throw new BadQueryException(BadQueryException.ERROR_MESSAGE_CONDITIONS_GENERAL);
            }
            operators.add(m.group(1));
            timestamps.add(Long.parseLong(m.group(2)));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("operators", operators);
        map.put("timestamps", timestamps);
        map.put("join", joinCondition);
        return map;
    }

    public Series handleOperatorsCondition(String action, String condition, String series, Long timestamp) {
        switch (condition) {
            case "<": {
                if (action.equals("select")) {
                    return this.request.selectLowerThanTimestamp(series, timestamp);
                } else {
                    request.deleteLowerThanTimestamp(series, timestamp);
                    return null;
                }
            }
            case "<=": {
                if (action.equals("select")) {
                    return request.selectLowerOrEqualThanTimestamp(series, timestamp);
                } else {
                    // return request.deleteHigherOrEqualThanTimestamp(series, timestamp);
                }
            }
            case ">": {
                if (action.equals("select")) {
                    return request.selectHigherThanTimestamp(series, timestamp);
                } else {
                    request.deleteHigherThanTimestamp(series, timestamp);
                    return null;
                }
            }
            case ">=": {
                if (action.equals("select")) {
                    return request.selectHigherOrEqualThanTimestamp(series, timestamp);
                } else {
                    request.deleteHigherOrEqualThanTimestamp(series, timestamp);
                    return null;
                }
            }
            case "==": {
                if (action.equals("select")) {
                    return request.selectByTimestamp(series, timestamp);
                } else {
                    request.deleteByTimestamp(series, timestamp);
                    return null;
                }
            }
            default: {
                return null;
            }
        }
    }

    private Series handleAndCondition(String action, List<Long> timestamps, List<String> operators, String series)
            throws BadQueryException, SeriesNotFoundException {
        Long time1 = timestamps.get(0);
        Long time2 = timestamps.get(1);
        String op1 = operators.get(0);
        String op2 = operators.get(1);
        if (time1.equals(time2)) {
            if (op1.equals("<") && op2.equals(">") || op1.equals(">") && op2.equals("<")) {
                throw new BadQueryException("Condition is not valid");
            }
            if (!op1.contains("=") && !op2.contains("=")) {
                return handleOperatorsCondition(action, op1, series, time1);
            } else {
                return handleOperatorsCondition(action, op1.substring(0, 1), series, time1);
            }
        } else if (time1 <= time2) {
            if (op1.equals("<") || op1.equals("<=") || op2.equals(">") || op2.equals(">=")) {
                throw new BadQueryException("Intervals do not overlap");
            }
            if (action.equals("select")) {
                return request.selectBetweenTimestampBothIncluded(series, time1, time2);
            } else {
                request.deleteBetweenTimestampBothIncluded(series, time1, time2);
                return null;
            }

        } else {
            if (op2.equals("<") || op2.equals("<=") || op1.equals(">") || op1.equals(">=")) {
                throw new BadQueryException("Intervals do not overlap");
            }
            if (action.equals("select")) {
                return request.selectBetweenTimestampBothIncluded(series, time2, time1);
            } else {
                request.deleteBetweenTimestampBothIncluded(series, time2, time1);
                return null;
            }

        }
    }

    private Series handleOrCondition(String action, List<Long> timestamps, List<String> operators, String series) throws SeriesNotFoundException {
        Long time1 = timestamps.get(0);
        Long time2 = timestamps.get(1);
        String op1 = operators.get(0).substring(0, 1);
        String op2 = operators.get(1).substring(0, 1);
        String fullOp1 = operators.get(0);
        String fullOp2 = operators.get(1);
        if (time1 > time2) {
            if (op2.equals(">")) {
                if (op1.equals("<")) {
                    if (action.equals("select")) {
                        return request.selectSeries(series);
                    } else {
                        request.deleteAllPoints(series);
                        return null;
                    }
                } else {
                    return handleOperatorsCondition(action, fullOp2, series, time2);
                }
            } else {
                if (op1.equals(">")) {
                    if (action.equals("select")) {
                        return request.selectNotInBetweenTimestampBothIncluded(series, time2, time1);
                    } else {
                        request.deleteNotInBetweenTimestampBothIncluded(series, time2, time1);
                        return null;
                    }
                } else {
                    return handleOperatorsCondition(action, fullOp1, series, time1);
                }
            }
        } else if (time1 < time2) {
            if (op1.equals(">")) {
                if (op2.equals("<")) {
                    if (action.equals("select")) {
                        return request.selectSeries(series);
                    } else {
                        request.deleteAllPoints(series);
                        return null;
                    }
                } else {
                    return handleOperatorsCondition(action, fullOp1, series, time1);
                }
            } else {
                if (op2.equals(">")) {
                    if (action.equals("select")) {
                        return request.selectNotInBetweenTimestampBothIncluded(series, time1, time2);
                    } else {
                        request.deleteNotInBetweenTimestampBothIncluded(series, time1, time2);
                        return null;
                    }
                } else {
                    return handleOperatorsCondition(action, fullOp2, series, time2);
                }
            }
        } else {
            // Both timestamps are equal, we only have to check if the conditions are different
            if (op1.equals(op2)) {
                if (fullOp1.length() > fullOp2.length()) {
                    return handleOperatorsCondition(action, fullOp1, series, time1);
                } else {
                    return handleOperatorsCondition(action, fullOp2, series, time2);
                }
            } else {
                // Conditions are different, we check if one of them have the symbol = to return the whole series,
                // or just every point but the provided timestamp.
                if (op1.equals("=")) {
                    return handleOperatorsCondition(action, op2 + "=", series, time2);
                } else if (op2.equals("=")) {
                    return handleOperatorsCondition(action, op1 + "=", series, time1);
                } else {
                    if (fullOp1.length() > 1 || fullOp2.length() > 1) {
                        if (action.equals("select")) {
                            return request.selectSeries(series);
                        } else {
                            request.deleteAllPoints(series);
                            return null;
                        }
                    } else {
                        if (action.equals("select")) {
                            return request.selectNotInBetweenTimestampBothIncluded(series, time1 - 1, time2 + 1);
                        } else {
                            request.deleteNotInBetweenTimestampBothIncluded(series, time1 - 1, time2 + 1);
                            return null;
                        }
                    }
                }
            }
        }
    }
}
