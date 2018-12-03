package xls.type;

import org.apache.poi.ss.usermodel.Cell;
import xls.adapter.TypeAdapter;
import xls.adapter.XlsAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

class StringTypeFieldWriter<T> implements TypeFieldWriter {

    private static StringTypeFieldWriter stringTypeFieldWriter;
    private TypeAdapter typeAdapter = null;

    private StringTypeFieldWriter() {
    }

    static <T> StringTypeFieldWriter getInstance() {
        if (stringTypeFieldWriter == null) {
            stringTypeFieldWriter = new StringTypeFieldWriter<T>();
        }
        return stringTypeFieldWriter;
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
                writeFormulaToField(field, instant, cell);
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
            field.set(instant, typeAdapter.write(cell.getStringCellValue()));
        } else {
            field.set(instant, cell.getStringCellValue());
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
            field.set(instant, typeAdapter.write(""));
        } else {
            field.set(instant, "");
        }
    }

    private <T> void writeErrorToField(Field field, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(""));
        } else {
            field.set(instant, "");
        }
    }

    private <T> void writeBooleanToField(Field field, T instant, Cell cell) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(((Boolean) cell.getBooleanCellValue()).toString()));
        } else {
            field.set(instant, ((Boolean) cell.getBooleanCellValue()).toString());
        }
    }

    private <T> void writeFormulaToField(Field field, T instant, Cell cell) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(cell.getCellFormula()));
        } else {
            field.set(instant, cell.getCellFormula());
        }
    }

    private <T> void writeNumericToField(Field field, T instant, Cell cell) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, typeAdapter.write(((Double) cell.getNumericCellValue()).toString()));
        } else {
            field.set(instant, ((Double) cell.getNumericCellValue()).toString());
        }
    }
}
