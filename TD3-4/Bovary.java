
public class Bovary {
	static HMap buildTable(String[] files, int n) {
		WordReader wr;
		HMap h = new HMap();
		Prefix p;
		for(String s : files) {
			wr = new WordReader("src/bovary/" + s);
			p = new Prefix(n);
			for (String w = wr.read(); w != null; w = wr.read()) {
				h.add(p, w);
				p = p.addShift(w);
			}
			h.add(p, Prefix.end);
		}
		return h;
	}
	
	static void generate(HMap t, int n) {
		String w;
		Prefix p = new Prefix(n);
		WordList wl;
		Node word;
		int l, ran;
		while(true) {
			wl = t.find(p);
			if(wl == null) {System.out.println(); System.out.println("Generation Failed..."); return;}
			l = wl.length(); word =wl.content;
			ran = (int)(Math.random() * l);
			for(int i = 0; i != ran; ++i) word = word.next;
			w = word.head;
			if(w.equals(Prefix.end)) {System.out.println(); break;}
			if(w.equals(Prefix.par)) {
					System.out.println(); 
					System.out.println();
			}
			else {
				System.out.print(w + ' ');
			}
			p = p.addShift(w);
		}
	}
	
	public static void main(String[] s) {
		String[] str = new String[35];
		for(int i = 1; i != 10; ++i) str[i - 1] = "0" + i + ".txt";
		for(int i = 10; i != 36; ++i) str[i - 1] = i + ".txt";
		for(int i = 0; i != 35; ++i) System.out.println(str[i]);
		HMap h = Bovary.buildTable(str, 4);
		
		generate(h, 4);
		/*Prefix p = new Prefix(3);
		WordReader wr = new WordReader("src/bovary/02.txt");
		int cnt = 0;
		for (String w = wr.read(); w != null; w = wr.read()) {
			System.out.println(w);
			++cnt;
			if(cnt >= 20) break;
			p = p.addShift(w);
			p.show();
		}*/
	}
}
