package fr.tse.db.storage.data;


import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static fr.tse.db.storage.data.BitsConverter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BitsConverterTest {

    @Test
    public void LongToBitSetTest() {
        BitSet bs1 = new BitSet();
        bs1.set(0, true);
        BitSet bs2 = new BitSet();
        bs2.set(1, true);
        BitSet bs2000 = new BitSet();
        bs2000.set(4, true);
        bs2000.set(6, true);
        bs2000.set(7, true);
        bs2000.set(8, true);
        bs2000.set(9, true);
        bs2000.set(10, true);

        assertEquals(bs1, LongToBitSet(1L));
        assertEquals(bs2, LongToBitSet(2L));
        assertEquals(bs2000, LongToBitSet(2000L));
    }

    @Test
    public void BitSetToLongTest() {

        BitSet BitOne = new BitSet();
        BitOne.set(0, true);
        Long one = BitSetToLong(BitOne);


        BitSet BitTwo = new BitSet();
        BitTwo.set(1, true);
        Long two = BitSetToLong(BitTwo);


        assertEquals(Long.valueOf(1), one);
        assertEquals(Long.valueOf(2), two);
    }

    @Test
    public void ValTypeToBitSetTest() {

        Int32 one32 = new Int32(1);
        Int64 one64 = new Int64(1L);
        Float32 one32f = new Float32(1f);


        BitSet one32Bit = ValTypeToBitSet(one32);
        BitSet one64Bit = ValTypeToBitSet(one64);
        BitSet one32fBit = ValTypeToBitSet(one32f);

        BitSet bs1 = new BitSet();
        bs1.set(0, true);

        BitSet bsFloat1 = new BitSet();
        bsFloat1.set(0, true);
        bsFloat1.set(1, true);
        bsFloat1.set(2, true);
        bsFloat1.set(3, true);
        bsFloat1.set(4, true);
        bsFloat1.set(5, true);
        bsFloat1.set(15, true);

        assertEquals(bs1, one32Bit);
        assertEquals(bs1, one64Bit);
        assertEquals(bsFloat1, one32fBit);
    }

    @Test
    public void BitSetToInt64Test() {

        BitSet BitOne = new BitSet();
        BitOne.set(0, true);
        Int64 one = BitSetToInt64(BitOne);

        assertEquals(Long.valueOf(1), one.getVal());
    }

    @Test
    public void BitSetToInt32Test() {

        BitSet BitOne = new BitSet();
        BitOne.set(0, true);
        Int32 one = BitSetToInt32(BitOne);

        assertEquals(Integer.valueOf(1), one.getVal());

    }

    @Test
    public void BitSetToFloat32Test() {
        BitSet bsFloat1 = new BitSet();
        bsFloat1.set(0, true);
        bsFloat1.set(1, true);
        bsFloat1.set(2, true);
        bsFloat1.set(3, true);
        bsFloat1.set(4, true);
        bsFloat1.set(5, true);
        bsFloat1.set(15, true);

        assertEquals(new Float32(1.f), BitSetToFloat32(bsFloat1));
    }

    @Test
    public void BitSetToValTypeTest() {
        BitSet BitOne = new BitSet();
        BitOne.set(0, true);
        Int64 ex64 = new Int64(0L);
        Int64 one64 = (Int64) BitSetToValType(BitOne, ex64.getClass().getSimpleName());
        assertEquals(Long.valueOf(1), one64.getVal());

        Int32 ex32 = new Int32(0);
        Int32 one32 = (Int32) BitSetToValType(BitOne, ex32.getClass().getSimpleName());
        assertEquals(Integer.valueOf(1), one32.getVal());


        BitSet bsFloat1 = new BitSet();
        bsFloat1.set(0, true);
        bsFloat1.set(1, true);
        bsFloat1.set(2, true);
        bsFloat1.set(3, true);
        bsFloat1.set(4, true);
        bsFloat1.set(5, true);
        bsFloat1.set(15, true);
        Float32 ex32f = new Float32(1.f);
        Float32 one32f = (Float32) BitSetToValType(bsFloat1, ex32f.getClass().getSimpleName());
        assertEquals(new Float(1), one32f.getVal());
    }
}
