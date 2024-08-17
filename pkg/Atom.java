package pkg;

public class Atom extends Node{

    public String value;
    int id;

    public Atom(){
        id = cnt;
        value = "NIL";
    }

    public Atom(String v){
        id = cnt;
        if(v == null){
            value = "NIL";
        }else{
            value = v;
        }
    }

    public Node car() throws Exception{
        throw new Exception("at Atom(" + id + ").car() not allowed\n");
    }

    public Node cdr() throws Exception{
        throw new Exception("at Atom(" + id + ").cdr() not allowed\n");
    }

    public String value(){
        return value;
    }

    public Boolean isAtom(){
        return true;
    }

    public Boolean isNIL(){
        return value.equals("NIL");
    }

    public Boolean isNum(){
        return value.matches("[0-9-]+");
    }

    public String tos(int i){
        String s = "Atom" + id + " " + value + "\n";
        return s;
    }
}
