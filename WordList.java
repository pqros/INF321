class WordList {
	public Node content;

	WordList() {
		content = null;
	}

	WordList(Node n) {
		content = n;
	}

	WordList(String[] t) {
		for (String s : t) {
			insert(s);
		}
	}

	int length() {
		int len = 0;
		Node ntemp = content;
		while (ntemp != null) {
			++len;
			ntemp = ntemp.next;
		}
		return len;
	}

	void real_print() {
		System.out.println(print());
	}

	String print() {
		String str = "[";
		Node l = this.content;
		while (l != null) {
			str = str + l.head;
			l = l.next;
			if (l != null)
				str = str + ", ";
		}
		str += "]";
		return str;
	}

	void addFirst(String w) {
		Node ntemp = new Node(w, content);
		content = ntemp;
	}

	void addLast(String w) {
		if (content == null) {
			content = new Node(w, null);
			return;
		}
		Node ntemp = content;
		while (ntemp.next != null)
			ntemp = ntemp.next;
		ntemp.next = new Node(w, null);
	}

	String removeFirst() {
		if (content == null)
			return null;
		String stemp;
		stemp = content.head;
		content = content.next;
		return stemp;
	}

	String removeLast() {
		if (content == null)
			return null;
		String stemp;
		if (content.next == null) {
			stemp = content.head;
			content = null;
			return stemp;
		}
		Node ntemp = content;
		while (ntemp.next.next != null)
			ntemp = ntemp.next;
		stemp = ntemp.next.head;
		ntemp.next = null;
		return stemp;
	}

	void insert(String s) {
		if (content == null) {
			content = new Node(s, null);
			return;
		}
		if (s.compareTo(content.head) < 0) {
			Node l0 = new Node(s, content);
			content = l0;
			return;
		}
		Node ntemp = content;
		while (ntemp.next != null && s.compareTo(ntemp.next.head) >= 0)
			ntemp = ntemp.next;
		Node ltemp = new Node(s, ntemp.next);
		ntemp.next = ltemp;
	}

	boolean search(String s) {
		Node n = content;
		while (n != null) {
			if (n.head.equals(s))
				return true;
			n = n.next;
		}
		return false;
	}

	void insertionSort() {
		if (content == null)
			return;
		WordList wtemp = new WordList();
		while (content != null) {
			wtemp.insert(content.head);
			content = content.next;
		}
		content = wtemp.content;
	}

	String[] toArray() {
		String[] s = new String[length()];
		Node ntemp = content;
		for (int i = 0; i != length(); ++i) {
			s[i] = ntemp.head;
			ntemp = ntemp.next;
		}
		return s;
	}

	public static void main(String[] args) {
		WordList wl = new WordList();
		wl.content = new Node("foo", new Node("bar", new Node("baz", null)));
		wl.real_print();
		wl.insert("ddd");
		wl.insertionSort();
		wl.real_print();
	}
}