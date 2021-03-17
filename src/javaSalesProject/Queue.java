package javaSalesProject;

public class Queue {
	
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
	
	public Queue() {
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
	
	
	
	public void enqueue(Item itemToAdd) {
		if (isEmpty() == true) {
			first = new Node(itemToAdd);
			last = first;
		}
		else {
			last.next = new Node(itemToAdd);
			last = last.next;
		}
	}

	// We will likely remove these methods to replace them
	/*
	public void add(Item itemToAdd) {
		if (isEmpty() == true) {
			first = new Node(itemToAdd);
			last = first;
		}
		else {
			last.next = new Node(itemToAdd);
			last = last.next;
		}
	}
	
	
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
	
	
	//We will replace these with dequeue
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
	
	*/
	
	
	
	// Returns the first object in the queue (the element that has been in the queue the longest)
	public Item dequeue() {
		Node p = first;
		Item e = p.item; // Item to be returned
		first = p.next;
		return e;
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
	
	
	public Item peek(int index) {
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
		Queue queue = new Queue();
		
		Item item1 = new Item(25, "item1", 12);
		Item item2 = new Item(13, "item2", 21);
		Item item3 = new Item(64, "item3", 26);
		
		queue.enqueue(item1);
		queue.enqueue(item2);
		queue.enqueue(item3);
		
		for (int i=0; i<queue.size(); i++) {
			System.out.println(queue.peek(i).toString());
		}
		
		System.out.println("Dequeueing 1 element");
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		
		
		System.out.println("There should only be one item left at this point: ");
		for (int i=0; i<queue.size(); i++) {
			System.out.println(queue.peek(i).toString());
		}
		
		
	}

}