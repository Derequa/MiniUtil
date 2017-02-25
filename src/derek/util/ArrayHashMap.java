package derek.util;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class ArrayHashMap<K, V> extends AbstractMap<K, V> {

	/** The default number of buckets, if no number is given. */
	private static final int DEFAULT_SIZE = 16;
	/** The default load-factor, if no factor is given. */
	private static final float DEFAULT_LOADFACTOR = 0.6f;
	
	/** Factor used to determine when to increase the bucket count. */
	private float loadFactor;
	/** The number of buckets to start out with. */
	private int initialCapacity;
	/**
	 * The 3D array of Objects used to map.
	 * The outer array contains the "buckets". A bucket is a 2D array or a "list of arrays". 
	 * Each array in the bucket has a size of two and holds two Objects, a key and value.
	 * The key is always stored in position 0, and the value is always stored in position 1.
	 */
	private Object[][][] buckets;
	/** The number of key-value pairs stored in this map. */
	private int size = 0;
	
	
	/**
	 * This constructs an empty Array-based HashMap with the default number of buckets,
	 * and the default load-factor.
	 */
	public ArrayHashMap(){
		this(DEFAULT_SIZE, DEFAULT_LOADFACTOR);
	}
	
	
	/**
	 * This constructs and Array-based HashMap with the given initial number of buckets.
	 * @param initialCapacity The initial number of buckets to use (default is 16).
	 */
	public ArrayHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOADFACTOR);
	}
	
	
	/**
	 * This constructs an empty Array-based HashMap with a given initial number of buckets and
	 * a given load-factor.
	 * @param initialCapacity The initial number of buckets to use (default is 16).
	 * @param loadFactor The initial load-factor that will determine when to increase the number
	 * 		  of buckets (default is 0.6).
	 */
	public ArrayHashMap(int initialCapacity, float loadFactor) {
		this.loadFactor = loadFactor;
		this.initialCapacity = initialCapacity;
		buckets = new Object[initialCapacity][][];
	}
	
	
	/**
	 * This method uses a hashing function to map a given hash code to a range of integers we can get an index from.
	 * This is to be used in conjunction with the toIndex method.
	 * @param hashCode The hash code to map.
	 * @return The map hashed code.
	 */
	private int hash(int hashCode) {
		// TODO stop the hacks
		if (hashCode < 0)
			return hashCode *-1;
		return hashCode;
	}

	
	/**
	 * This method converts a mapped hash code into a usable index for this HashMap.
	 * @param hash The result of the hashing function.
	 * @return A usable index derived from the hashing function.
	 */
	private int toIndex(int hash) {
		// TODO srsly stop the hacks
		return hash % buckets.length;
	}

	
	/**
	 * This method completely empties all the Keys and Values and resets the HashMap to its
	 * default number of buckets.
	 */
	@Override
	public void clear() {
		buckets = new Object[initialCapacity][][];
		size = 0;
	}



	/**
	 * 
	 */
	@Override
	public boolean containsKey(Object key) {
		// Generate an index for the array of buckets using the key's hashcode
		int index = toIndex(hash(key.hashCode()));
		
		// Return false if the key hashes to a bucket that hasn't been initialized
		if (buckets[index] == null)
			return false;
		
		// Loop through the list of arrays in the bucket
		for (int i = 0 ; i < buckets[index].length ; i++) {
			// Get the current key
			Object k = buckets[index][i][0];
			// Check for end-of-list
			if (k == null)
				return false;
			// Compare keys
			if ((k.hashCode() == key.hashCode()) && (k.equals(key)))
				return true;
		}
		
		return false;
	}
	

	
	@Override
	public boolean containsValue(Object value) {
		// Look through the buckets
		for (int i = 0 ; i < buckets.length ; i++) {
			// Move on if we find an empty bucket
			if (buckets[i] == null)
				continue;
			// Look through the list of arrays in this bucket
			inner: for (int j = 0 ; j < buckets[i].length ; j++) {
				// Get the value
				Object v = buckets[i][j][1];
				// Move on if null
				if(v == null)
					break inner;
				// Check equals
				if (v.equals(value))
					return true;
			}
		}
		return false;
	}




	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		// Create and empty set
		Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>();
		// Get all the keys
		Set<K> keys = keySet();
		// Add each key with its mapped value
		for (K key : keys) 
			set.add(new AbstractMap.SimpleEntry<K, V>(key, get(key)));
		
		return set;
	}




	@Override
	public V get(Object key) {
		// Generate an index for the array of buckets using the key's hashcode
		int index = toIndex(hash(key.hashCode()));
		
		// Return false if the key hashes to a bucket that hasn't been initialized
		if (buckets[index] == null)
			return null;
		
		// Loop through the list of arrays in the bucket
		for (int i = 0 ; i < buckets[index].length ; i++) {
			// Get the current key
			Object k = buckets[index][i][0];
			// Check for end-of-list
			if (k == null)
				return null;
			// Compare keys
			if ((k.hashCode() == key.hashCode()) && (k.equals(key)))
				return (V) buckets[index][i][1];
		}
			
		return null;
	}




	@Override
	public boolean isEmpty() {
		return size == 0;
	}




	@Override
	public Set<K> keySet() {
		Set<K> set  = new HashSet<K>();
		
		// Loop through all the buckets
		for (int i = 0 ; i < buckets.length ; i++) {
			// Skip an empty bucket
			if (buckets[i] == null)
				continue;
			
			// Loop through all the entries in the bucket's list of arrays, and add the values to the list
			for (int j = 0 ; j < buckets[i].length ; j++) {
				set.add((K) buckets[i][j][0]);
			}
		}
		return set;
	}




	@Override
	public V put(K key, V value) {
		// Generate an index for the array of buckets using the key's hashcode
		int index = toIndex(hash(key.hashCode()));
		
		// Check for empty list of arrays
		if (buckets[index] == null) {
			// Create new list of arrays
			buckets[index] = new Object[DEFAULT_SIZE][2];
			// Store the key and value
			buckets[index][0][0] = key;
			buckets[index][0][1] = value;
			size++;
			return value;
		}
		
		int listLength = buckets[index].length;
		// Check for full list of arrays
		if ((buckets[index][listLength - 1][0] != null)) {
			// Double the size of the list of arrays
			buckets[index] = Arrays.copyOf(buckets[index], listLength * 2);
			growBuckets();
			// Store the key and value at the end of the list
			buckets[index][listLength][0] = key;
			buckets[index][listLength][1] = value;
			size++;
			return value;
		}
		
		// Loop to the end of a list of arrays
		for (int i = 0 ; i < listLength - 1 ; i++) {
			if (buckets[index][i][0] == null) {
				// Store the key and value at the end of the array
				buckets[index][i][0] = key;
				buckets[index][i][1] = value;
				size++;
				return value;
			}
		}

		// Bad things happened
		throw new IllegalStateException("Key/Value pair was not stored for some strange reason.");
	}




	private void growBuckets() {
		// A counter for the number of buckets that are at least half-full
		int numHalfFull = 0;
		
		// Loop through all the buckets
		for (int i = 0 ; i < buckets.length ; i++) {
			
			// If a list of arrays has not been created, move on
			if (buckets[i] == null)
				continue;
			
			// Count the number of lists of arrays that have a key/value stored
			int j;
			for (j = 1 ; j < buckets[i].length ; j++){
				if (buckets[i][j][0] == null)
					break;
			}
			
			// Check if the number we counted is at least half of the size of the list of arrays
			if (((double) j / (double) buckets[i].length) >= 0.5)
				numHalfFull++;
		}
		
		// If 60% of the buckets are at least half-full, double the amount of buckets
		if (((double) numHalfFull / (double) buckets.length) >= loadFactor)
			buckets = Arrays.copyOf(buckets, buckets.length * 2);
	}
	
	
	
	private void shiftBucket(Object[][] bucket) {
		// Look through the buckets list of arrays for gaps
		for (int i = 0 ; i < bucket.length ; i++) {
			// If we find a gap, shift the next element down
			if ((bucket[i][0] == null) && (i < (bucket.length - 1)) && (bucket[i + 1][0] != null)) {
				bucket[i][0] = bucket[i + 1][0];
				bucket[i][1] = bucket[i + 1][1];
				bucket[i + 1][0] = null;
				bucket[i + 1][1] = null;
			}
		}
	}




	@Override
	public void putAll(Map m) {
		for (Object key : m.keySet()) {
			Object value = m.get(key);
			put((K) key, (V) value);
		}
	}




	@Override
	public V remove(Object key) {
		// Generate an index for the array of buckets using the key's hashcode
		int index = toIndex(hash(key.hashCode()));
		
		// Return false if the key hashes to a bucket that hasn't been initialized
		if (buckets[index] == null)
			return null;
		
		// Loop through the list of arrays in the bucket
		for (int i = 0 ; i < buckets[index].length ; i++) {
			// Get the current key
			Object k = buckets[index][i][0];
			// Check for end-of-list
			if (k == null)
				return null;
			// Compare keys
			if ((k.hashCode() == key.hashCode()) && (k.equals(key))) {
				V v = (V) buckets[index][i][1];
				buckets[index][i][0] = null;
				buckets[index][i][1] = null;
				shiftBucket(buckets[i]);
				size--;
				return v;
			}
		}
			
		return null;
	}




	@Override
	public int size() {
		return size;
	}




	@Override
	public Collection<V> values() {
		Collection<V> list  = new LinkedList<V>();
		
		// Loop through all the buckets
		for (int i = 0 ; i < buckets.length ; i++) {
			// Skip an empty bucket
			if (buckets[i] == null)
				continue;
			
			// Loop through all the entries in the bucket's list of arrays, and add the values to the list
			for (int j = 0 ; j < buckets[i].length ; j++) {
				list.add((V) buckets[i][j][1]);
			}
		}
		return list;
	}
}
