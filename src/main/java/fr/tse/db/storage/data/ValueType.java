package fr.tse.db.storage.data;
import fr.tse.db.storage.exception.WrongValueTypeException;

/**
* This ValueType is a general interface extended by the
* {@link Int32}, {@link Int64} and {@link Float32} subclasses
*
* @author  Valentin, Alexandre, Youssef
* @since   2020-11
*/

public interface ValueType<T> extends Comparable<ValueType>{
	
	public T getVal();
	public void setVal(T val);
	
	/**
	 * Sum this object with another ValueType
	 * @param i another ValueType object
	 * @throws WrongValueTypeException if types do not match
	 */
	public ValueType sum(ValueType i) throws WrongValueTypeException;
	
	/**
	 * Divide this object by an int
	 * @param denom the int to divide with
	 * @return divided value
	 */
	public float divide(int denom);
}
