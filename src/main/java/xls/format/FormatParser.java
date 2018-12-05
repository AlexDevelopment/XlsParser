package xls.format;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.List;

public interface FormatParser <T> {
    <T> List<T> parse(Workbook workBook);
}
