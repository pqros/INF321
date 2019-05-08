import java.util.*;

public class Calculator {
	public java.util.LinkedList<Double> results;
	public Stack<Operator> operators;
	public Stack<Double> numbers;
	private boolean isNumber;
	private boolean hasDot;
	private int mantisse;
	char lastToken;

	// Q2
	Calculator() {
		results = new java.util.LinkedList<Double>();
		operators = new Stack<Operator>();
		numbers = new Stack<Double>();
		isNumber = false;
		hasDot = false;
		mantisse = 0;
		lastToken = '+';
	}

	public String toString() {
		return numbers.toString() + "\n" + operators.toString();// + "\n" + results.toString();
	}

	// Q3
	public Double getResult() {
		if (numbers.isEmpty())
			return 0.0;
		return numbers.peek();
	}

	// Q4
	public void read(char c) throws ParsingException {
		if (((c >= '0') && (c <= '9')) || (c == '.')) {
			if (!isNumber) {
				if (lastToken == '=')
					numbers.pop();
				numbers.push(0.0);
				isNumber = true;
				lastToken = '0';
			}
			if (c == '.')
				read();
			else
				read(c - '0');

		} else {

			isNumber = false;
			hasDot = false;
			if (isOperator(c)) {
				read(toOperator(c, lastToken));
			} else if (c == '=') {
				execute();
				lastToken = '=';
				// assert (operators.isEmpty()) && (numbers.size() == 1);
			} else if (c == 'C') {
				operators = new Stack<Operator>();
				numbers = new Stack<Double>();
				isNumber = false;
				hasDot = false;
				mantisse = 0;
				lastToken = '+';
			} else if (c != ' ') {
				throw new ParsingException(c);
			}
		}
	}

	public void read(int i) {
		Double d = numbers.pop();
		if (!hasDot)
			d = d * 10 + i;
		else {
			++mantisse;
			d = d + i * Math.pow(10, -mantisse);
		}
		numbers.push(d);
	}

	public void read() throws ParsingException {
		if (hasDot)
			throw new ParsingException(numbers.pop());
		hasDot = true;
		mantisse = 0;
	}

