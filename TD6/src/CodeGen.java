import java.util.*;

import edu.polytechnique.xvm.asm.*;
import edu.polytechnique.xvm.asm.interfaces.*;

public final class CodeGen {
  public Vector<AsmInstruction>  instructions;
  public Map<String, Integer> labels;

  public CodeGen() {
    this.instructions = new Vector<AsmInstruction>();
    this.labels = new HashMap<String, Integer>();
  }

  public void pushLabel(String label) {
    labels.put(label, instructions.size());
  }

  public void pushInstruction(AsmInstruction asm) {
    instructions.add(asm);
  }

  @Override
  public String toString() {
    return Printer.programToString(this.instructions, this.labels);
  }
}
