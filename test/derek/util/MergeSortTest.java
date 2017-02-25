package derek.util;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

public class MergeSortTest {

	@Test
	public void test() {
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (int i = 10 ; i >= 0 ; i--)
			l.add(new Integer(i));
		
		System.out.println(l);
		MergeSort.sort(l);
		System.out.println(l);
		assertEquals(11, l.size());
		
		for (int i = 0 ; i < 11 ; i++)
			assertEquals(i, l.get(i).intValue());
		
		Random r = new Random();
		l.clear();
		for (int i = 0 ; i < 100 ; i++)
			l.addLast(r.nextInt());
		
		MergeSort.sort(l);
		assertEquals(100, l.size());
		
		for (int i = 0 ; i < 100 ; i++) {
			if ((i > 0) && (i < 99)) {
				assertTrue("Element out of order!"
						 + " i: " + i
						 + " value: " + l.get(i).intValue()
						 + " previous value: " + l.get(i - 1)
						 +"\n", l.get(i - 1).compareTo(l.get(i)) <= 0);
				assertTrue("Element out of order!"
						 + " i: " + i
						 + " value: " + l.get(i).intValue()
						 + " next value: " + l.get(i - 1)
						 +"\n", l.get(i + 1).compareTo(l.get(i)) >= 0);
			}
		}
	}

}
