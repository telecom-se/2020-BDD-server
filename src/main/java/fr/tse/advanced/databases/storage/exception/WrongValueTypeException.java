package fr.tse.advanced.databases.storage.exception;

import fr.tse.advanced.databases.storage.data.ValueType;

public class WrongValueTypeException extends RuntimeException {
	
	public WrongValueTypeException(Class<? extends ValueType> valueType, Class<? extends ValueType> seriesValueType){
		super("ValueType="+valueType.getSimpleName() + " | SeriesValueType=" + seriesValueType.getSimpleName());
	}

}
