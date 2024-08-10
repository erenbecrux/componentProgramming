package org.example;

import java.util.List;

public interface Dao<T> {
    T read(String name);

    void write(String name,T obj);

    List<String> names();
}
