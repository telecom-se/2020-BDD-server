package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.WrongValueTypeException;

/**
 * This Float32 class encapsulates a float of 32-bits
 *
 * @author Valentin, Alexandre, Youssef
 * @since 2020-11
 */
public class Float32 implements ValueType<Float> {

    private Float val;

    public Float32(Float val) {
        this.val = val;
    }

    public Float getVal() {
        return val;
    }

    public void setVal(Float val) {
        this.val = val;
    }

    public int compareTo(ValueType o) {
        if (o instanceof Float32) {
            if (this.val == ((Float32) o).val) {
                return 0;
            } else if (this.val < ((Float32) o).val) {
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
        return "Float32[" + val + "]";
    }

    public ValueType sum(ValueType i) throws WrongValueTypeException {
        Float32 res = new Float32(0F);
        if (i instanceof Float32) {
            res.setVal(this.getVal() + ((Float32) i).getVal());
        } else {
            throw new WrongValueTypeException(this.getClass(), i.getClass());
        }
        return res;
    }

    public float divide(int denom) {
        return (float) this.val / denom;
    }
}
