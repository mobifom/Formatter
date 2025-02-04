package formatter.transformer;

public interface DataTransformer<T> {
    T transform(String value);
}
