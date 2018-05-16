import java.awt.Color;
import java.awt.Graphics;

public abstract class Box {
	final static boolean debug = false;
	public final void draw(Graphics graph, double x, double y, double w) {
	  if (debug) {
	    graph.setColor(Color.red);
	    graph.drawRect((int) x, (int) y, (int) w, (int) (getAscent() + getDescent()));
	    graph.setColor(Color.black);
	  }
	  doDraw(graph, x, y, w);
	}
	
	protected abstract void doDraw(Graphics graph, double x, double y, double w);
	
	double width;
	double ascent;
	double descent;
	double stretchingCapacity;
	double getWidth() {return width;}
	double getAscent() {return ascent;}
	double getDescent() {return descent;}
	double getStretchingCapacity() {return stretchingCapacity;}
	public String toString() {
		String s = "[w=" + getWidth() + ", a=" + getAscent() +
	    	      ", d=" + getDescent() + ", sC=" + getStretchingCapacity() + "]";
		return s;
	}
}