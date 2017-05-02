import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by quiet on 02/05/17.
 */
public class Compiler {
    static ArrayList INSTRUCTIONS = new ArrayList();
    private char[][] instsTable = new char[32][11];
    private Map<String, String> instructions = new HashMap<String, String>();

    public Compiler() {
        instructions.put("HLT", "1000");
        instructions.put("ADD", "0000");
        instructions.put("INC", "0001");
        instructions.put("DBL", "0010");
        instructions.put("DBT", "0011");
        instructions.put("NOT", "0100");
        instructions.put("AND", "0101");
        instructions.put("LD", "0110");
        instructions.put("ST", "0111");
        instructions.put("TSF", "1001");
        instructions.put("CAL", "1010");
        instructions.put("RET", "1011");
        instructions.put("JMP", "1100");
        instructions.put("JMR", "1101");
        instructions.put("PSH", "1110");
        instructions.put("POP", "1111");
    }

    public char[][] compile(LinkedList<LexNode> lexList) {
        String[] lex;
        for (LexNode lexem : lexList) {
            lex = lexem.getLexes();
            if (lex[0].equals("ORG")) {

            } else if (lex[0].equals("HLT")) {

            } else if (lex[0].equals("ADD")) {

            } else if (lex[0].equals("INC")) {

            } else if (lex[0].equals("DBL")) {

            } else if (lex[0].equals("DBT")) {

            } else if (lex[0].equals("NOT")) {

            } else if (lex[0].equals("AND")) {

            } else if (lex[0].equals("LD")) {

            } else if (lex[0].equals("ST")) {

            } else if (lex[0].equals("TSF")) {

            } else if (lex[0].equals("CAL")) {

            } else if (lex[0].equals("RET")) {

            } else if (lex[0].equals("JMP")) {

            } else if (lex[0].equals("JMR")) {

            } else if (lex[0].equals("PSH")) {

            } else if (lex[0].equals("POP")) {

            } else if (lex[0].equals("END")) {

            } else if (lex[0].equals("DEC")) {

            } else if (lex[0].equals("HEX")) {

            } else {
            }


        }
        return null;
    }

}
