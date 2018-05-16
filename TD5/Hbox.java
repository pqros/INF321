import java.awt.Graphics;

public class Hbox extends Group {
	void add(Box b) {
		super.add(b);
		if (b.ascent > ascent)
			ascent = b.ascent;
		if (b.descent > descent)
			descent = b.descent;
		width += b.width;
	}

	Hbox(){
		width = 0;
	}
	protected void doDraw(Graphics graph, double x, double y, double w) {
		double mw = width;
		if (mw > w) {
			System.out.println("Attempt failed");
			return;
		}
		boolean bl = true;
		for (Box b : list) {
			if (b.getStretchingCapacity() != 0) {
				bl = false;
				break;
			}
		}
		double xtemp = x;
		double ytemp = y + ascent;
		double ecart = 0;
		if(bl == true) {
			if(list.size()>=2) ecart = (w-mw)/(list.size()-1);
			for (Box b : list) {
				b.doDraw(graph, xtemp, ytemp - b.getAscent(), b.getWidth());
				xtemp +=  b.getWidth()+ ecart;
			}
		}else {
			double sum = 0;
			for(Box b : list) {sum += b.getStretchingCapacity();}
			ecart = (w - mw) / sum;
			for (Box b : list) {
				b.doDraw(graph, xtemp, ytemp - b.getAscent(), b.getWidth());
				xtemp +=  b.getWidth() + b.getStretchingCapacity() * ecart;
			}
		}
	}
	
	public String toString() {
		return "HBox" + super.toString();
	}
}
