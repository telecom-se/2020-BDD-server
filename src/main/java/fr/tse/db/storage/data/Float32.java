package fr.tse.db.storage.data;

import fr.tse.db.storage.exception.WrongValueTypeException;
import lombok.Data;

/**
 * This Float32 class encapsulates a float of 32-bits
 *
 * @author Valentin, Alexandre, Youssef
 * @since 2020-11
 */
@Data
public class Float32 implements ValueType<Float> {

    private Float val;

    public Float32(Float val) {
        this.val = val;
    }

    public int compareTo(ValueType o) {
        if (o instanceof Float32) {
            return this.val.compareTo(((Float32) o).val);
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
}
