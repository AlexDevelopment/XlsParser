package xls.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import xls.adapter.TypeAdapter;
import xls.adapter.XlsAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class DateTypeFieldWriter <T> implements TypeFieldWriter {

    private static DateTypeFieldWriter typeFieldWriter;
    private TypeAdapter typeAdapter = null;

    private DateTypeFieldWriter() {
    }

    static <T> DateTypeFieldWriter<T> getInstance() {
        if (typeFieldWriter == null) {
            typeFieldWriter = new DateTypeFieldWriter<T>();
        }
        return typeFieldWriter;
    }

    @Override
    public <T> void write(Field field, Cell cell, T instant) throws IllegalAccessException, InstantiationException {
        field.setAccessible(true);
        changeAnnotation(field);

        switch (cell.getCellType()) {
            case STRING:
                writeStringToField();
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
                writeBooleanToField();
                break;
            case FORMULA:
                writeFormulaToField();
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

    private <T> void writeStringToField() throws IllegalAccessException {
        throw new IllegalAccessException();
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

    private <T> void writeBooleanToField() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private <T> void writeFormulaToField() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private <T> void writeNumericToField(Field field, T instant, Cell cell) throws IllegalAccessException {
            if (DateUtil.isCellDateFormatted(cell))
                if (typeAdapter != null) {
                    field.set(instant, typeAdapter.write(cell.getDateCellValue()));
                } else {
                    field.set(instant, cell.getDateCellValue());
                }
            else
                throw new IllegalAccessException();
    }
}
