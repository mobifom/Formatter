package formatter.transformer;

import formatter.entity.DataType;
import org.junit.jupiter.api.Test;

import static formatter.transformer.DataTransformerFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class DataTransformerTest {
    @Test
    void testTextConverter() {
        var transformer = getTransformer(DataType.TEXT);
        assertEquals("Hello", transformer.transform("Hello    "));
        assertEquals("", transformer.transform("      "));
    }

    @Test
    void testBooleanConverter() {
        var transformer = getTransformer(DataType.BOOLEAN);
        assertEquals(true, transformer.transform("1"));
        assertEquals(false, transformer.transform("0"));
    }

    @Test
    void testIntegerConverter() {
        var transformer = getTransformer(DataType.INTEGER);

        assertEquals(123, transformer.transform("  123  "));
    }

    @Test
    void testIntegerConverter_invalidFormat() {
        var transformer = getTransformer(DataType.INTEGER);

        assertThrowsExactly(NumberFormatException.class, () -> {
            transformer.transform("invalid");
        });
    }
}
