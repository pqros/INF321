
public class Percolation {//all the rows and columns (i & j) are inversed!!! :(
	static int size = 10;
	static int length = size * size;
	static boolean[] grid = new boolean[length];
	static int nwrt; //number of white cases
	static void init() {
		for(int i = 0; i != length; ++i) {
			grid[i] = false;
			Unionfind.init();
		}
		
		nwrt = length;
	}
	
	static void print() {
		for(int i = 0; i != size; ++i) {
			for(int j = 0; j != size; ++j) if(grid[i*size + j]) System.out.print("*"); else System.out.print("-");
			System.out.println();
		}
	}
	
	static void random_shadow() {
		if(nwrt > 0) {
			double color = Math.random() * nwrt - 1;
			int cnt = 0;
			int ind = 0;
			while(cnt < color) {
				if(!grid[ind]) ++cnt;
				++ind;
			}//to skip the first (n-1) th white case
			while(grid[ind]) ++ind; //locate the n th white case
			grid[ind] = true; //turn it black!
			--nwrt; // dec #{white cases}
		}
	}
	
	static boolean[] seen = new boolean[length + 2];
	static boolean up ; 
	static boolean down;
	
	static boolean is_seen(int n) {
		if(seen[n]) return true; else return false;
	}
	
	static void detect_path_init(boolean[] seen) {
		for(int i = 0; i != length + 2; ++i) seen[i] = false;
	}
	
	static void detect_path(boolean[] seen, int n) { 
		//before using this function, up, down = false, seen is all false
		//to simplify the code, we use the last 2 elements of seen[] to record up & down
		seen[n] = true;seen[n] = true;
		if(grid[n]) {
			if(n - size >= 0) {
				if (!seen[n - size]) detect_path(seen, n - size);
			}else
				seen[length] = true;//we've arrived the first line
			
			if (n + size < length) {
				if (!seen[n + size]) detect_path(seen, n + size);
			}else
				seen[length + 1] = true;//we've arrived the last line
		
			if (n % size != 0 && !seen[n - 1]) detect_path(seen, n - 1);
			if (n % size != size - 1 && !seen[n + 1]) detect_path(seen, n + 1);
		}
	}
	
	/*static boolean is_percolation(int n) {
		detect_path_init(seen);
		detect_path(seen, n);
		if(seen[length] && seen[length+1]) return true; else return false;
	}*/
	
	static boolean is_percolation() {
		Unionfind.init();
		detect_path_init(seen);
		for(int i = 0; i != size; ++i) {
			propagate_union(i);
			if(grid[i]) for(int j = 0; j != size; ++j) if(Unionfind.is_eq(i, j - size + length)) return true;
		}
		return false;
	}
	
	static void propagate_union(int n) {	
		if(grid[n]) {
			if (n - size >= 0 && !seen[n - size]) {seen[n - size] = true; if(grid[n - size]) {Unionfind.union(n, n - size); propagate_union(n - size);}}	
			if (n + size < length && !seen[n + size]) {seen[n + size] = true; if(grid[n + size]) {Unionfind.union(n, n + size); propagate_union(n + size);}}
			if (n % size != 0 && !seen[n - 1]) {seen[n - 1] = true; if(grid[n - 1]) {Unionfind.union(n, n - 1); propagate_union(n - 1);}}
			if (n % size != size - 1 && !seen[n + 1]) {seen[n + 1] = true; if(grid[n + 1]) {Unionfind.union(n, n + 1); propagate_union(n + 1);}}
		}
	}
	
	static boolean is_percolation(int n) {
		//Unionfind.init(); // not needed if we only use is_percolation()
		//detect_path_init(seen); // not needed if we only use is_percolation()
		propagate_union(n);
		for(int i = 0; i != size; ++i) if(Unionfind.is_eq(i, n)) seen[length] = true;
		for(int i = 0; i != size; ++i) if(Unionfind.is_eq(length - 1 - i, n)) seen[length + 1] = true;
		if (seen[length] && seen[length + 1]) return true; else return false;
	}
	
	
	static double percolation() {
		init();
		Unionfind.init();
		int cnt = 0;
		while(cnt < 10 || !is_percolation()) {
			random_shadow();
			++cnt;
		}
		return (double) cnt/length;
	}
	
	static double montecarlo(int n) {
		double res = 0; // exp result
		for(int i = 0; i != n; ++i) res = (res * i + percolation())/(i + 1);
		return res;
	}
	
	public static void main(String[] args) {
		int n = 100;
//		int n = Integer.parseInt(args[0]);
//		for(int i = 0; i != length; ++i) if(i % 5 != 0 && i % 7 != 0) grid[i] = true;
//		print();
//		System.out.println(is_percolation());
//		System.out.println(is_percolation());
		long time1 = System.currentTimeMillis();
//		System.out.println("1");
		System.out.println ("After "+ n + " experiments, the average proportion of black cases is " + montecarlo(n));
//		System.out.println(percolation());
		time1 = System.currentTimeMillis() - time1;
		System.out.println("Runtime: " + time1);
	}
}
