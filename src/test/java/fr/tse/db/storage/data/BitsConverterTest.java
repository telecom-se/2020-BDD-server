package fr.tse.db.storage.data;

import static org.junit.jupiter.api.Assertions.*;

import static fr.tse.db.storage.data.BitsConverter.*;
import java.util.BitSet;

import org.junit.jupiter.api.Test;

class BitsConverterTest {

	@Test
	void LongToBitSetTest() {
		
		BitSet oneBit = LongToBitSet(1L);
		BitSet BitOne = new BitSet();
		BitOne.set(0,true);

		BitSet twoBit = LongToBitSet(2L);
		BitSet BitTwo = new BitSet();
		BitTwo.set(1,true);
		
		assertTrue(oneBit.equals(BitOne));
		assertTrue(twoBit.equals(BitTwo));
	}

	@Test
	void BitSetToLongTest() {
		
		BitSet BitOne = new BitSet();
		BitOne.set(0,true);
		Long one = BitSetToLong(BitOne);

		
		BitSet BitTwo = new BitSet();
		BitTwo.set(1,true);
		Long two = BitSetToLong(BitTwo);

		
		assertEquals(1L,one);
		assertEquals(2L, two);
	}
	
	@Test
	void ValTypeToBitSetTest() {
		
		Int32 one32 = new Int32(1);
		Int64 one64 = new Int64(1L);
		Float32 one32f = new Float32(1f);
		

		BitSet one32Bit = ValTypeToBitSet(one32);
		BitSet one64Bit = ValTypeToBitSet(one64);
		BitSet one32fBit = ValTypeToBitSet(one32f);

		BitSet BitOne = new BitSet();
		BitOne.set(0,true);
		
		assertTrue(BitOne.equals(one32Bit));
		assertTrue(BitOne.equals(one64Bit));
		assertTrue(BitOne.equals(one32fBit));

	}
	
	@Test
	void BitSetToInt64Test() {
		
		BitSet BitOne = new BitSet();
		BitOne.set(0,true);
		Int64 one = BitSetToInt64(BitOne);
		
		assertEquals(1L,one.getVal());
	}
	
	@Test
	void BitSetToInt32Test() {
		
		BitSet BitOne = new BitSet();
		BitOne.set(0,true);
		Int32 one = BitSetToInt32(BitOne);

		assertEquals(1,one.getVal());
		
	}
	
	@Test
	void BitSetToFloat32Test() {
		BitSet BitOne = new BitSet();
		BitOne.set(0,true);
		Float32 one = BitSetToFloat32(BitOne);

		assertEquals(1f,one.getVal());	
	}
	
	@Test
	void BitSetToValTypeTest() {
		
		BitSet BitOne = new BitSet();
		BitOne.set(0,true);
		Int64 ex64 = new Int64(0L);
		Int64 one64 = (Int64) BitSetToValType(BitOne,ex64.getClass().getSimpleName());
		assertEquals(1L,one64.getVal());
		
		Int32 ex32 = new Int32(0);
		Int32 one32 = (Int32) BitSetToValType(BitOne, ex32.getClass().getSimpleName());
		assertEquals(1,one32.getVal());
		
		Float32 ex32f = new Float32(0f);
		Float32 one32f = (Float32) BitSetToValType(BitOne, ex32f.getClass().getSimpleName());
		assertEquals(1f,one32f.getVal());	
	}
}
