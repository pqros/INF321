import java.util.*;

import edu.polytechnique.mjava.ast.*;
import edu.polytechnique.mjava.ast.expr.*;
import edu.polytechnique.mjava.ast.topdecl.*;
import edu.polytechnique.xvm.asm.opcodes.*;

public class ProgramCodeGen {
	public final CodeGen cg = new CodeGen();

	public static String labelOfProcName(String name) {
		return String.format("__%s", name);
	}

	public void visit(TProcDef proc) {
		final List<EVar> args = proc.getArgs(); // Proc. arguments
		final List<EVar> locals = proc.getLocals(); // Proc. locals
		final Instruction is = proc.getBody(); // Proc. body
		final String name = labelOfProcName(proc.getName());
		Map<String, Integer> offsets = new HashMap<String, Integer>();
		this.cg.pushLabel(name);

		int cnt = -args.size();
		for (EVar i : args) {
			offsets.put(i.getName(), cnt);
			++cnt;
		}
		cnt = 2;
		for (EVar i : locals) {
			offsets.put(i.getName(), cnt);
			++cnt;
		}
		offsets.put("!LOCAL!", locals.size());
		InstrCodeGen icg = new InstrCodeGen(this.cg, offsets);
		for (EVar i : locals) {
			this.cg.pushInstruction(new PUSH(0));
		}
		icg.visit(is);

//		for(EVar i : locals) this.cg.pushInstruction(new POP());
		if (name.equals(labelOfProcName("main")))
			this.cg.pushInstruction(new STOP());
	}

	public void visit(List<TProcDef> procs) {
		for (TProcDef proc : procs)
			this.visit(proc);
	}

	public ProgramCodeGen() {// final operation
		this.cg.pushInstruction(new GSB(labelOfProcName("main")));
		this.cg.pushInstruction(new STOP());
	}
}
