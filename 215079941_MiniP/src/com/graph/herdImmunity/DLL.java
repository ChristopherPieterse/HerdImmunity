package com.graph.herdImmunity;

public class DLL<T>{
	private static class Node<T>{
		T element;
		Node<T> next;
		Node<T> prev;
		public T getElement() {
			return element;
		}
		public void setElement(T element) {
			this.element = element;
		}
		public Node<T> getNext() {
			return next;
		}
		public void setNext(Node<T> next) {
			this.next = next;
		}
		public Node<T> getPrev() {
			return prev;
		}
		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}
		public Node(T ele, Node<T> n, Node<T> p) {
			element = ele;
			next = n;
			prev = p;
		}
		public String toString() {
			return element.toString();
		}
	}
	
	Node<T> header;
	Node<T> trailer;
	int size;
	
	public DLL() {
		size = 0;
		header = new Node<>(null,null,null);
		trailer = new Node<>(null,null,header);
		header.setNext(trailer);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public T first() {
		if(isEmpty()) {
			return null;
		}
		return header.getNext().getElement();
	}
	
	public T last() {
		if(isEmpty()) {
			return null;
		}
		return trailer.getPrev().getElement();
	}
	
	public void addFirst(T ele) {

		if(isEmpty()) {
			Node<T> newN = new Node<>(ele,trailer,header);
			trailer.setPrev(newN);
			header.setNext(newN);
		}else {
			Node<T> newN = new Node<>(ele,header.getNext(),header);
			header.getNext().setPrev(newN);
			header.setNext(newN);
		}
		size++;
	}
	
	public void addLast(T ele) {

		if(isEmpty()) {
			Node<T> newN = new Node<>(ele,trailer,header);
			trailer.setPrev(newN);
			header.setNext(newN);
		}else {
			Node<T> newN = new Node<>(ele,trailer,trailer.getPrev());
			trailer.getPrev().setNext(newN);
			trailer.setPrev(newN);
		}
		size++;
	}
	
	public T removeFirst() {
		if(isEmpty()) {
			return null;
		}
		T ele = header.getNext().getElement();
		Node<T> after = header.getNext().getNext();
		header.setNext(after);
		after.setPrev(header);
		size--;
		return ele;
	}
	
	public T removeLast() {
		if(isEmpty()) {
			return null;
		}
		T ele = trailer.getPrev().getElement();
		Node<T> before = trailer.getPrev().getPrev();
		trailer.setPrev(before);
		before.setNext(trailer);
		size--;
		return ele;
	}
	
	public String toString() {
		String info = "";
		Node<T> curr = header.getNext();
		while(curr!=trailer) {
			info = info + " " + curr.toString();
			curr = curr.getNext();
		}
		return info;
	}

}
