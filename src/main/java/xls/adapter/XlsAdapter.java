package xls.adapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface XlsAdapter {
    Class<? extends TypeAdapter> handler();
}
