package derek.util;

import java.util.Arrays;

public class MaxHeap<T extends Comparable<T>> {

    private static final int DEFAULT_SIZE = 20;
    
    private int size = 0;
    private Object[] elements;

    public MaxHeap() {
        this(DEFAULT_SIZE);
    }

    public MaxHeap(int initialCapacity) {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("Heap must have an initial size > 0");
        elements = new Object[initialCapacity];
    }
    
    public int size() {
    	return size;
    }

    @SuppressWarnings("unchecked")
	public T peek() {
        if (size == 0)
            throw new IllegalArgumentException("Empty heap!");
        return (T) elements[0];
    }

    public void add(T element) {
    	size++;
    	maintainCapacity();
        elements[size - 1] = element;
        maintainUpwards();
    }

    @SuppressWarnings("unchecked")
	public T poll() {
        if (size == 0)
            throw new IllegalArgumentException("Empty heap!");
        T temp = (T) elements[0];
        elements[0] = elements[size - 1];
        size--;
        maintainDownwards();
        return temp;
    }
    
    public String toString() {
    	return Arrays.toString(elements);
    }

    @SuppressWarnings("unchecked")
	private void maintainDownwards() {
    	int i = 0;
    	while (hasLeftChild(i)) {
    		int greaterIndex = getIndexOfLeftChild(i);
    		if (hasRightChild(i) && (getRightChild(i).compareTo(getLeftChild(i)) > 0))
    			greaterIndex = getIndexOfRightChild(i);
    		if (((T) elements[i]).compareTo((T) elements[greaterIndex]) < 0)
    			swap(i, greaterIndex);
    		else
    			break;
    		i = greaterIndex;
    	}
    }

    @SuppressWarnings("unchecked")
	private void maintainUpwards() {
    	int i = size - 1;
    	loop: while (hasParent(i)) {
    		if (getParent(i).compareTo((T) elements[i]) > 0) 
    			break loop;
    		int parentIdx = getIndexOfParent(i);
    		swap(i, parentIdx);
    		i = parentIdx;
    	}
    }

    @SuppressWarnings("unchecked")
	private void swap(int index0, int index1) {
        T temp = (T) elements[index0];
        elements[index0] = elements[index1];
        elements[index1] = temp;
    }

    private void maintainCapacity() {
        if (size == elements.length)
            elements = Arrays.copyOf(elements, size * 2);
    }

    @SuppressWarnings("unchecked")
	private T getLeftChild(int parentIndex) {
        return (T) elements[getIndexOfLeftChild(parentIndex)];
    }

    @SuppressWarnings("unchecked")
	private T getRightChild(int parentIndex) {
        return (T) elements[getIndexOfRightChild(parentIndex)];
    }

    @SuppressWarnings("unchecked")
	private T getParent(int childIndex) {
        return (T) elements[getIndexOfParent(childIndex)];
    }

    private boolean hasLeftChild(int parentIndex) {
        return ((2 * parentIndex) + 1) < size;
    }

    private boolean hasRightChild(int parentIndex) {
        return ((2 * parentIndex) + 2) < size;
    }

    private boolean hasParent(int childIndex) {
    	if (childIndex == 0)
    		return false;
        return ((childIndex - 1) / 2) >= 0;
    }

    private int getIndexOfLeftChild(int parentIndex) {
        return (2 * parentIndex) + 1;
    }

    private int getIndexOfRightChild(int parentIndex) {
        return (2 * parentIndex) + 2;
    }

    private int getIndexOfParent(int childIndex) {
        return (childIndex - 1) / 2;
    }
} 