package formatter.service;

import formatter.entity.ColumnSpec;
import formatter.entity.DataType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONServiceTest {

    JSONService jsonHandler;

    @BeforeEach
    public void setUp() {
        jsonHandler = new JSONService();
    }

    @Test
    public void parseLine_validData() {
        //arrange
        String fileIdentifier = "testformat1";
        String fileNameWithoutExtension = "testformat1_2021-07-06";

        List<ColumnSpec> columnSpecs = new ArrayList<>();
        columnSpecs.add(new ColumnSpec("name", 10, DataType.TEXT));
        columnSpecs.add(new ColumnSpec("valid", 1, DataType.BOOLEAN));
        columnSpecs.add(new ColumnSpec("count", 3, DataType.INTEGER));

        String line = "Diabetes  1  1";

        //act
        JSONObject json = jsonHandler.parseLine(line, columnSpecs);
        //assert
        assertEquals("Diabetes", json.get("name"));
        assertEquals(true, json.get("valid"));
        assertEquals(1, json.get("count"));
    }

    @Test
    public void parseLine_invalidData() {
        //arrange
        String fileIdentifier = "testformat1";
        String fileNameWithoutExtension = "testformat1_2021-07-06";

        List<ColumnSpec> columnSpecs = new ArrayList<>();
        columnSpecs.add(new ColumnSpec("name", 10, DataType.TEXT));
        columnSpecs.add(new ColumnSpec("valid", 1, DataType.BOOLEAN));
        columnSpecs.add(new ColumnSpec("count", 3, DataType.INTEGER));

        String line = "Diabetes  0  o";

        //act
        JSONObject json = jsonHandler.parseLine(line, columnSpecs);
        //assert
        assertEquals("Diabetes", json.get("name"));
        assertEquals(false, json.get("valid"));
        assertEquals("null", json.get("count"));

    }

}