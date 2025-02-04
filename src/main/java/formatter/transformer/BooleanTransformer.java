package formatter.transformer;

public class BooleanTransformer implements  DataTransformer<Boolean>{

    public static final DataTransformer<Boolean> booleanTransformer= new BooleanTransformer();

    @Override
    public Boolean transform(String value) {
        return value.trim().equals("1");
    }
}
