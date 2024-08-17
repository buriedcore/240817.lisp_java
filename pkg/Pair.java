package pkg;

public class Pair extends Node {
    public Node car, cdr;
    public int id;

    public Pair() {
        id = cnt;
        car = null;
        cdr = null;
    }

    public Pair(Node x, Node y) {
        id = cnt;
        if (x == null) {
            car = new Atom();
        } else {
            car = x;
        }
        if (y == null) {
            cdr = new Atom();
        } else {
            cdr = y;
        }
    }

    public Node car() {
        return car;
    }

    public Node cdr() {
        return cdr;
    }

    public String value() throws Exception{
        throw new Exception("at Pair(" + id + ").value() not allowed\n");
    }

    public String tos(int i) {
        String indent = "";
        int j = 0;
        while(j++ < i){
            indent += "    ";
        }
        String s = "Pair" + id + "\n";
        s = s + indent + "car" + id + ": " + car.tos(i + 1);
        s = s + indent + "cdr" + id + ": " + cdr.tos(i);
        return s;
    }
}