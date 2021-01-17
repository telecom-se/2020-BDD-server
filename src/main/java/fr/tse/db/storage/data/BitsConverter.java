package fr.tse.db.storage.data;

import java.util.BitSet;

/**
 * Utility class to make conversions between bitsets and the different value types.
 * Mainly used by the SeriesQueue class.
 *
 * @author remi huguenot
 */
public class BitsConverter {
    public static BitSet LongToBitSet(long value) {
    	byte longBits = ((Number) value).byteValue();
    	BitSet bits = BitSet.valueOf(new byte[]{longBits});
        return bits;
    }

    public static long BitSetToLong(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    public static BitSet ValTypeToBitSet(ValueType value) {
         byte floatBits = ((Number) value.getVal()).byteValue();
         BitSet bits = BitSet.valueOf(new byte[]{floatBits});
        return bits;
    }

    public static Int64 BitSetToInt64(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return new Int64(value);
    }

    public static Int32 BitSetToInt32(BitSet bits) {
        Integer value = 0;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1 << i) : 0;
        }
        System.out.println(bits);

        return new Int32(value);
    }

    public static Float32 BitSetToFloat32(BitSet bits) {
        byte[] byteArr = bits.toByteArray();
        return new Float32(((Byte) byteArr[0]).floatValue());
    }

    public static ValueType BitSetToValType(BitSet val, String className) {
        System.out.println(val);
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
