package xls.format;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import xls.exception.OnErrorListener;
import xls.type.TypeFieldWriter;
import xls.type.TypeWriterFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XlsFormatParser<T> implements FormatParser {

    private Class<T> type;
    private String nameSheet;
    private OnErrorListener onErrorListener = null;
    private List<String> headers = new ArrayList<String>();
    private Boolean isFirstSheetParser = false;
    private Boolean isIterableSheetsParser = false;

    private XlsFormatParser() {}

    XlsFormatParser(Class<T> type, String nameSheet, OnErrorListener onErrorListener) {
        this.type = type;
        this.nameSheet = nameSheet;
        this.onErrorListener = onErrorListener;
    }

    static <T> XlsFormatParser newFirstSheetParserInstance(Class<T> type, OnErrorListener onErrorListener){
        XlsFormatParser parser = new XlsFormatParser<T>(type, onErrorListener);
        parser.isFirstSheetParser();
        return parser;
    }

    static <T> XlsFormatParser newIterableSheetsParser(Class<T> type, OnErrorListener onErrorListener){
        XlsFormatParser parser = new XlsFormatParser<T>(type, onErrorListener);
        parser.isIterableSheetsParser();
        return parser;
    }

    private XlsFormatParser(Class<T> type, OnErrorListener onErrorListener) {
        this.type = type;
        this.onErrorListener = onErrorListener;
    }

    private void isFirstSheetParser(){
        isFirstSheetParser = true;
    }

    private void isIterableSheetsParser(){
        isIterableSheetsParser = true;
    }

    @Override
    public List<T> parse(File file) {
        FileInputStream fileInputStream = createFileInputStreamFrom(file);
        Workbook excelBook = createHSSFWorkbookFrom(fileInputStream);
        return readBook(excelBook);
    }

    private FileInputStream createFileInputStreamFrom(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            onDispatchError(e);
            e.printStackTrace();
        }
        return fileInputStream;
    }

    private Workbook createHSSFWorkbookFrom(FileInputStream fileInputStream) {
        Workbook excelBook = null;
        try {
            excelBook = new HSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            onDispatchError(e);
            e.printStackTrace();
        } catch (OfficeXmlFileException e) {
            onDispatchError(e);
            e.printStackTrace();
        }
        return excelBook;
    }

    private List<T> readBook(Workbook book) {
        List<T> items = new ArrayList<T>();
        if (isIterableSheetsParser){
            for(int i = 0; i<book.getNumberOfSheets(); i++){
                items.addAll(readSheet(book.getSheetAt(i)));
            }
        } else {
            Sheet excelSheet = getSheetFrom(book);
            items.addAll(readSheet(excelSheet));
        }
        closeBook(book);
        return items;
    }

    private Sheet getSheetFrom(Workbook book) {
        if(isFirstSheetParser){
            return book.getSheetAt(0);
        } else {
            if (!nameSheet.isEmpty()) {
                return book.getSheet(nameSheet);
            } else {
                onDispatchError(new IllegalArgumentException("name sheet must not be empty"));
                return null;
            }
        }
    }

    private List<T> readSheet(Sheet sheet) {
        List<T> items = new ArrayList<T>();
        writeHeadersFrom(sheet);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            T instant = newInstant();

            for (int k = 0; k < row.getLastCellNum(); k++) {
                Cell cell = row.getCell(k);
                try {
                    Field field = instant.getClass().getDeclaredField(headers.get(k));
                    TypeFieldWriter typeW = TypeWriterFactory.<Class<T>>getTypeWriterBy(field);
                    typeW.write(field, cell, instant);
                } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                    onDispatchError(e);
                    e.printStackTrace();
                }
            }
            items.add(instant);
        }
        return items;
    }

    private void writeHeadersFrom(Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        for (Cell cell : firstRow) {
            headers.add(cell.getStringCellValue());
        }
    }

    private T newInstant() {
        T instant = null;
        try {
            instant = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            onDispatchError(e);
            e.printStackTrace();
        }
        return instant;
    }

    private void closeBook(Workbook book) {
        try {
            book.close();
        } catch (IOException e) {
            onDispatchError(e);
            e.printStackTrace();
        }
    }

    private void onDispatchError(Exception ex) {
        if (onErrorListener != null) onErrorListener.onError(ex);
    }
}
