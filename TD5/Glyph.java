import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class Glyph extends Box {

  final private static FontRenderContext frc = new FontRenderContext(null, false, false);
  final private Font font;
  final private char[] chars;
  final private Rectangle2D bounds;

  // classe à compléter

  public String toString() {
    return "Glyph(" + chars[0] + ")" + super.toString();
  }
  
  Glyph(Font font, char c){
	  this.font = font;
	  chars = new char[1];
	  chars[0] = c;
	  TextLayout layout = new TextLayout(""+chars[0], font, frc);
	  bounds = layout.getBounds();
	  ascent  = -bounds.getY();
	  descent = bounds.getHeight() + bounds.getY();
	  width = bounds.getWidth();	  
	  
  }
  
  double getStretchingCapacity() {
	  return 0;
  }

	@Override
	protected void doDraw(Graphics graph, double x, double y, double w) {
		graph.setFont(font);
		graph.drawChars(chars, 0, chars.length, (int)(x - bounds.getX()), (int)(y - bounds.getY()));
//		graph.drawChars(chars, 0, chars.length, (int)(x - bounds.getX()), (int)(bounds.getY()));
	}
  
}