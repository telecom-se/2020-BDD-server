package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.WrongValueTypeException;

import java.io.Serializable;

/**
 * This ValueType is a general interface extended by the
 * {@link Int32}, {@link Int64} and {@link Float32} subclasses
 *
 * @author Valentin, Alexandre, Youssef
 * @since 2020-11
 */

public interface ValueType<T> extends Comparable<ValueType>, Serializable {

    T getVal();

    void setVal(T val);

    /**
     * Sum this object with another ValueType
     *
     * @param i another ValueType object
     *
     * @throws WrongValueTypeException if types do not match
     */
    ValueType sum(ValueType i) throws WrongValueTypeException;
}
