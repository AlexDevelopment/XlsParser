package xls.format;

import xls.exception.OnErrorListener;

public class FormatParserFactory {

    private static final String XLSX_FORMAT = "xlsx";

    public static <T> FormatParser getFormatParser(String fileExtension, Class<T> type, String nameSheet, OnErrorListener onErrorListener){
        if (fileExtension.compareToIgnoreCase(XLSX_FORMAT) == 0){
            return  new XlsxFormatParser<T>(type, nameSheet, onErrorListener);
        } else {
            return new XlsFormatParser<T>(type, nameSheet, onErrorListener);
        }
    }

    public static <T> FormatParser getFirstSheetFormatParser(String fileExtension, Class<T> type, OnErrorListener onErrorListener){
        if (fileExtension.compareToIgnoreCase(XLSX_FORMAT) == 0){
            return XlsxFormatParser.newFirstSheetParserInstance(type, onErrorListener);
        } else {
            return XlsFormatParser.newFirstSheetParserInstance(type, onErrorListener);
        }
    }

    public static <T> FormatParser getIterableSheetsFormatParser(String fileExtension, Class<T> type, OnErrorListener onErrorListener){
        if (fileExtension.compareToIgnoreCase(XLSX_FORMAT) == 0){
            return XlsxFormatParser.newIterableSheetsParserInstance(type, onErrorListener);
        } else {
            return XlsFormatParser.newIterableSheetsParser(type, onErrorListener);
        }
    }

}
