package formatter.main;

import formatter.service.FileDataHandler;
import formatter.utils.FormatterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileFormatterMain {
    private static final Logger logger = LoggerFactory.getLogger(FileFormatterMain.class);

    public static void main(String[] args) {
        String specsPath = System.getenv().getOrDefault("SPECS_PATH", "specs/");
        String dataPath = System.getenv().getOrDefault("DATA_PATH", "data/");
        String outputPath = System.getenv().getOrDefault("OUTPUT_PATH", "output/");
        boolean validDirectoryPaths = isValidDirectoryPaths(specsPath, dataPath);

        if (!validDirectoryPaths) {
            logger.error("Invalid directory paths: Specs Path - {}, Data Path - {}", specsPath, dataPath);
            System.exit(1);
        }

        try {
            new FileDataHandler(specsPath, dataPath, outputPath).processFiles();
        } catch (
                IOException ioException) {
            logger.error("IO error while processing files", ioException);
            System.exit(1);
        } catch (
                Exception exception) {
            logger.error("An unexpected error occurred", exception);
            System.exit(1);
        }

        logger.info("All files processed successfully.");
    }

    private static boolean isValidDirectoryPaths(String specsPath, String dataPath) {
        boolean validDirPath = true;
        try {
            if (FormatterUtil.isValidDirectory(specsPath) || FormatterUtil.isValidDirectory(dataPath)) {
                validDirPath = false;
            }
        } catch (URISyntaxException e) {
            validDirPath = false;
        }
        return validDirPath;
    }
}
