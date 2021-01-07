package fr.tse.advanced.databases.storage.exception;

import fr.tse.advanced.databases.storage.data.ValueType;

public class WrongValueTypeException  extends RuntimeException{
	public WrongValueTypeException(Class<? extends ValueType> valueType1, Class<? extends ValueType> valueType2){
		super("ValueType="+valueType1.getSimpleName() + " | ValueType 2=" + valueType2.getSimpleName());
	}
}
