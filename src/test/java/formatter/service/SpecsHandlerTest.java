package formatter.service;

import formatter.entity.ColumnSpec;
import org.junit.jupiter.api.Test;

import formatter.entity.DataType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecsHandlerTest {

    @Test
    public void loadSpecs_validInput() {
        //arrange
        String specsDir = "testSpecs/valid/";

        SpecsHandler specsService = new SpecsHandler(specsDir);

        String fileName = "testformat1";
        ColumnSpec expectedNameColumn = new ColumnSpec("name", 10, DataType.TEXT);
        ColumnSpec expectedValidColumn = new ColumnSpec("valid", 1, DataType.BOOLEAN);
        ColumnSpec expectedCountColumn = new ColumnSpec("count", 3, DataType.INTEGER);

        //act
        specsService.loadSpecs();
        List<ColumnSpec> actualColumnSpectsList = specsService.getSpecs(fileName);
        ColumnSpec nameColumn = actualColumnSpectsList.stream().filter(columnSpec -> columnSpec.columnName().equals("name")).toList().get(0);
        ColumnSpec validColumn = actualColumnSpectsList.stream().filter(columnSpec -> columnSpec.columnName().equals("valid")).toList().get(0);
        ColumnSpec countColumn = actualColumnSpectsList.stream().filter(columnSpec -> columnSpec.columnName().equals("count")).toList().get(0);

        //assert
        assertEquals(expectedNameColumn, nameColumn);
        assertEquals(expectedValidColumn, validColumn);
        assertEquals(expectedCountColumn, countColumn);
    }

    @Test
    void loadSpecs_malformedLine() {
        //arrange
        String specsDir = "testSpecs/malformed/";
        String fileName = "testformat1";

        SpecsHandler specsService = new SpecsHandler(specsDir);

        //act
        specsService.loadSpecs();
        List<ColumnSpec> actualColumnSpectsList = specsService.getSpecs(fileName);

        //assert
        assertEquals(2, actualColumnSpectsList.size());
    }

    @Test
    void loadSpecs_empty() {
        //arrange
        String specsDir = "testSpecs/empty/";
        String fileName = "testformat1";
        SpecsHandler specsService = new SpecsHandler(specsDir);

        //act
        specsService.loadSpecs();
        List<ColumnSpec> actualColumnSpectsList = specsService.getSpecs(fileName);

        //assert
        assertTrue(actualColumnSpectsList.isEmpty());
    }
}