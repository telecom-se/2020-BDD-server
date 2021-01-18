package fr.tse.db.storage.data;

import java.nio.ByteBuffer;
import java.util.BitSet;

/**
 * Utility class to make conversions between bitsets and the different value types.
 * Mainly used by the SeriesQueue class.
 *
 * @author remi huguenot
 */
public class BitsConverter {
    public static BitSet LongToBitSet(long value) {
        return BitSet.valueOf(new long[]{value});
    }

    public static BitSet ValTypeToBitSet(ValueType value) {
        switch (value.getClass().getSimpleName()) {
            case "Int64":
                return LongToBitSet((Long) value.getVal());
            case "Int32":
                return LongToBitSet((Integer) value.getVal());
            case "Float32":
                Float f = (Float) value.getVal();
                return BitSet.valueOf(ByteBuffer.allocate(4).putFloat(f).array());
            default:
                throw new IllegalArgumentException("Cannot convert from value type " + value.getClass().getSimpleName());
        }
    }

    public static long BitSetToLong(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    public static Int64 BitSetToInt64(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return new Int64(value);
    }

    public static Int32 BitSetToInt32(BitSet bits) {
        int value = 0;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1 << i) : 0;
        }
        return new Int32(value);
    }

    public static Float32 BitSetToFloat32(BitSet bits) {
        float f = ByteBuffer.wrap(ByteBuffer.allocate(4).put(bits.toByteArray()).array()).getFloat();
        return new Float32(f);
    }

    public static ValueType BitSetToValType(BitSet val, String className) {
        switch (className) {
            case "Int64":
                return BitSetToInt64(val);
            case "Int32":
                return BitSetToInt32(val);
            case "Float32":
                return BitSetToFloat32(val);
            default:
                return null;
        }
    }
}
