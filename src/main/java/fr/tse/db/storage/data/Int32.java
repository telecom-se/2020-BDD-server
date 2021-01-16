package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.WrongValueTypeException;

/**
 * This Int32 class encapsulates an int of 32-bits
 *
 * @author Valentin, Alexandre, Youssef
 * @since 2020-11
 */
public class Int32 implements ValueType<Integer> {
    private Integer val;

    public Int32(Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public int compareTo(ValueType o) {
        if (o instanceof Int32) {
            return this.val.compareTo(((Int32) o).val);
        } else {
            throw new WrongValueTypeException(this.getClass(), o.getClass());
        }
    }

    @Override
    public String toString() {
        return "Int32[" + val + "]";
    }

    public ValueType sum(ValueType i) throws WrongValueTypeException {
        Int32 res = new Int32(0);
        if (i instanceof Int32) {
            res.setVal(this.getVal() + ((Int32) i).getVal());
        } else {
            throw new WrongValueTypeException(this.getClass(), i.getClass());
        }
        return res;
    }
}
