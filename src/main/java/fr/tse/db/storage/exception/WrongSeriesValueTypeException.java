package fr.tse.db.storage.exception;

import fr.tse.db.storage.data.ValueType;

public class WrongSeriesValueTypeException extends RuntimeException {
    public WrongSeriesValueTypeException(Class<? extends ValueType> valueType, Class<? extends ValueType> seriesValueType) {
        super("ValueType=" + valueType.getSimpleName() + " | SeriesValueType=" + seriesValueType.getSimpleName());
    }
}
