package pkg;

public class Node {
    public static int cnt = 0; //インスタンス化された回数
    Node(){
        cnt++;
    }

    public String tos(int i){
        String s = "Node(" + cnt + ")";
        return s;
    }

    public void echo(){
        System.out.println(tos(0));
    }

    public Node car() throws Exception{
        return null;
    }

    public Node cdr() throws Exception{
        return null;
    }

    public String value() throws Exception{
        return null;
    }

    public Boolean isAtom(){
        return false;
    }

    public Boolean isNIL(){
        return false;
    }

    public Boolean isNum(){
        return false;
    }


}
