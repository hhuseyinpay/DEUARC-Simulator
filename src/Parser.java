import java.util.LinkedList;

/**
 * Created by quiet on 02/05/17.
 */
class Parser {

    public LinkedList<LexNode> parse(String content) {
        if (content == null) {
            return null;
        }
        String[] lines = content.split("\n");
        String[] lex;
        String[] comment;

        LinkedList<LexNode> lexList = new LinkedList<>();

        for (String line : lines) {
            comment = line.trim().split("%");

            //we eliminated comment line
            if (comment[0].equals("")) {
                continue;
            }

            lex = comment[0].trim().split("\\s+");
            int len = lex.length;

            if (len < 1)
                continue;

            if (lex[0].charAt(lex[0].length() - 1) == ':') {

                if (len > 1 && lex[2].contains(",")) {
                    String[] parameter = lex[2].split(",");
                    len = parameter.length;

                    if (len < 2 || len > 3 || parameter[0].equals("") || parameter[len - 1].equals("")) {
                        lexList.add(new LexNode(lex, true));
                        continue;
                    }
                    String[] tmp = new String[2 + parameter.length];
                    tmp[0] = lex[0];
                    tmp[1] = lex[1];
                    for (int i = 2; i < parameter.length + 2; i++) {
                        tmp[i] = parameter[i - 2];
                    }
                    lexList.add(new LexNode(tmp, false));
                    continue;
                }
                lexList.add(new LexNode(lex[0], new String[]{lex[1], lex[2]}, false));
                continue;
            }

            // cannot be greater than 3
            if (len > 3) {
                lexList.add(new LexNode(lex, true));
                continue;
            }
            //first element cannot be anything but letter
            else if (lex[0].charAt(0) < 65 || lex[0].charAt(0) > 90) {
                lexList.add(new LexNode(lex, true));
                continue;
            }

            // if there is parameter, split it.
            if (len > 1 && lex[1].contains(",")) {
                String[] parameter = lex[1].split(",");
                len = parameter.length;

                if (len < 2 || len > 3 || parameter[0].equals("") || parameter[len - 1].equals("")) {
                    lexList.add(new LexNode(lex, true));
                    continue;
                }
                lexList.add(new LexNode(lex[0], parameter, false));
                continue;
            }

            lexList.add(new LexNode(lex, false));

        }
        return lexList;
    }
}
