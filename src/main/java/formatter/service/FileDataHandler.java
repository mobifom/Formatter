package formatter.service;

import formatter.entity.ColumnSpec;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static formatter.utils.FormatterUtil.loadResourceRelativePath;

public class FileDataHandler {

    private final SpecsHandler specsService;
    private final JSONService jsonHandler;
    private final String dataDirPath;
    private final String outputDirPath;

    private static final Logger logger = LoggerFactory.getLogger(FileDataHandler.class);


    public FileDataHandler(String specsPath, String dataDirPath, String outputDirPath) {
        this.specsService = new SpecsHandler(specsPath);
        this.jsonHandler = new JSONService();
        this.dataDirPath = dataDirPath;
        this.outputDirPath = outputDirPath;
    }

    // Main method to process all files in the data directory
    public void processFiles() throws IOException {
        specsService.loadSpecs();
        try {
            Path dataDir = loadResourceRelativePath(this.dataDirPath);
            Path outputDir = loadResourceRelativePath(this.outputDirPath);

            processFilesInDirectory(dataDir, outputDir);
        } catch (IOException | URISyntaxException exception) {
            logger.warn("Failed to locate data directory {}: {}", dataDirPath, exception.getMessage());
        }

    }

    private void processFilesInDirectory(Path dataDir, Path outputDir) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dataDir)) {
            for (Path dataFile : directoryStream) {
                processSingleDataFile(dataFile, outputDir);
            }
        }
    }

    // Process an individual data file
    private void processSingleDataFile(Path dataFile, Path outputDir) {
        String fileName = String.valueOf(dataFile.getFileName());
        String fileIdentifier = extractFileIdentifier(fileName);

        logger.info("Start processing file: {}", fileName);

        try {
            Path outputFile = outputDir.resolve(fileName.replace("txt", "ndjson"));
            generateOutput(dataFile, fileIdentifier, outputFile);
        } catch (IOException exception) {
            logger.warn("Failed to process data file {}: {}", dataFile, exception.getMessage());
        }

        logger.info("Processing for file: {} completed", fileName);
    }

    // Extract file identifier from file name
    private String extractFileIdentifier(String fileName) {
        String[] fileNameParts = fileName.split("_");
        return fileNameParts[0];
    }

    // Generate output for the data file in the desired format
    private void generateOutput(Path dataFile, String fileIdentifier, Path outputFile) throws IOException {
        List<ColumnSpec> columnSpecsList = getSpecs(fileIdentifier);
        if (columnSpecsList == null || columnSpecsList.isEmpty()) {
            logger.warn("No specs found for file identifier: {}", fileIdentifier);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(dataFile); BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

            processLines(reader, writer, columnSpecsList);
        } catch (IOException exception) {
            throw new IOException(String.format("Error writing to output file %s: %s", outputFile, exception.getMessage()));
        }

    }

    // Process each line from the input file and write the corresponding JSON output
    private void processLines(BufferedReader reader, BufferedWriter writer, List<ColumnSpec> columnSpecsList) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            JSONObject json = jsonHandler.parseLine(line, columnSpecsList);
            writer.write(json.toString());
            writer.newLine();
        }
    }

    private List<ColumnSpec> getSpecs(String fileIdentifier) {
        List<ColumnSpec> columnSpecsList = specsService.getSpecs(fileIdentifier);
        if (columnSpecsList == null) {
            logger.warn("Failed to load specs for {} ", fileIdentifier);
        }
        return columnSpecsList;
    }
}