import java.util.*;

import edu.polytechnique.mjava.ast.*;
import edu.polytechnique.mjava.ast.expr.*;
import edu.polytechnique.mjava.ast.instruction.*;
import edu.polytechnique.mjava.ast.visitor.*;
import edu.polytechnique.xvm.asm.opcodes.*;

public class InstrCodeGen extends DefaultInstructionVisitor {
	private static int labelc = 0;

	public static String generateLabel() {
//		// Generate a fresh label using `labelc'.
//		// For example, lXXX where XXX is the value of labelc.
//		// Two generated labels should never be equal.
//		// A label must start with a lowercase letter.
		++labelc;
		return "l" + labelc;
	}

//    public static String toABC(int n) {
//        if (n < 0)
//            return null;
//        int q = n / 26;
//        int r = n % 26;
//        char letter = (char) ((int) 'a' + r);
//        if (q == 0) {
//            return "" + letter;
//        } else {
//            return toABC(q - 1) + letter;
//        }
//    }
//
//    public static String generateLabel() {
//        String s = toABC(labelc);
//        labelc++;
//        return s;
//    }
    
	protected final ExprCodeGen codegen;

	@Override
	public void visit(IIf iif) {
		final Expr cond = iif.getCondition(); // Condition
		final Instruction i1 = iif.getIftrue(); // 'then' branch
		final Instruction i2 = iif.getIffalse(); // 'else' branch
		String a = generateLabel();
		String b = generateLabel();
		String c = generateLabel();
		String d = generateLabel();
		this.codegen.visit(cond);
		this.codegen.push(new GTZ(a));
		this.visit(i1);
		this.codegen.push(new GTO(b));
		this.codegen.push(a);
		this.visit(i2);
		this.codegen.push(b);
	}

	@Override
	public void visit(IWhile iwhile) {
		final Expr cond = iwhile.getCondition(); // Loop condition
		final Instruction body = iwhile.getBody(); // Loop body

		String lbl1 = generateLabel();
		String lbl2 = generateLabel();

		// Push start of loop label
		this.codegen.push(lbl1);
		// Assemble condition
		this.codegen.visit(cond);
		// If condition is 0, jump to lbl2
		this.codegen.push(new GTZ(lbl2));
		// Generate code for the body
		this.visit(body);
		this.codegen.push(new GTO(lbl1));
		// this.codegen.push(lbl2);
	}

	@Override
	public void visit(IBlock iblock) {
		final List<Instruction> is = iblock.getBody(); // Seq of instructions
		for (Instruction i : is) {
			this.visit(i);
		}
	}

	@Override
	public void visit(IAssign iassign) {
		final String target = iassign.getLName().orElse(null); // Lvalue (can be null)
		final Expr expr = iassign.getRvalue(); // Expr to assign
		if (target == null)
			return;
		this.codegen.visit(expr);
		this.codegen.push(new WFR(this.codegen.getVarOffset(target)));
	}

	@Override
	public void visit(IPrint iprint) {
		final Expr expr = iprint.getExpr(); // Expr to print
		this.codegen.visit(expr);
		this.codegen.push(new PXR());
	}

	public InstrCodeGen(CodeGen codegen, Map<String, Integer> offsets) {
		this.codegen = new ExprCodeGen(codegen, offsets);
	}

	public InstrCodeGen(ExprCodeGen codegen) {
		this.codegen = codegen;
	}

	@Override
	public void visit(IReturn ri) {
		final Optional<Expr> result = ri.getResult(); // Proc. result
		if (result.isPresent()) {
			this.codegen.visit(result.get());
			this.codegen.push(new PXR());
		}
		for (int j = 0; j < this.codegen.offsets.get("!LOCAL!"); j++)
			this.codegen.push(new POP());
		this.codegen.push(new RET());
	}
}
