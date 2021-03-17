package javaSalesProject;

public class Stack {
	
	private class Node {
		
		private Item item;
		private Node next;
		private Node prev;
		
		Node(Item item, Node next, Node prev) {
			this.item = item;
			this.next = next;
			this.prev = prev;
		}
		
		Node(Item item) {
			this(item, null, null);
		}
	}
	
	private Node first;
	private Node last;
	
	public Stack() {
		first = null;
		last = null;
	}
	
	
	public boolean isEmpty() {
		if (first == null) {
			return true;
		}
		return false;
	}
	
	public int size() {
		int count = 0;
		Node p = first;
		while (p != null) {
			count++;
			p = p.next;
		}
		return count;
	}
	
	
	public void push(Item itemToAdd) {
		if (isEmpty() == true) {
			first = new Node(itemToAdd);
			last = first;
		}
		else {
			last.next = new Node(itemToAdd);
			last = last.next;
		}
	}
	
	
	/*
	
	public void add(int index, Item itemToAdd) {
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		if (index == 0) {
			Node temp = new Node(itemToAdd);
			temp.next = first;
			first.prev = temp;
			first = temp;
		}
		else {
			Node pred = first;
			for (int k = 1; k <= index - 1; k++) {
				pred = pred.next;
			}
			Node succ = pred.next;
			Node nodeToAdd = new Node(itemToAdd, succ, pred);
			pred.next = nodeToAdd;
			if (succ == null)
				last = nodeToAdd;
			else
				succ.prev = nodeToAdd;
		}
	}
	
	*/
	
	
	public Item pop() {
		Node p = last;
		Item e = p.item;	// The grabbing the item that we are to return
		if (p.prev != null) {
			p.prev = last;
		}
		
		// How to handle the popping of an element when there is only one element in the stack?
		else {
			remove(p.item);
		}
		return p.item;
	}
	
	
	// The remove methods will get replaced with the pop method
	/*
	public Item remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}

		Node target = first;
		for (int k = 1; k <= index; k++)
			target = target.next;

		Item element = target.item;
		Node pred = target.prev;
		Node succ = target.next;

		if (pred == null)
			first = succ;
		else
			pred.next = succ;

		if (succ == null)
			last = pred;
		else
			succ.prev = pred;

		return element;
	}
	
	
	*/
	
	
	public boolean remove(Item itemToBeRemoved) {
		if (isEmpty() == true) {
			return false;
		}

		Node target = first;
		while (target != null && !itemToBeRemoved.equals(target.item))
			target = target.next;

		if (target == null)
			return false;

		Node pred = target.prev; 
		Node succ = target.next; 

		if (pred == null)
			first = succ;
		else
			pred.next = succ;

		if (succ == null)
			last = pred;
		else
			succ.prev = pred;

		return true;
	}
	
	
	
	/**
	 * 
	 * When supplied a given item, it will return the index at 
	 * which that item is located in the linked list. Returns -1
	 * if that item can't be found in the list
	 * @param item
	 * @return
	 */
	
	public int indexOf(Item item) {
		int count = 0;
		Node p = first;
		for (count = 0; p.item != item; count++) {
			p = p.next;
		}
		if (p.item == item) return count;
		else return -1;
	}
	
	
	public Item get(int index) {
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		
		Node p = first;
		for (int i = 0; i <= index; i++) {
			if (i == index) {
				return p.item;
			}
			p = p.next;
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		Stack stack = new Stack();
		
		Item item1 = new Item(24, "item1", 25);
		Item item2 = new Item(24, "item2", 25);
		Item item3 = new Item(24, "item3", 25);
		
		stack.push(item1);
		stack.push(item2);
		stack.push(item3);
		
		for (int i = 0; i < stack.size(); i++) {
			System.out.println(stack.get(i));
		}
		
		System.out.println("popping an item off the stack");
		Item e = stack.pop();
		System.out.println(e.toString());
		
		System.out.println("Remaining elements in the stack: ");
		for (int i = 0; i < stack.size(); i++) {
			System.out.println(stack.get(0).toString());
		}
		
	}

}
