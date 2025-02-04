package formatter.transformer;

public class TextTransFormer implements DataTransformer<String> {

    public static final DataTransformer<String> textTransFormer = new TextTransFormer();

    @Override
    public String transform(String value) {
        return value.trim();
    }
}
