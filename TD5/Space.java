import java.awt.Graphics;

public class Space extends Box{
	
	Space(double w, double s){
		width = w;
		ascent = 0;
		descent = 0;
		stretchingCapacity = s;
	}
	@Override
	protected void doDraw(Graphics graph, double x, double y, double w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	double getAscent() {
		return 0;
	}
	
	@Override
	double getDescent() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Space" + super.toString();
	}
	
}
