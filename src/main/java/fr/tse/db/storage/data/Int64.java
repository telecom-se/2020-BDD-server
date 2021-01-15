package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.WrongValueTypeException;

/**
 * This Int64 class encapsulates an int of 64-bits
 *
 * @author Valentin, Alexandre, Youssef
 * @since 2020-11
 */
public class Int64 implements ValueType<Long> {
    private Long val;

    public Int64(Long val) {
        this.val = val;
    }

    public Long getVal() {
        return val;
    }

    public void setVal(Long val) {
        this.val = val;
    }

    public int compareTo(ValueType o) {
        if (o instanceof Int64) {
            if (this.val == ((Int64) o).val) {
                return 0;
            } else if (this.val < ((Int64) o).val) {
                return -1;
            } else {
                return 1;
            }
        } else {
            throw new WrongValueTypeException(this.getClass(), o.getClass());
        }
    }

    @Override
    public String toString() {
        return "Int64[" + val + "]";
    }

    public ValueType sum(ValueType i) throws WrongValueTypeException {
        Int64 res = new Int64(0L);
        if (i instanceof Int64) {
            res.setVal(this.getVal() + ((Int64) i).getVal());
        } else {
            throw new WrongValueTypeException(this.getClass(), i.getClass());
        }
        return res;
    }

    public float divide(int denom) {
        return (float) this.val / denom;
    }
}
