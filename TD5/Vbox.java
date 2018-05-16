import java.awt.Color;
import java.awt.Graphics;

public class Vbox extends Group{
	double lineSkip;
	
	Vbox(double l){
		lineSkip = l;
		width = 0;
	}
	
	void add(Box b) {
		super.add(b);
		ascent += lineSkip + descent + b.ascent;
		descent = b.descent;
		if(b.width > this.width) this.width = b.width;
	}
	
	protected void doDraw(Graphics graph, double x, double y, double w) {
		double x0 = x, y0 = y;
		Hbox p;
		for(Box b : list) {
			b.doDraw(graph, x0, y0, w);
			y0 += b.ascent + b.descent + lineSkip;
		}
	}
	
	public String toString() {
		return "Vbox" + super.toString();
		
	}
}
