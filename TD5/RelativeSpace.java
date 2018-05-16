import java.awt.Font;

public class RelativeSpace extends Space{

	RelativeSpace(double s, Font f) {
		super(s * f.getSize(), 1.0);
		
	}
	
}
