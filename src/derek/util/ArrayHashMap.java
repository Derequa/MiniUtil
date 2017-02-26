package derek.util;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * This class implements a hash-map using only arrays. No linked lists at all.
 * Chanllenging thing is challenging.
 * This also extends AbstractMap and uses generic types, so that its not a
 * complete waste of code.
 * 
 * @author Derek Batts <dsbatts@ncsu.edu>
 */
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
	 * This method checks if the given key exists in the HashMap.
	 * @param key The key to look for.
	 * @return True if the key exists, false if not.
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
	

	/**
	 * This method checks if a given value exists in this HashMap.
	 * @param value The value to look for.
	 * @return True if the value exists in teh map, false if not.
	 */
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



	/**
	 * This method will create and return an entry set of all the key-value pairs in this HashMap.
	 * @return An entry set of all the key-value pairss in this HashMap.
	 */
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



	/**
	 * This metod will get the value at the given key in the map.
	 * @param key The key for the value we are looking for.
	 * @return The value associated with the given key or null if the key doesn't exist in the map.
	 */
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



	/**
	 * This method checks if the map is empty.
	 * @return True if the map is empty, false if not.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}



	/**
	 * This method creates and returns a set of all the keys in this HashMap.
	 * @return A new set of all the keys in this map.
	 */
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



	/**
	 * This method will store the given key-value pair in this HashMap. If the key already
	 * exists in the map, the new value will be stored and associated with the given key.
	 * @param key The key to store this value with.
	 * @param value The value to store.
	 * @return V The value that was stored.
	 */
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


	/**
	 * This is a helper method to grow the number of buckets this HashMap is using.
	 * It will grow them by doubling the number of buckets present.
	 */
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
	
	
	/**
	 * This is a helper method to shift all the elements in a bucket to remove and gaps there may be.
	 * @param bucket The bucket to work on.
	 */
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



	/**
	 * This method will store all the key value pairs from a given map into this HashMap.
	 * @param m The map to copy key-value pairs from.
	 */
	@Override
	public void putAll(Map m) {
		for (Object key : m.keySet()) {
			Object value = m.get(key);
			put((K) key, (V) value);
		}
	}



	/**
	 * This method will remove the key-value pair from the map and return the value
	 * that was stored if any.
	 * @param key The key to remove and whose value to remove.
	 * @return The value at the given key, or null if there is no such key.
	 */
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



	/**
	 * This gets the number of key-value pairs currently stored in this map.
	 * @return The number of pairs stored.
	 */
	@Override
	public int size() {
		return size;
	}



	/**
	 * This will create and return a list of all the values stored in this map.
	 * @return A new list of all the values in this map.
	 */
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
