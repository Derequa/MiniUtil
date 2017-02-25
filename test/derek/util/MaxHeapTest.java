package derek.util;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class MaxHeapTest {

	@Test
	public void test() {
		Random r = new Random();
		MaxHeap<Integer> heap = new MaxHeap<Integer>();

		for (int count = 0 ; count < 10 ; count++) {
			heap.add(new Integer(count));
			assertEquals("Heap isn't the right size!", count + 1, heap.size());
			assertEquals("Heap has incorrect max value!", new Integer(count), heap.peek());
		}
		System.out.println(heap);
		for (int count = 9 ; count >= 0 ; count--) {
			assertEquals("Wrong max value!", new Integer(count), heap.poll());
		}
		
		int currentMax = -1;
		for (int iterations = 0 ; iterations < 100 ; iterations++) {
			int next = r.nextInt(Integer.MAX_VALUE);
			if (next > currentMax)
				currentMax = next;
			heap.add(new Integer(next));
			assertEquals("Heap isn't the right size!", iterations + 1, heap.size());
			assertEquals("Heap has incorrect max value!", new Integer(currentMax), heap.peek());
		}
		System.out.println(heap);
	}

}
