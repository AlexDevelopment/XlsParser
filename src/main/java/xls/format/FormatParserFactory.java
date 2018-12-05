package xls.format;

import xls.exception.OnErrorListener;

import static xls.util.WorkBookUtil.XLSX_BOOK_TYPE;

public class FormatParserFactory {

    public static <T> FormatParser getFormatParser(String bookType, Class<T> type, String nameSheet, OnErrorListener onErrorListener){
        if (bookType.equals(XLSX_BOOK_TYPE)){
            return  new XlsxFormatParser<T>(type, nameSheet, onErrorListener);
        } else {
            return new XlsFormatParser<T>(type, nameSheet, onErrorListener);
        }
    }

    public static <T> FormatParser getFirstSheetFormatParser(String bookType, Class<T> type, OnErrorListener onErrorListener){
        if (bookType.equals(XLSX_BOOK_TYPE)){
            return XlsxFormatParser.newFirstSheetParserInstance(type, onErrorListener);
        } else {
            return XlsFormatParser.newFirstSheetParserInstance(type, onErrorListener);
        }
    }

    public static <T> FormatParser getIterableSheetsFormatParser(String bookType, Class<T> type, OnErrorListener onErrorListener){
        if (bookType.equals(XLSX_BOOK_TYPE)){
            return XlsxFormatParser.newIterableSheetsParserInstance(type, onErrorListener);
        } else {
            return XlsFormatParser.newIterableSheetsParser(type, onErrorListener);
        }
    }

}
