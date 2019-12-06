
import java.util.ArrayList;

public class Hashtable {
	// initialize variables
	private ArrayList<Hashnode> buckets;
	private static final double LOAD_FACTOR = 0.7;
	private int numBuckets;
	private int size;

	// constructor
	public Hashtable() {
		buckets = new ArrayList<Hashnode>();
		numBuckets = 10;
		size = 0;

		// initialize all the buckets to null
		for (int i = 0; i < numBuckets; i++) {
			buckets.add(i, null);
		}
	}

	public boolean containsKey(String key) {
		int bucket = getBucketIndex(key);
		Hashnode head = buckets.get(bucket);
		// returns true if a key/value object pair(with the key matching the argument
		// and any value)
		while (head != null) {
			if (head.key.equals(key)) {
				return true;
			}
			head = head.next;
		}
		// returns false otherwise
		return false;
	}

	// returns a value of a key
	public String get(String key) {
		int bucket = getBucketIndex(key);
		Hashnode head = buckets.get(bucket);
		// search for the key in the chain
		while (head != null) {
			if (head.key.equals(key)) {
				return head.value;
			}
			head = head.next;
		}
		// if the key is not found, return null
		return null;
	}

	// function to find the index for a key
	private int getBucketIndex(String key) {
		int hash = key.hashCode();
		int index = hash % numBuckets;
		return index;
	}

	// puts a key value pair into the hash
	public void put(String key, String value) {
		// finds the head of the chain for a given key
		int bucket = getBucketIndex(key);
		Hashnode head = buckets.get(bucket);

		// checks if the key exists in the list already
		while (head != null) {
			if (head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}

		// inserts the key into the chain
		head = buckets.get(bucket);
		Hashnode newNode = new Hashnode(key, value);
		newNode.next = head;
		buckets.set(bucket, newNode);

		// if the load facter goes beyond the threshold of the buckets, then we double
		// the size of the hash table
		if ((1.0 * size) / numBuckets >= LOAD_FACTOR) {
			ArrayList<Hashnode> temp = buckets;
			buckets = new ArrayList<Hashnode>();
			numBuckets *= 2;
			int tempSize = size;
			size = 0; // sets the size back to 0
			for (int i = 0; i < numBuckets; i++)
				buckets.add(null);

			for (int i = 0; i < tempSize; i++) {
				Hashnode headNode = temp.get(i);
				if (headNode != null) {
					while (headNode != null) {
						put(headNode.key, headNode.value);
						headNode = headNode.next;
					}
				}
			}
		}
	}

	// method to remove the key given in the parameter
	public String remove(String key) throws Exception {
		// find the index of the key given
		int bucket = getBucketIndex(key);
		// get the head
		Hashnode head = buckets.get(bucket);
		if (head == null) {
			throw new Exception("Out of bounds."); // if the key is not found in the hashtable, then throw an exception
		}

		// searches for the given key in the chain
		Hashnode prev = null;
		while (head != null && !head.key.equals(key)) {
			prev = head;
			head = head.next;
		}
		// removes the key
		if (prev != null) {
			prev.next = head.next;
		} else {
			buckets.set(bucket, head.next);
		}

		return head.value;
	}
}