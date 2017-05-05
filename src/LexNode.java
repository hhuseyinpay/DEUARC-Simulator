import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * Created by quiet on 02/05/17.
 */
public class LexNode {

    private String[] lexes;
    private boolean isWrong;

    public LexNode(String[] lexes, boolean isWrong) {
        this.lexes = lexes;
        this.isWrong = isWrong;
    }

    public LexNode(String lex, String[] parameters, boolean isWrong) {
        int len = parameters.length;
        this.lexes = new String[len + 1];
        this.lexes[0] = lex;

        for (int i = 1; i < len + 1; i++) {
            lexes[i] = parameters[i - 1];
        }

        this.isWrong = isWrong;
    }

    public String[] getLexes() {
        return lexes;
    }

    public boolean isWrong() {
        return isWrong;
    }

    public void setWrong() {
        isWrong = true;
    }


    public String getLine() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (lexes[0].charAt(lexes[0].length() - 1) == ':') {
            sb.append(lexes[0]);
            i++;
        }
        sb.append("\t");
        for (; i < lexes.length; i++) {
            sb.append(lexes[i]);
            sb.append("  ");
        }
        sb.append('\n');
        return sb.toString();
    }
}
