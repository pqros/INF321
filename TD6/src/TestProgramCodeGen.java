import java.io.*;

import edu.polytechnique.mjava.ast.*;
import edu.polytechnique.mjava.parser.*;
import edu.polytechnique.mjava.toplevel.*;
import edu.polytechnique.mjava.typing.exn.*;

public final class TestProgramCodeGen {
  public final static String PATH = "example.wil";

  public static void main(String[] args) throws IOException {
    try {
      Program prg = MJavaTop.parseAndTypeProgramFromFile(PATH);
      ProgramCodeGen cg = new ProgramCodeGen();

      cg.visit(prg.procs);
      System.out.print(cg.cg);
    } catch (MJavaParseError | MJavaTypingError e) {
      System.err.println(e.getMessage());
    }
  }
}
