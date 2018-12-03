package xls.type;

import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Field;

public interface TypeFieldWriter {
    <T> void write(Field field, Cell cell, T instant) throws IllegalAccessException, InstantiationException;
}
