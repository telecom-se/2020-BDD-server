package fr.tse.db.storage.exception;

import fr.tse.db.storage.data.ValueType;

public class WrongValueTypeException extends RuntimeException {
    public WrongValueTypeException(Class<? extends ValueType> valueType1, Class<? extends ValueType> valueType2) {
        super("ValueType=" + valueType1.getSimpleName() + " | ValueType 2=" + valueType2.getSimpleName());
    }
}
