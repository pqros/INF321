import java.util.*;

import edu.polytechnique.mjava.ast.*;
import edu.polytechnique.mjava.ast.expr.*;
import edu.polytechnique.mjava.ast.visitor.*;
import edu.polytechnique.xvm.asm.interfaces.*;
import edu.polytechnique.xvm.asm.opcodes.*;

public class ExprCodeGen extends DefaultExprVisitor {
	protected final CodeGen codegen;
	protected final Map<String, Integer> offsets;

	public void push(AsmInstruction asm) {
		this.codegen.pushInstruction(asm);
	}

	public void push(String label) {
		this.codegen.pushLabel(label);
	}

	@Override
	public void visit(EBool ebool) {
		final boolean value = ebool.getValue(); // Literal value
		if (value)
			this.push(new PUSH(1));
		else
			this.push(new PUSH(0));
	}

	@Override
	public void visit(EInt eint) {
		final int value = eint.getValue(); // Literal value
		this.push(new PUSH(value));
	}

	@Override
	public void visit(EVar evar) {
		final String name = evar.getName(); // Variable name
		this.push(new RFR(this.getVarOffset(name)));
	}

	public int getVarOffset(String name) {
		
			return offsets.get(name);
		
	}

	@Override
	public void visit(EBinOp ebin) {
		final Expr eleft = ebin.getLeft(); // Left operand
		final Expr eright = ebin.getRight(); // Right operand
		final EBinOp.Op op = ebin.getOp(); // Operator
		this.visit(eleft);
		this.visit(eright);
		switch (op) {
		case ADD:
			this.push(new ADD());
			break;
		case SUB:
			this.push(new SUB());
			break;
		case AND:
			this.push(new AND());
			break;
		case DIV:
			this.push(new DIV());
			break;
		case EQ:
			this.push(new EQ());
			break;
		case GE:
			this.push(new LT());
			this.push(new NOT());
			break;
		case GT:
			this.push(new POP());
			this.push(new POP());
			this.visit(eleft);
			this.visit(eright);
			this.push(new LT());
			break;
		case LE:
			this.push(new POP());
			this.push(new POP());
			this.visit(eleft);
			this.visit(eright);
			this.push(new LT());
			this.push(new NOT());
			break;
		case LT:
			this.push(new LT());
			break;
		case MUL:
			this.push(new MULT());
			break;
		case NEQ:
			this.push(new EQ());
			this.push(new NOT());
			break;
		case OR:
			this.push(new OR());
			break;
		}
	}

	@Override
	public void visit(EUniOp euni) {
		final Expr esub = euni.getExpr(); // Operand
		final EUniOp.Op op = euni.getOp(); // Operator

		switch (op) {
		case NOT:
			this.visit(esub);
			this.push(new NOT());
			break;

		case NEG:
			this.push(new PUSH(0));
			this.visit(esub);
			this.push(new SUB());
			break;
		}
	}

	public static String labelOfProcName(String name) {
		return String.format("__%s", name);
	}

	@Override
	public void visit(ECall ecall) {
		final String name = ecall.getName(); // Proc. name
		final List<Expr> args = ecall.getArgs(); // Proc. args
		for (Expr e : args) {
			this.visit(e);
		}
		this.push(new GSB(labelOfProcName(name)));
		for (Expr e : args) {
			this.push(new POP());
		}
		this.push(new PRX());
	}

	public ExprCodeGen(CodeGen codegen, Map<String, Integer> offsets) {
		this.codegen = codegen;
		this.offsets = offsets;
	}
}