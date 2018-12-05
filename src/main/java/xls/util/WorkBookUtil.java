package xls.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkBookUtil {
    public static final String XLS_BOOK_TYPE = "xls";
    public static final String XLSX_BOOK_TYPE = "xlsx";

    public static String getBookTypeBy(Workbook workbook) {
        if (workbook instanceof HSSFWorkbook){
            return XLS_BOOK_TYPE;
        } else if (workbook instanceof XSSFWorkbook){
            return XLSX_BOOK_TYPE;
        }
        return null;
    }
}
