package fr.tse.advanced.databases.storage.data;

import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public interface ValueType extends Comparable<ValueType>{
	public void sum(ValueType i) throws WrongValueTypeException;
	public float divide(int denom);
}
