package com.graph.herdImmunity;

public class Deque<T>{
	private DLL<T> list = new DLL<>();
	public Deque() {
	}
	
	public void queueHead(T ele) {
		list.addFirst(ele);
	}
	
	public void queueTail(T ele) {
		list.addLast(ele);
	}
	
	public T popHead() {
		return list.removeFirst();
	}
	
	public T popTail() {
		return list.removeLast();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public int size() {
		return list.size();
	}
	
	public T peakHead() {
		return list.first();
	}
	
	public T peakTail() {
		return list.last();
	}
	
	public String toString() {
		return list.toString();
	}
}
