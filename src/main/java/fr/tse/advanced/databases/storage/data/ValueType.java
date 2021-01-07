package fr.tse.advanced.databases.storage.data;

import fr.tse.advanced.databases.storage.exception.WrongValueTypeException;

public interface ValueType<T> extends Comparable<ValueType>{
	public T getVal();
	public void setVal(T val);
	public void sum(ValueType i) throws WrongValueTypeException;
	public float divide(int denom);
}
