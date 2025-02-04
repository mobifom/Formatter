package formatter.transformer;

public class IntegerTransformer implements DataTransformer<Integer> {

    public static final DataTransformer<Integer> integerTransformer = new IntegerTransformer();

    @Override
    public Integer transform(String value) throws NumberFormatException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid integer format: " + value);
        }
    }

}
