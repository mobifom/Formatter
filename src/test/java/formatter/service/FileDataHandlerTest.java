package formatter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

class FileDataHandlerTest {

    FileDataHandler dataHandler;

    @BeforeEach
    void setUp() {
        String dataPath = "data/";
        String specsPath = "specs/";
        String outputPath = "output/";

        dataHandler = Mockito.spy(new FileDataHandler(specsPath, dataPath, outputPath));
    }

    @Test
    void loadData_validInput() throws IOException {
        //arrang

        //act
        dataHandler.processFiles();

        //assert
        verify(dataHandler, atLeastOnce()).processFiles();
    }

}