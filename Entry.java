import pkg.*;

public class Entry {

    public static void main(String[] args) {
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

                if (s.equals("exit")) {
                    break;
                }
                Parser e = new Parser(s);
                Parser a = new Parser("(())");

                Node result = Eval.eval(e.code.car(), a.code.car());
                result.echo();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
