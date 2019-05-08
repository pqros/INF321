public class Test{
	public static void main(String[] str) throws ParsingException {
		Calculator c = new Calculator();
		c.read("2= 4= 6= $1=");
		System.out.println(c);
	}
}