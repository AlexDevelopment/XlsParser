package xls.type;

import org.apache.poi.ss.usermodel.Cell;
import xls.adapter.TypeAdapter;
import xls.adapter.XlsAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FloatTypeFieldWriter <T> implements TypeFieldWriter {

    private static FloatTypeFieldWriter typeFieldWriter;
    private TypeAdapter typeAdapter = null;

    private FloatTypeFieldWriter() {
    }

    static <T> FloatTypeFieldWriter<T> getInstance() {
        if (typeFieldWriter == null) {
            typeFieldWriter = new FloatTypeFieldWriter<T>();
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
                writeBooleanToField(field, instant);
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
            field.set(instant, typeAdapter.write(Float.parseFloat(cell.getStringCellValue())));
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

    private <T> void writeBooleanToField(Field field, T instant) throws IllegalAccessException {
        if (typeAdapter != null) {
            field.set(instant, null);
        } else {
            field.set(instant, null);
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
            field.set(instant, typeAdapter.write((float)cell.getNumericCellValue()));
        } else {
            field.set(instant, (float)cell.getNumericCellValue());
        }
    }
}