	// Q5
	public void read(String s) throws ParsingException {
		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			read(c);
		}
	}

	// Q7
	public boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c == '$')
			return true;
		return false;
	}

	public static char toChar(Operator o) {
		switch (o) {
		case PLUS:
			return '+';

		case MINUS:
			return '-';

		case UMINUS:
			return '-';

		case MULT:
			return '*';

		case DIV:
			return '/';

		case VAR:
			return '$';
		}
		throw new java.lang.Error("Operator unfound");
	}

	public Operator toOperator(char c, char ch) throws ParsingException {

		if (c == '+') {
			if (lastToken == '+')
				throw new ParsingException(Operator.PLUS);
			lastToken = '+';
			return Operator.PLUS;
		}
		if (c == '-') {
			if (lastToken == '0') {
				lastToken = '+';
				return Operator.MINUS;
			}
			if (lastToken == '+' || lastToken == '=')
				if (lastToken == '=') numbers.pop();
				lastToken = '+';
				return Operator.UMINUS;
		}
		if (c == '*') {
			if (lastToken == '+')
				throw new ParsingException(Operator.MULT);
			lastToken = '+';
			return Operator.MULT;
		}
		if (c == '/') {
			if (lastToken == '+')
				throw new ParsingException(Operator.DIV);
			lastToken = '+';
			return Operator.DIV;
		}
		if (c == '(') {
			lastToken = '+';
			return Operator.OPEN;
		}
		if (c == ')') {
			lastToken = '0';
			return Operator.CLOSE;
		}
		if (c == '$') {
			if (lastToken == '=') numbers.pop(); 
			lastToken = '+';
			return Operator.VAR;
		}
		throw new java.lang.Error("Operator unfound");
	}

	public static int precedence(Operator o) {

		if (o == Operator.PLUS)
			return 0;
		if (o == Operator.MINUS)
			return 0;
		if (o == Operator.MULT)
			return 1;
		if (o == Operator.DIV)
			return 1;
		if (o == Operator.UMINUS)
			return 2;
		if (o == Operator.VAR)
			return 3;
		if (o == Operator.OPEN)
			return 4;
		if (o == Operator.CLOSE)
			return -1;

		throw new java.lang.Error("Operator unfound");
	}

	public static int precedence(Stack<Operator> o) {
		if (o.isEmpty())
			return -2;
		else
			return precedence(o.peek());
	}

	public int precedence() {
		if (operators.isEmpty())
			return -1;
		return precedence(operators.peek());
	}

	public void read(Operator o) {
		operators.push(o);
	}

	// 08
	public void execute(Operator o) throws ParsingException {
		if (o != Operator.UMINUS && o != Operator.VAR) {

			Double d2;
			if (!numbers.isEmpty())
				d2 = numbers.pop();
			else
				throw new ParsingException(o);
			Double d1;
			if (!numbers.isEmpty())
				d1 = numbers.pop();
			else
				d1 = 0.0;
			switch (o) {
			case PLUS:
				numbers.push(d1 + d2);
				break;
			case MINUS:
				numbers.push(d1 - d2);
				break;
			case MULT:
				numbers.push(d1 * d2);
				break;
			case DIV:
				numbers.push(d1 / d2);
				break;
			}
		} else {
			Double d1;
			if (o == Operator.UMINUS) {
				System.out.println("running var" + results.getFirst());
				if (!numbers.isEmpty())
					d1 = numbers.pop();
				else
					throw new ParsingException(o);
				numbers.push(-d1);
			} else if (o == Operator.VAR) {
				if (numbers.isEmpty())
					throw new ParsingException(o);
				d1 = numbers.pop();
				if (d1 != Math.rint(d1))
					throw new ParsingException(d1.toString());
				if (d1 > results.size())
					throw new ParsingException(d1.toString());
				System.out.println("running var" + results.getFirst());
				numbers.push(results.getFirst());
			}
		}
	}

	public static boolean isBinary(Operator o) {
		if (o == Operator.PLUS || o == Operator.MINUS || o == Operator.MULT || o == Operator.DIV)
			return true;
		return false;
	}

	public void execute(Operator o, Stack<Double> d) throws ParsingException {
		if (o != Operator.UMINUS && o != Operator.VAR) {
			Double d1;
			if (!d.isEmpty())
				d1 = d.pop();
			else
				throw new ParsingException(o);
			Double d2;
			if (!d.isEmpty())
				d2 = d.pop();
			else {
				d2 = d1;
				d1 = 0.0;
			}
			switch (o) {
			case PLUS:
				d.push(d1 + d2);
				break;
			case MINUS:
				d.push(d1 - d2);
				break;
			case MULT:
				d.push(d1 * d2);
				break;
			case DIV:
				d.push(d1 / d2);
				break;
			}
		} else {
			Double d1;
			if (o == Operator.UMINUS) {
				if (!d.isEmpty())
					d1 = d.pop();
				else
					throw new ParsingException(o);
				d.push(-d1);
			} else if (o == Operator.VAR) {
				if (d.isEmpty())
					throw new ParsingException(o);
				d1 = d.pop();
				if (d1 != Math.rint(d1))
					throw new ParsingException(d1.toString());
				if (d1 > results.size())
					throw new ParsingException(d1.toString());
				d.push(results.get((int) Math.rint(d1 - 1)));
			}
		}
	}

	public void execute() throws ParsingException {
		if (numbers.isEmpty()) {
			numbers.push(0.0);
			return;
		}
		Stack<Double> dtemp = new Stack<Double>();
		Stack<Operator> otemp = new Stack<Operator>();
//		System.out.println("--------");
//		System.out.println(this);
//		System.out.println("--------");
		Operator o, o1;
		boolean flag = true;
		while (!operators.isEmpty()) {
			// System.out.println("new number");
			flag = true;
			dtemp.push(numbers.pop());
			// System.out.println(dtemp);
			// System.out.println(otemp);
			// System.out.println("first cycle");
			while (!operators.isEmpty() && flag) {
				o = operators.pop();
				if (isBinary(o))
					flag = true;
//				 System.out.println(dtemp);
//				 System.out.println(otemp);
				switch (o) {
				case OPEN:
					if (!otemp.isEmpty())
						o1 = otemp.pop();
					else
						throw new ParsingException(false);
					while (o1 != Operator.CLOSE) {
						execute(o1, dtemp);
						if (!otemp.isEmpty())
							o1 = otemp.pop();
						else {
							throw new ParsingException(false);
						}
					}
					break;
				case CLOSE:
					otemp.push(o);
					break;
				default:
					if (isBinary(o))
						flag = false;
					while (precedence(o) < precedence(otemp)) {
						// System.out.println("Executing...");
						execute(otemp.pop(), dtemp);
					}
					otemp.push(o);
				}
			}
		}
		// System.out.println(this);
		if (!numbers.isEmpty())
			dtemp.push(numbers.pop());
		while (!otemp.isEmpty()) {
			o1 = otemp.pop();
			if (o1 == Operator.CLOSE)
				throw new ParsingException(true);
			execute(o1, dtemp);
		}
//		System.out.println(dtemp);
//		System.out.println(otemp);
		results.addFirst(dtemp.peek());
		numbers.push(dtemp.pop());
	}

	// main
	public static void main(String[] s) {

	}

}