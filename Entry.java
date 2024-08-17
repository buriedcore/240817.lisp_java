import pkg.*;

public class Entry {
    public static void main(String[] args) {
        Atom a = new Atom();
        while (true) {
            try {
                System.out.println("lisp>>");
                String s = "";
                String temp;
                while (true) {
                    temp = Input.readLine();
                    if (temp.equals("")) {
                        break;
                    }
                    s = s + " " + temp;
                }
                s = s.trim();

                if (s.equals("")) {
                    System.out.println("quit.");
                    break;
                }
                Parser e = new Parser(s);
                // Parser a = new Parser("()");

                Node result = Eval.eval(e.code.car(), a);

                System.out.println("result ===================================\n");
                result.echo();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
