package xls.type;

import org.apache.poi.ss.usermodel.Cell;
import xls.adapter.TypeAdapter;
import xls.adapter.XlsAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class IntegerTypeFieldWriter <T> implements TypeFieldWriter {

    private static IntegerTypeFieldWriter typeFieldWriter;
    private TypeAdapter typeAdapter = null;

    private IntegerTypeFieldWriter() {
    }

    static <T> IntegerTypeFieldWriter<T> getInstance() {
        if (typeFieldWriter == null) {
            typeFieldWriter = new IntegerTypeFieldWriter<T>();
        }
        return typeFieldWriter;
    }

    @Override
    public <T> void write(Field field, Cell cell, T instant) throws IllegalAccessException, InstantiationException {
        field.setAccessible(true);
        changeAnnotation(field);

        switch (cell.getCellType()) {
            case STRING:
                writeStringToField(field, cell, instant);
                break;
            case _NONE:
                writeNoneToField(field, instant);
                break;
            case BLANK:
                writeBlankToField(field, instant);
                break;
            case ERROR:
                writeErrorToField(field, instant);
                break;
            case BOOLEAN:
                writeBooleanToField(field, instant, cell);
                break;
            case FORMULA:
                writeFormulaToField(field, instant);
                break;
            case NUMERIC:
                writeNumericToField(field, instant, cell);
                break;
            default:
                field.set(instant, "");
                break;
        }
        typeAdapter = null;
    }

    private void changeAnnotation(Field field) throws IllegalAccessException, InstantiationException {
        Annotation annotation = field.getAnnotation(XlsAdapter.class);
        if (annotation != null) {
            typeAdapter = field.getAnnotation(XlsAdapter.class).handler().newInstance();
        }

    }

    private <T> void writeStringToField(Field field, Cell cell, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(Integer.parseInt(cell.getStringCellValue())));
        } else {
            field.set(instant, Integer.parseInt(cell.getStringCellValue()));
        }
    }

    private <T> void writeNoneToField(Field field, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(null));
        } else {
            field.set(instant, null);
        }
    }

    private <T> void writeBlankToField(Field field, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(null));
        } else {
            field.set(instant, null);
        }
    }

    private <T> void writeErrorToField(Field field, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(null));
        } else {
            field.set(instant, null);
        }
    }

    private <T> void writeBooleanToField(Field field, T instant, Cell cell) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(getIntByBoolean(cell.getBooleanCellValue())));
        } else {
            field.set(instant, getIntByBoolean(cell.getBooleanCellValue()));
        }
    }

    private int getIntByBoolean(Boolean value){
        if(value){
            return 0;
        } else {
            return 1;
        }
    }

    private <T> void writeFormulaToField(Field field, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, null);
        } else {
            field.set(instant, null);
        }
    }

    private <T> void writeNumericToField(Field field, T instant, Cell cell) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write((int)cell.getNumericCellValue()));
        } else {
            field.set(instant, (int)cell.getNumericCellValue());
        }
    }
}
