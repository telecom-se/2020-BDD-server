package fr.tse.db.storage.data;

import java.util.BitSet;

/**
 * Utilitary class to make conversions between bitsets and the differents valuetypes.
 * Mainly used by the SeriesQueue class.
 *
 * @author remi huguenot
 */
public class BitsConverter {
    public static BitSet LongToBitSet(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
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
        String cl = value.getVal().getClass().getSimpleName();
        BitSet bits = new BitSet();
        int index;

        switch (cl) {
            // Case Float32
            case "Float":
                byte floatBits = ((Number) value.getVal()).byteValue();
                bits = BitSet.valueOf(new byte[]{floatBits});
                break;
            //Case Int32
            case "Integer":
                int valInt = ((Int32) value).getVal();
                index = 0;
                while (valInt != 0) {
                    if (valInt % 2 != 0) {
                        bits.set(index);
                    }
                    ++index;
                    valInt = valInt >>> 1;
                }
                break;
            //Case Int64
            case "Long":
                long valLong = ((Int64) value).getVal();
                index = 0;
                while (valLong != 0L) {
                    if (valLong % 2L != 0) {
                        bits.set(index);
                    }
                    ++index;
                    valLong = valLong >>> 1;
                }
                break;
            default:
                System.out.println("default");
                break;
        }
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
        return new Int32(value);
    }

    public static Float32 BitSetToFloat32(BitSet bits) {
        byte[] byteArr = bits.toByteArray();

        return new Float32(((Byte) byteArr[0]).floatValue());
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
