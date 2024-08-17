package pkg;

import java.util.Arrays;

public class Parser {
    public Node code;

    public Parser(String s) throws Exception {
        try {
            if (s.equals("")) {
                throw new Exception("case: empty\n");
            }
            String[] ss = format(s);
            int i = 0;
            int j = 0;
            while (i < ss.length) {
                if (ss[i].equals("(")) {
                    j++;
                }
                if (ss[i].equals(")")) {
                    j--;
                }
                if (j <= 0 && i + 1 < ss.length) {
                    throw new Exception("case: parenthesis\n" + Arrays.toString(ss) + " " + i + " " + j);
                }
                i++;
            }

            this.code = toNode(ss, 0);
        } catch (Exception error) {
            throw new Exception("at Parser.Parser\n" + error);
        }
    }

    Node toNode(String[] ss, int i) throws Exception {
        if (ss.length <= i) {
            return new Atom();
        }
        if (ss[i].equals("(")) {
            int close_parethent = search_parethent(ss, i);
            Pair p = new Pair();
            p.car = toNode(ss, i + 1);
            p.cdr = toNode(ss, close_parethent + 1);

            return p;
        }
        if (ss[i].equals(")")) {
            return new Atom();
        }

        Pair p = new Pair();
        p.car = new Atom(ss[i]);
        p.cdr = toNode(ss, i + 1);
        return p;
    }

    String[] format(String s) {
        String s2 = s.replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .replaceAll("\n", " ")
                .replaceAll("\t{1,}", " ")
                .replaceAll(" {2,}", " ")
                .trim();
        return s2.split("\s");
    }

    int search_parethent(String[] ss, int i) throws Exception {
        try {
            int j = i + 1;
            int cnt = 1;
            while (j < ss.length && ss[j] != null) {
                if (ss[j].equals("(")) {
                    cnt++;
                }
                if (ss[j].equals(")")) {
                    cnt--;
                }
                if (cnt == 0) {
                    break;
                }
                j++;
            }

            if (0 < cnt) {
                throw new Exception("case: missing closing parenthesis\n");
            }
            return j;
        } catch (Exception error) {
            throw new Exception("at Parser.search_parethent\n" + Arrays.toString(ss) + "\n" + error);
        }
    }
}
