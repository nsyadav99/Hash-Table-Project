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

/**
 * Generic hash table utilizing CHAINED BUCKET: array of linked nodes
 * 
 * @author Nishant Yadav
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  // Data fields
  private int initialCapacity; // initial capacity of table
  private double loadFactorThreshold; // load factor threshold
  private Object[] map; // map containing key, value pairs in table
  private int numNodes; // number of nodes

  /**
   * Inner class holds key, value pairs
   */
  private class Node {
    private K key;
    private V value;
    private Node next;

    private Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }


  /**
   * Default constructor initializes table with capacity 10 and load factor threshold .75
   */
  public HashTable() {
    initialCapacity = 10;
    loadFactorThreshold = .75;
    map = new Object[initialCapacity];
  }

  /**
   * Constructor takes in initial capacity and load factor threshold Initializes hash table with
   * initial capacity
   * 
   * @param initialCapacity     of table
   * @param loadFactorThreshold of the table
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.initialCapacity = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    map = new Object[initialCapacity];
  }

  /**
   * Add key, value pair to table and increment numKeys If key is null throw IllegalNullKeyException
   * If key is already in table, replace value with new value
   * 
   * @param key   to be inserted
   * @param value to be inserted
   * @throws IllegalNullKeyException
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException {
    // if key is null throw exception
    if (key == null)
      throw new IllegalNullKeyException();

    // index to put node into
    int index = key.hashCode() % map.length;

    // if key is in ds, replace value with given value
    if (map[index] != null) {
      Node temp = (Node) map[index]; // head node at index
      // advance to node containing key
      while (temp != null) {
        // if key = current node key, replace value
        if (temp.key.equals(key)) {
          temp.value = value;
          return;
        }
        // else advance to next node at index
        temp = temp.next;
      }
    }


    // insert node with chained bucket
    // if no nodes at index, insert node
    if (map[index] == null)
      map[index] = new Node(key, value);
    // else advance to tail at index
    else {
      // head node at index
      Node temp2 = (Node) map[index];
      // advance to tail node
      while (temp2.next != null) {
        temp2 = temp2.next;
      }
      // set next to new node being inserted
      temp2.next = new Node(key, value);
    }

    // increment nodes
    numNodes++;

    // if loadFactor > threshold, increase size and rehash
    if (getLoadFactor() > loadFactorThreshold) {
      // temp map array
      Object[] temp = map;
      // increase capacity
      map = new Object[map.length * 2 + 1];
      // iterate through temp and rehash
      for (int i = 0; i < temp.length; i++) {
        // if current element is not null
        if (temp[i] != null) {

          // new temp node for head of list at i
          Node temp3 = (Node) temp[i];

          // advance through list and insert each node in new array
          while (temp3 != null) {
            // new index of key at current node
            int newIndex = temp3.key.hashCode() % map.length;

            // if spot at new index is null, insert temp3
            if (map[newIndex] == null)
              map[newIndex] = new Node(temp3.key, temp3.value);
            else {
              // head node at index
              Node temp4 = (Node) map[newIndex];
              // advance to tail
              while (temp4.next != null) {
                temp4 = temp4.next;
              }
              // insert new node
              temp4.next = new Node(temp3.key, temp3.value);
            }

            // go to next node at i
            temp3 = temp3.next;

          }

        }
      }
    }

  }

  /**
   * Remove node containing key If key is null throw IllegalNullKeyException
   * 
   * @param key to be removed
   * @throws IllegalNullKeyException
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    // if key is null throw exception
    if (key == null)
      throw new IllegalNullKeyException();

    // find node and remove
    for (int i = 0; i < map.length; i++) {
      // if node at i is not null
      if (map[i] != null) {
        // if current node key = key
        if (((Node) map[i]).key.equals(key)) {
          // remove node
          map[i] = null;
          // decrement num nodes
          numNodes--;
          // return true for successful removal
          return true;
        }
      }
    }

    // return false if not removed
    return false;
  }

  /**
   * Get value of node containing key
   * 
   * If key is null throw IllegalNullKeyException If key is not in table throw KeyNotFoundException
   * 
   * @param key to get
   * @throws IllegalNullKeyException, KeyNotFoundException
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // if key is null throw exception
    if (key == null)
      throw new IllegalNullKeyException();

    // index of node containing key
    int index = Math.abs(key.hashCode()) % map.length;
    // head node at index
    Node temp = (Node) map[index];
    while (temp != null) {
      // if current node key = key, return value
      if (temp.key.equals(key))
        return temp.value;
      // else go to next node
      temp = temp.next;
    }

    // throw exception if key not found
    throw new KeyNotFoundException();
  }

  /**
   * Return number of keys
   */
  @Override
  public int numKeys() {
    return numNodes;
  }

  /**
   * Return load factor threshold
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Return load factor
   */
  @Override
  public double getLoadFactor() {
    return (double) numNodes / (double) map.length;
  }

  /**
   * Return capacity
   */
  @Override
  public int getCapacity() {
    return map.length;
  }

  /**
   * Return 5 for CHAINED BUCKET: array of linked nodes
   */
  @Override
  public int getCollisionResolution() {
    return 5;
  }

}
