package formatter.service;

import formatter.entity.ColumnSpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import formatter.entity.DataType;
import formatter.utils.FormatterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpecsHandler {

    private Map<String, List<ColumnSpec>> columnSpecsMap;
    private final String specsPath;

    private static final Logger logger = LoggerFactory.getLogger(SpecsHandler.class);

    public SpecsHandler(String specsPath) {
        columnSpecsMap = new HashMap<>();
        this.specsPath = specsPath;
    }

    public void loadSpecs() {
        try {
            Path specsDir = FormatterUtil.loadResourceRelativePath(specsPath);
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(specsDir)) {
                for (Path file : directoryStream) {
                    processSpecsFile(file);
                }
            }
        } catch (IOException | URISyntaxException exception) {
            logger.error("Failed to load specs directory {}: {}", specsPath, exception.getMessage());
        }

    }

    private void processSpecsFile(Path file) {
        String fileName = String.valueOf(file.getFileName()).replace(".csv", "");
        List<ColumnSpec> columnSpecsList = parseSpecsFile(file);
        columnSpecsMap.put(fileName, columnSpecsList);
    }

    private List<ColumnSpec> parseSpecsFile(Path file) {
        List<ColumnSpec> columnSpecsList = null;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            reader.readLine();
            String line;
            columnSpecsList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                try {
                    columnSpecsList.add(extractColumnSpecs(line));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
                    logger.warn("Malformed specification {} in : {} ", line, file);
                }
            }
        } catch (IOException exception) {
            logger.error("failed to load specification {} : ", file, exception);
        }
        return columnSpecsList;
    }

    private ColumnSpec extractColumnSpecs(String line) {
        String[] metaData = line.split(",");
        int width = Integer.parseInt(metaData[1]);
        DataType dataType = DataType.valueOf(metaData[2]);
        return new ColumnSpec(metaData[0], width, dataType);
    }

    public List<ColumnSpec> getSpecs(String objectId) {
        return columnSpecsMap.get(objectId);
    }
}
