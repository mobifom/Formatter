package formatter.utils;

import formatter.service.SpecsHandler;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FormatterUtil {
    public static Path loadResourceRelativePath(String relativeResourcePath) throws URISyntaxException {
        URL relativeResourceURL = SpecsHandler.class.getClassLoader().getResource(relativeResourcePath);
        return relativeResourceURL !=null ? Paths.get(relativeResourceURL.toURI()): null;
    }

    public static boolean isValidDirectory(String directoryPath) throws URISyntaxException {
        Path dataDir = loadResourceRelativePath(directoryPath);
        return directoryPath != null && new File(directoryPath).isDirectory();
    }
}
