package edu.ncsu.csc316.wdhouseh.spellcheck;

public class HashTable {
	
	private static final int M_VALUE = 50291;
	private static final int R_VALUE = 2;
	
	private int totalProbes;
	private int numLookups;
	private LinkedList[] hashTable;
	
	public HashTable() {
		totalProbes = 0;
		numLookups = 0;
		hashTable = new LinkedList[M_VALUE];
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = new LinkedList();
		}
	}
	
	public boolean lookup(String word) {
		numLookups++;
		int hashValue = 0;
		for (int i = 0; i < word.length(); i++) {
			hashValue += word.charAt(i) * Math.pow(R_VALUE, i);
		}
		hashValue %= M_VALUE;
		int numProbes = hashTable[hashValue].find(word);
		totalProbes += Math.abs(numProbes);
		if (numProbes <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void insert(String word) {
		int hashValue = 0;
		for (int i = 0; i < word.length(); i++) {
			hashValue += word.charAt(i) * Math.pow(R_VALUE, i);
		}
		hashValue %= M_VALUE;
		hashTable[hashValue].insert(word);
	}
	
	public void delete() {
		
	}
	
	public int getTotalProbes() {
		return totalProbes;
	}
	
	public int getNumLookups() {
		return numLookups;
	}
	
	public int getProbesPerLookup() {
		return totalProbes / numLookups;
	}

}
