
public class Node {
	String head;//this is value
	Node next;
		
	static int length_rec(Node l) {
		if (l == null) return 0;
		return (1 + length_rec(l.next));
	}
		
	static int length(Node l) {
		if(l == null) return 0;
		int len = 1;
		while(l.next != null) {l = l.next; ++len;}
		return len;
	}
	
	

	
	static void addLast(String s, Node l) {
		if(l == null) {l = new Node(s, null); return;}
		while(l.next != null) l = l.next;
		l.next = new Node(s, null);
	}
	
	static Node insert(String s, Node l) {	
		if(l == null) return new Node(s, null);
		if(s.compareTo(l.head) < 0) {Node l0 = new Node(s, l); return l0;}
		Node lr = l;
		while(l.next != null && s.compareTo(l.next.head) >= 0) l = l.next;
		Node ltemp = new Node(s, l.next);
		l.next = ltemp;
		return lr;
	}
	
	static Node insertionSort(Node l) {
		 if(l == null) return l;
		 Node lr = null;
		 while(l != null) {
			 lr = insert(l.head, lr);
			 l = l.next;
		 }
		 return lr;
	}
	
	static String printNodes(Node l) {
	    String str = "[";
	    while(l != null) {
	    	str = str + l.head;
	    	l = l.next;
	    	if(l != null) str = str + ", ";
	    }
	    str += "]";
	    return str;
	}
	
	static WordList foobar = new WordList(new Node("foo", new Node("bar", new Node("baz", null))));
	
	Node(String head, Node next) {
		this.head = head;
		this.next = next;
	}
	
	static Node merge(Node l1, Node l2) {
		if(l1 == null) return l2;
		if(l2 == null) return l1;
		Node l = new Node("", null);
		while(l1 != null && l2 != null) {
			if(l1.head.compareTo(l2.head) <= 0) {addLast(l1.head, l); l1 = l1.next;}else {addLast(l2.head, l); l2 = l2.next;}
		}
		while(l1 != null) {addLast(l1.head, l); l1 = l1.next;}
		while(l2 != null) {addLast(l2.head, l); l2 = l2.next;}
		return l.next;
	}
	
	
	public static void main(String[] args) {
		Node llll = null;
		Node.addLast("aaa",llll);
		Node.addLast("bbbn", llll);
		Node ll = null;
		ll = Node.insert("bbc", ll);
		ll = Node.insert("add", ll);
		ll = Node.insert("ccd", ll);
		ll = Node.insert("aa", ll);
		Node l = new Node("bb", new Node("cc", new Node("aa", null)));
		l = insertionSort(l);
		Node lll = merge(l, ll);
		System.out.println(printNodes(lll));
	}
}
