package formatter.service;

import formatter.entity.ColumnSpec;
import formatter.transformer.DataTransformerFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONService {

    private static final Logger logger = LoggerFactory.getLogger(JSONService.class);

    public JSONObject parseLine(String line, List<ColumnSpec> columnSpecs) {
        Map<String, Object> orderedMap = new LinkedHashMap<>();
        int currentIndex = 0;

        for (ColumnSpec column : columnSpecs) {
            int width = column.width();
            Object valueObj = extractColumnValue(line, column, currentIndex, width);
            currentIndex += width;

            orderedMap.put(column.columnName(), valueObj != null ? valueObj : JSONObject.NULL.toString());
        }
        return new JSONObject(orderedMap);
    }

    private static Object extractColumnValue(String line, ColumnSpec column, int currentIndex, int width) {
        try {
            String value = line.substring(currentIndex, currentIndex + width);
            return DataTransformerFactory
                    .getTransformer(column.dataType())
                    .transform(value);
        } catch (IndexOutOfBoundsException exception) {
            logger.warn("Line format error for column {}: {}", column.columnName(), line);
        } catch (NumberFormatException exception) {
            logger.warn("Failed to process column {} in [ {} ]: {}", column.columnName(), line, exception.getMessage());
        }
        return null;
    }

}