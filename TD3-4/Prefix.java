
public class Prefix {
	String[] t;
	final static String start = "<START>", end = "<END>", par = "<PAR>";
	int len;
	
	Prefix(int n){
		len = n;
		if(n <= 0) {t = null; return;}
		t = new String[n];
		for(int i = 0; i != n; ++i) t[i] = start;
	}
	
	static boolean eq(Prefix p1, Prefix p2) {
		if(p1.t.length != p2.t.length) return false;
		for(int i = 0; i != p1.t.length; ++i) if(!p1.t[i].equals(p2.t[i])) return false;
		return true;
	}
	
	public void show() {
		for(int i = 0; i !=t.length; ++i) System.out.print(t[i] + ' ');
		System.out.println();
	}
	
	public Prefix addShift(String w) {
		if(t.length == 0) return null;
		Prefix ptemp = new Prefix(t.length);
		for(int i = 0; i != t.length - 1; ++i) ptemp.t[i] = t[i + 1];
		ptemp.t[t.length - 1] = w;
		return ptemp;
	}
	
	public int hashCode() {
		int h = 0;
		for(int i = 0; i != t.length; ++i) h = 37 * h + t[i].hashCode();
		return h;
	}
	
	int hashCode(int n) {
		if(n <= 0) return 0;
		int temp = hashCode() % n;
		if(temp < 0) temp += n;
		return temp;
	}
	
	public static void main(String[] args) {
		Prefix p1 = new Prefix(3);
		p1.t[0] = start;
		p1.t[1] = par;
		p1.t[2] = end;
		Prefix p2 = new Prefix(3);
		p2.t[0] = start;
		p2.t[1] = start;
		p2.t[2] = end;
		Prefix p3 = new Prefix(4);
		p3.t[0] = start;
		p3.t[1] = par;
		p3.t[2] = end;
		p3.t[3] = end;
		System.out.println(p1.hashCode(13));
		System.out.println(p2.hashCode(13));
		System.out.println(p3.hashCode(13));
		
	}
}
