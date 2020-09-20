//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: p3b - Hash Table Performance
// Files: HashTable.java, HashTableTest.java, DataStructureADT.java,
//        HashTableADT.java, IllegalNullKeyException.java,
//        KeyNotFoundException.java, MyProfiler.java,
//        SampleProfilerApplication.java
//
// Course: CS 400, Spring 2019
// Author: Nishant Yadav
// Email: nsyadav@wisc.edu
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


// Used as the data structure to test our hash table against Tree Map
import java.util.TreeMap;

/**
 * Profiler compares performance of my Hash Table with Java's tree map
 * 
 * @author Nishant Yadav
 *
 * @param <K>
 * @param <V>
 */
public class MyProfiler<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable; // my hash table
	TreeMap<K, V> treemap; // Java tree map

	/**
	 * Constructor initializes hashtable and treemap
	 */
	public MyProfiler() {
	  hashtable = new HashTable();
	  treemap = new TreeMap();
	}

	/**
	 * Inserts (key, value) into hashtable and treemap
	 * 
	 * @param key
	 * @param value
	 */
	public void insert(K key, V value) {
	  // insert into hashtable
	  try{
	    hashtable.insert(key, value);
	  }
	  catch(Exception e) {
	    
	  }
	  // insert into treemap
	  treemap.put(key, value);
	}

	/**
	 * Retrieves value from hashtable and treemap given key
	 * 
	 * @param key
	 */
	public void retrieve(K key) {
	  // get from hashtable
	  try {
	    hashtable.get(key);
	  }
      catch(Exception e) {
        
      }
	  // get from treemap
	  treemap.get(key);
	}

	/**
	 * Main method inserts and retrieves for a specified number of elements
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		    // number of elements to insert and retrieve
			int numElements = Integer.parseInt(args[0]);

			// new profile object
			MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
			// insert into profile numElements times
			for(int i = 0; i < numElements; i++) {
			  profile.insert(i, i);
			}
			// retrieve numElements times
			for(int i = 0; i < numElements; i++) {
			  profile.retrieve(i);
			}

			// print number of (key, value) pairs inserted and retrieved
			String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
			System.out.println(msg);
		} catch (Exception e) {
			System.out.println("Usage: java MyProfiler <number_of_elements>");
			System.exit(1);
		}
	}
}
