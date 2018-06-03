import java.util.stream.*;

import edu.polytechnique.mjava.parser.*;
import edu.polytechnique.mjava.toplevel.*;
import edu.polytechnique.mjava.toplevel.MJavaTop.*;
import edu.polytechnique.mjava.typing.*;
import edu.polytechnique.mjava.typing.exn.*;
import edu.polytechnique.xvm.asm.opcodes.*;

public class TestInstrCodeGen {
  // Modify this instruction at will
  public final static String DEFAULT =
    "< x = 0; y = 10; >" + // Local variables declaration
    "if (x < y) x = x + 1; while (x < y) { x = x + 1; y = y - 1; }";
  
  public static void main(String[] args)
      throws MJavaParseError, MJavaTypingError
  {
    // Get input expression
    String input = args.length == 0 ? DEFAULT : String.join(" ", args);
    
    // Parse & type input instruction
    InstrWithCtxt is = MJavaTop.parseAndTypeInstr(input);

    // Create code generator
    CodeGen cg = new CodeGen();

    // Push local variables initialization
    // (in order of declaration from left-to-right)
    for (EVarInit vinit : is.getCtxt())
      cg.pushInstruction(new PUSH(vinit.getValue()));

    // Create instruction code generator using local variables `vars'
    InstrCodeGen icg = new InstrCodeGen(cg,
        IntStream
          .range(0, is.getCtxt().size())
          .mapToObj(i -> new Integer(i))
          .collect(Collectors.toMap(
                x -> is.getVInit(x).getName(),
                x -> x)));

    // Generate code for instruction `i'
    is.getInstruction().accept(icg);
    cg.pushInstruction(new STOP());

    // Display generated XVM code
    System.out.print(cg.toString());
  }
}
