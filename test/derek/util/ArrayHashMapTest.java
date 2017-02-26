package derek.util;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Test;

/**
 * This tests the array-based hash-map data structure implemented in ArrayHashMap.
 * @author Derek Batts <dsbatts@ncsu.edu>
 */
public class ArrayHashMapTest {

	/** A constant for creating string keys. */
	private static final String EMPTYKEY = "Key #";
	/** A constant for cerating string values. */
	private static final String EMPTYVALUE = "Value #";
	
	/**
	 * This method currently tests ArrayHashMap through 'brute-force usage'.
	 * That is a round abotu way of saying it just uses it and expects to to work like a hash-map.
	 */
	@Test
	public void testPutGetRemove() {
		// Create a map to test
		ArrayHashMap<String, String> map = new ArrayHashMap<String, String>();
		
		// Put 100 key-value pairs in the map
		int numValues = 100;
		for(int i = 0 ; i < numValues ; i++)
			map.put(EMPTYKEY + i, EMPTYVALUE + i);
		
		// Sanity test that keys were put in correctly and return the correct values
		assertTrue(!map.isEmpty());
		assertEquals(numValues, map.size());
		assertEquals(EMPTYVALUE + 0, map.get(EMPTYKEY + 0));
		assertEquals(EMPTYVALUE + (numValues - 1), map.get(EMPTYKEY + (numValues - 1)));
		assertEquals(EMPTYVALUE + (numValues / 2), map.get(EMPTYKEY + (numValues / 2)));
		
		// Randomly check other key-value pairs
		Random r = new Random();
		for (int i = 0 ; i < 20 ; i++) {
			int randomInt = r.nextInt(numValues);
			assertTrue(map.containsKey(EMPTYKEY + randomInt));
			assertTrue(map.containsValue(EMPTYVALUE + randomInt));
			assertEquals(EMPTYVALUE + randomInt, map.get(EMPTYKEY + randomInt));
			
			randomInt = r.nextInt() + numValues;
			assertFalse(map.containsKey(EMPTYKEY + randomInt));
			assertFalse(map.containsValue(EMPTYVALUE + randomInt));
			assertFalse(map.containsKey(EMPTYKEY + (randomInt * -1)));
			assertFalse(map.containsValue(EMPTYVALUE + (randomInt * -1)));
		}
		
		// Make sure removing changes the size
		map.remove(EMPTYKEY + (numValues - 1));
		assertEquals(numValues - 1, map.size());
		
		// Try to clear out the map, and make sure it actually did stuff
		map.clear();
		assertTrue(map.isEmpty());
		assertEquals(0,  map.size());
		assertFalse(map.containsKey(EMPTYKEY + 0));
		assertFalse(map.containsValue(EMPTYVALUE + 0));
	}
	
}
