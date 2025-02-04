package formatter.transformer;

import formatter.entity.DataType;

import java.util.HashMap;
import java.util.Map;

public class DataTransformerFactory {

    public static Map<DataType, DataTransformer> transformerMap;;

    static {
        transformerMap = new HashMap<>();
        transformerMap.put(DataType.TEXT, TextTransFormer.textTransFormer);
        transformerMap.put(DataType.BOOLEAN, BooleanTransformer.booleanTransformer);
        transformerMap.put(DataType.INTEGER, IntegerTransformer.integerTransformer);
    }

    public static DataTransformer getTransformer(DataType dataType) {
        return transformerMap.get(dataType);
    }
}
