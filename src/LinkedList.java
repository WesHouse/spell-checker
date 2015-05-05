package edu.ncsu.csc316.wdhouseh.spellcheck;

public class LinkedList {
	
	private class Node {
		private String data;
		private Node next;
		
		private Node(String data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node head;
	
	public LinkedList() {
		head = null;
	}
	
	public void insert(String word) {
		if (head == null) {
			head = new Node(word, null);
		} else {
			Node cursor = head;
			while (cursor.next != null) {
				cursor = cursor.next;
			}
			cursor.next = new Node(word, null);
		}
	}
	
	//returns the number of probes.
	//the number is positive if the word was found, negative if it wasn't
	public int find(String word) { 
		int numProbes = 1;
		if (head == null) {
			return 0;
		} else {
			Node cursor = head;
			while (!cursor.data.equals(word)) {
				numProbes++;
				if (cursor.next == null && !cursor.data.equals(word)) {
					return -(numProbes);
				}
				cursor = cursor.next;
			}
			return numProbes;
		}
	}
	
	

}
