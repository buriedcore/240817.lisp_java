package pkg;
import java.io.*;

public class Input {
    public static String readLine() throws IOException{
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String message;
            System.out.flush();
            message = stdin.readLine();
            return message;
    }
}