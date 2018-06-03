import java.util.*;

import edu.polytechnique.mjava.ast.*;
import edu.polytechnique.mjava.ast.expr.*;
import edu.polytechnique.mjava.ast.visitor.*;
import edu.polytechnique.xvm.asm.interfaces.*;
import edu.polytechnique.xvm.asm.opcodes.*;

public class Test {
	public static String labelOfProcName(String name) {
		return String.format("P%s", name);
	}

	public static void main(String[] s) {
		String str = "abc";
		System.out.println(labelOfProcName(str));
	}
}
