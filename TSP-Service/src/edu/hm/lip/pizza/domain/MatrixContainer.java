package edu.hm.lip.pizza.domain;

import java.util.HashMap;
import java.util.Map;

public class MatrixContainer<T> {

    Map<Integer, Map<Integer, T>> matrix;

    public MatrixContainer() {
        matrix = new HashMap<Integer, Map<Integer, T>>();
    }

    public T get(Integer x, Integer y) {
        return matrix.get(x).get(y);
    }

    public void set(Integer x, Integer y, T value) {
        Map<Integer, T> column = matrix.get(x);

        if (column == null) {
            column = new HashMap<Integer, T>();
            matrix.put(x, column);
        }

        column.put(y, value);

    }

    @Override
    public String toString() {
        return "MatrixContainer [matrix=" + matrix + "]";
    }

}
