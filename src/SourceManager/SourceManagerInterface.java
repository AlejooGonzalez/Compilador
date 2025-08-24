package SourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SourceManagerInterface {
    void open(String filePath) throws FileNotFoundException;

    void close() throws IOException;

    char getNextChar() throws IOException;

    int getLineNumber();

    public static final char END_OF_FILE = (char) 26;
}
