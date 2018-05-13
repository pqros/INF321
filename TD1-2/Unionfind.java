
public class Unionfind {//All the i's and j's are inversed (row & column) !
	public static int[] equiv = new int[Percolation.length];
	public static int[] height = new int[Percolation.length];

	
	static void init() {
		for(int i = 0; i != Percolation.length; ++i) {equiv[i] = i; height[i] = 0;}
	}
	
	static int find(int a) {
		if(a != equiv[a]) equiv[a] = find(equiv[a]);
		return equiv[a];
	}
	
	static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if(a == b) return;
		if(height[a] > height[b]) {
			equiv[b] = a;
		}else if(height[a] < height[b]) {
			equiv[a] = b;
		}else {
			equiv[a] = b;
			height[b]++;
		}
	}
	
	static boolean is_eq(int a, int b) {
		if(find(a) == find(b)) {return true;} else {return false;}
	}
	
	public static void main(String[] args) {
		init();
	}
}


