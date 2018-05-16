import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Group extends Box{
	protected final LinkedList<Box> list = new LinkedList<Box>();

	@Override
	protected void doDraw(Graphics graph, double x, double y, double w) {
		// TODO Auto-generated method stub
		
	}
	
	void add(Box b) {
		list.add(b);
	}
	
	Group(){
		
	}
	
	protected double getWidth() {return width;}
	protected double getAscent() {return ascent;}
	protected double getDescent() {return descent;}
	protected double getStretchingCapacity() {return stretchingCapacity;}
	
	@Override
	public String toString() {
		String s = "";
		for(Box b : list) {
			s = s + b.toString() + ",\n";
		}
		s = s.replaceAll("(?m)^", "\t");
		return super.toString() + "{\n" + s + "}";
	}
}
