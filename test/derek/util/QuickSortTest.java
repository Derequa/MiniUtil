package derek.util;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testWorstCase() {
		LinkedList<Integer> l0 = new LinkedList<Integer>();
		for (int i = 10 ; i >=0 ; i--)
			l0.add(new Integer(i));
		
		QuickSort.sort(l0);
		assertEquals(11, l0.size());
		
		for (int i = 0 ; i < 11 ; i++)
			assertEquals(i, l0.get(i).intValue());
	}

}
