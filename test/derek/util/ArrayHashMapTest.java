package derek.util;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Test;

public class ArrayHashMapTest {

	
	private String emptyKey = "Key #";
	private String emptyValue = "Value #";
	
	@Test
	public void testPutGetRemove() {
		ArrayHashMap<String, String> map = new ArrayHashMap<String, String>();
		
		int numValues = 100;
		for(int i = 0 ; i < numValues ; i++)
			map.put(emptyKey + i, emptyValue + i);
		
		assertTrue(!map.isEmpty());
		assertEquals(numValues, map.size());
		assertEquals(emptyValue + 0, map.get(emptyKey + 0));
		assertEquals(emptyValue + (numValues - 1), map.get(emptyKey + (numValues - 1)));
		assertEquals(emptyValue + (numValues / 2), map.get(emptyKey + (numValues / 2)));
		
		Random r = new Random();
		for (int i = 0 ; i < 20 ; i++) {
			int randomInt = r.nextInt(numValues);
			assertTrue(map.containsKey(emptyKey + randomInt));
			assertTrue(map.containsValue(emptyValue + randomInt));
			assertEquals(emptyValue + randomInt, map.get(emptyKey + randomInt));
			
			randomInt = r.nextInt() + numValues;
			assertFalse(map.containsKey(emptyKey + randomInt));
			assertFalse(map.containsValue(emptyValue + randomInt));
			assertFalse(map.containsKey(emptyKey + (randomInt * -1)));
			assertFalse(map.containsValue(emptyValue + (randomInt * -1)));
		}
		
		map.remove(emptyKey + (numValues - 1));
		assertEquals(numValues - 1, map.size());
		
		map.clear();
		assertTrue(map.isEmpty());
		assertEquals(0,  map.size());
		assertFalse(map.containsKey(emptyKey + 0));
		assertFalse(map.containsValue(emptyValue + 0));
	}
	
	
	
	@Test
	public void testEntrySet() {
		fail();
	}
	
	
	
	@Test
	public void testKeySet() {
		fail();
	}
	
	
	
	@Test
	public void testPutAll() {
		fail();
	}
	
	
	
	@Test
	public void testRemove() {
		fail();
	}
	
	
	
	@Test
	public void testValues() {
		fail();
	}
}
