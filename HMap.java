
public class HMap {
	int len = 0;
	EntryList[] t;
	int size = 0;
	
	
	HMap() {
		len = 20;
		t = new EntryList[20];
	}
	
	HMap(int n){
		if(n <= 0) {len = 20;}else {len = n;}
		t = new EntryList[n];
	}
	
	void addSimple(Prefix key, String w) { //add a new element in the hash table
		
		int index = key.hashCode(len);
		EntryList e = t[index];
//		if(e == null) ++size;
		while(e != null && !Prefix.eq(e.head.key, key)) e = e.next;
		if(e == null) { //if the prefix isn't added yet, make it up!
			WordList wtemp = new WordList();
			wtemp.addLast(w);
			Entry etemp = new Entry(key, wtemp);
			t[index] = new EntryList(etemp, t[index]);
			++size;
		}else {
			e.head.value.addFirst(w);
			
		}
	}
	
	WordList find(Prefix key) {
		int index = key.hashCode(len);
		EntryList ttemp = t[index];
		while(ttemp != null && !Prefix.eq(key, ttemp.head.key)) ttemp = ttemp.next;
		if(ttemp == null) return null;
		return ttemp.head.value;
	}
	
	void show() {
		EntryList ttemp;
		System.out.println("Table size:" + len + " , Occupied cases:" + size);
		for(int i = 0; i != len; ++i) {
			if(t[i] != null) {
				ttemp = t[i];
				while(ttemp != null) {ttemp.head.key.show(); ttemp.head.value.real_print(); ttemp = ttemp.next;}
				System.out.println("--------------------");
			}
		}
	}
	
	void rehash(int n) {
		if(n == len) return;
		HMap h = new HMap(n);
		EntryList ttemp;
		for(int i = 0; i != len; ++i) {
			ttemp = t[i];
			while(ttemp != null) {
				h.add_nocheck(ttemp);
				ttemp = ttemp.next;
			}
		}
		len = n;
		t = h.t;
	}
	
	void add_nocheck(EntryList w) {
		int n = w.head.key.hashCode(len);
		t[n] = new EntryList(w.head, t[n]);
	}
	
	void add(Prefix key, String w) {
		if(size >= 3 * len / 4) rehash(2 * len);
		int index = key.hashCode(len);
		EntryList e = t[index];
//		if(e == null) ++size;
		while(e != null && !Prefix.eq(e.head.key, key)) e = e.next;
		if(e == null) {
			WordList wtemp = new WordList();
			wtemp.addLast(w);
			Entry etemp = new Entry(key, wtemp);
			t[index] = new EntryList(etemp, t[index]);
			++size;
		}else {
			e.head.value.addFirst(w);
			
		}	
	}
	
	public static void main(String[] s) {
		HMap hm = new HMap(13);
		Prefix p1 = new Prefix(3);
		p1.t[0] = Prefix.start;
		p1.t[1] = Prefix.par;
		p1.t[2] = Prefix.end;
		Prefix p2 = new Prefix(3);
		p2.t[0] = Prefix.start;
		p2.t[1] = Prefix.start;
		p2.t[2] = Prefix.end;
		Prefix p3 = new Prefix(4);
		p3.t[0] = Prefix.start;
		p3.t[1] = Prefix.par;
		p3.t[2] = Prefix.end;
		p3.t[3] = Prefix.end;
		Prefix p4 = new Prefix(3);
		p4.t[0] = Prefix.start;
		p4.t[1] = Prefix.par;
		p4.t[2] = Prefix.end;
		Prefix p5 = new Prefix(1);
		p5.t[0] = "p5";
		Prefix p6 = new Prefix(1);
		p6.t[0] = "p6";		Prefix p7 = new Prefix(1);
		p7.t[0] = "p7";		Prefix p8 = new Prefix(1);
		p8.t[0] = "p8";		Prefix p9 = new Prefix(1);
		p9.t[0] = "p8";		
		hm.addSimple(p1, "1");
		hm.addSimple(p2, "2");
		hm.addSimple(p3, "3");
		hm.addSimple(p4, "41");
		hm.addSimple(p4, "43");
		hm.addSimple(p4, "43");
		hm.addSimple(p5, "5");
		hm.addSimple(p8, "8");
		hm.addSimple(p9, "9");
		hm.addSimple(p7, "7");
		hm.addSimple(p6, "6");
		hm.show();
		
		
		//hm.find(p4).real_print();
	}
}
