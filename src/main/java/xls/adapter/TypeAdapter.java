package xls.adapter;

public interface TypeAdapter<V, R> {
    public R write(V value);
}
