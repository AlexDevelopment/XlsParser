package xls.format;

import java.io.File;
import java.util.List;

public interface FormatParser <T> {
    <T> List<T> parse(File file);
}
