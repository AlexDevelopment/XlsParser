package xls.type;

import java.lang.reflect.Field;

public class TypeWriterFactory {

    private static final String STRING_TYPE = String.class.getTypeName();
    private static final String INTEGER_TYPE = int.class.getTypeName();
    private static final String INTEGER_OBJECT_TYPE = Integer.class.getTypeName();
    private static final String FLOAT_TYPE = float.class.getTypeName();
    private static final String FLOAT_OBJECT_TYPE = Float.class.getTypeName();
    private static final String LONG_TYPE = long.class.getTypeName();
    private static final String LONG_OBJECT_TYPE = Long.class.getTypeName();

    public static <T> TypeFieldWriter getTypeWriterBy(Field field){
        String fieldType = field.getGenericType().getTypeName();

        if (fieldType.equals(INTEGER_TYPE) || fieldType.equals(INTEGER_OBJECT_TYPE)){
            return IntegerTypeFieldWriter.<Class<T>>getInstance();
        } else if (fieldType.equals(FLOAT_TYPE) || fieldType.equals(FLOAT_OBJECT_TYPE)){
            return FloatTypeFieldWriter.<Class<T>>getInstance();
        } else if (fieldType.equals(LONG_TYPE) || fieldType.equals(LONG_OBJECT_TYPE)){
            return LongTypeFieldWriter.<Class<T>>getInstance();
        }

        return StringTypeFieldWriter.<Class<T>>getInstance();
    }
}
